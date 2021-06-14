package rijks.feature.rijksstudio.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rijks.data.database.*
import com.example.rijks.data.network.service.RijksRetrofitService
import retrofit2.HttpException
import java.io.IOException

const val RIJKS_STARTING_PAGE_INDEX = 0;

@ExperimentalPagingApi
class RijksstudioRemoteMediator(
    private val db: RijksDatabase,
    private val artObjectDao: ArtObjectDao,
    private val remoteKeyDao: RemoteKeysDao,
    private val rijksstudioApi: RijksRetrofitService
) : RemoteMediator<Int, ArtObjectEntity>() {

    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArtObjectEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: RIJKS_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }


        try {
            val apiResponse = rijksstudioApi.getAllArtObjects(
                "EN", hashMapOf(
                    "key" to "0fiuZFh4",
                    "imgonly" to "True",
                    "p" to "$page",
                    "ps" to "${RijksstudioRepositoryImp.RIJKS_NETWORK_PAGE_SIZE}"
                )
            )

            val artObjects = apiResponse.artObjects
            val endOfPaginationReached = artObjects.isEmpty()
            db.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.clearRemoteKeys()
                    artObjectDao.clearArtObjects()
                }
                val prevKey = if (page == RIJKS_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = artObjects.map {
                    RemoteKeysEntity(artObjectId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                remoteKeyDao.insertAllKeys(keys)

                artObjectDao.insertAll(artObjects.map {
                    val height = it.webImage?.height ?: 0
                    val width = it.webImage?.width ?: 0

                    ArtObjectEntity(
                        it.id,
                        it.objectNumber,
                        it.title,
                        it.principalOrFirstMaker ?: "",
                        it.webImage?.url ?: "",
                        height.toFloat()/width

                    )
                })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ArtObjectEntity>): RemoteKeysEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { art ->
                // Get the remote keys of the last item retrieved
                remoteKeyDao.remoteKeysRepoId(art.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ArtObjectEntity>): RemoteKeysEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { art ->
                // Get the remote keys of the first items retrieved
                remoteKeyDao.remoteKeysRepoId(art.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ArtObjectEntity>
    ): RemoteKeysEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { artId ->
                remoteKeyDao.remoteKeysRepoId(artId)
            }
        }
    }
}