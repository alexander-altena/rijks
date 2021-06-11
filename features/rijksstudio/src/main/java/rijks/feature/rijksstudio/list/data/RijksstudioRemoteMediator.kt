package rijks.feature.rijksstudio.list.data

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

@OptIn(ExperimentalPagingApi::class)
class RijksstudioRemoteMediator(
    private val db: RijksDatabase,
    private val artObjectDao: ArtObjectDao,
    private val remoteKeyDao: RemoteKeysDao,
    private val rijksstudioApi: RijksRetrofitService
    ) : RemoteMediator<Int, ArtObjectEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArtObjectEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
//                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//                remoteKeys.nextKey.minus(1) ?: RIJKS_STARTING_PAGE_INDEX
                null
            }
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                when {
                    remoteKeys == null -> RIJKS_STARTING_PAGE_INDEX
                    remoteKeys.nextKey == null -> return MediatorResult.Success(endOfPaginationReached = true)
                    else -> remoteKeys.nextKey
                }
            }
        }

        try {
            val response = rijksstudioApi.getAllArtObjects("nl", hashMapOf(
                "key" to "0fiuZFh4",
                "p" to "$page",
                "ps" to "${RijksstudioRepositoryImp.RIJKS_NETWORK_PAGE_SIZE}"
            ))

            val endOfPaginationReached = response.artObjects.isEmpty()
            db.withTransaction {
                // Clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.clearRemoteKeys()
                    artObjectDao.clearArtObjects()
                }
                val prevKey = if (page == RIJKS_STARTING_PAGE_INDEX) null else page?.minus(1)
                val nextKey = if (endOfPaginationReached) null else page?.plus(1)
                val keys = response.artObjects.map{
                    RemoteKeysEntity(artObjectId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                remoteKeyDao.insertAllKeys(keys)
                artObjectDao.insertAll(response.artObjects.map { ArtObjectEntity(it.id, it.objectNumber, it.title, it.principalOrFirstMaker?: "", it.webImage.url) })
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
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { artObject ->
                // Get the remote keys of the last item retrieved
                remoteKeyDao.remoteKeysRepoId(artObject.id)
            }
    }


}
