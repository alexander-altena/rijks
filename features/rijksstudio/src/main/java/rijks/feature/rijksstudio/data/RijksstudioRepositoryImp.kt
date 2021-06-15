package rijks.feature.rijksstudio.data

import androidx.paging.*
import com.example.rijks.common.Resource
import com.example.rijks.data.database.ArtObjectDao
import com.example.rijks.data.database.RemoteKeysDao
import com.example.rijks.data.database.RijksDatabase
import com.example.rijks.data.network.service.RijksRetrofitService
import com.example.rijks.domain.model.ArtObject
import com.example.rijks.domain.model.ArtObjectDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rijks.feature.rijksstudio.domain.RijksstudioRepository
import javax.inject.Inject

class RijksstudioRepositoryImp @Inject constructor(
    private val service: RijksRetrofitService,
    private val database: RijksDatabase,
    private val artObjectDao: ArtObjectDao,
    private val remoteKeysDao: RemoteKeysDao
) :
    RijksstudioRepository {

    @ExperimentalPagingApi
    override fun getAllArtObjects(): Flow<PagingData<ArtObject>> {
        val pagingSourceFactory = { artObjectDao.artObjects() }
        return Pager(
            config = PagingConfig(
                pageSize = RIJKS_NETWORK_PAGE_SIZE,
                enablePlaceholders = true,
               ),
            remoteMediator = RijksstudioRemoteMediator(
                database,
                artObjectDao,
                remoteKeysDao,
                service
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { paginData ->
            paginData.map {
                ArtObject(
                    it.id,
                    it.objectNumber,
                    it.title,
                    it.principalOrFirstMaker ?: "", it.imageUrl, it.imgRatio
                )
            }
        }
    }


    override suspend fun getArtObjectDetail(objectId: String): Resource<ArtObjectDetail> {
        return try {
            val response = service.getArtObjectDetails("EN", objectId, "0fiuZFh4")
            if (response.isSuccessful) {
                response.body()?.let {
                    val label = it.artObject.label
                    return Resource.success(
                        ArtObjectDetail(
                            label.title,
                            label.makerLine,
                            label.description,
                            it.artObject.webImage.url
                        )
                    )
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Resource.error(response.message(), null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }


    }

    companion object {
        const val RIJKS_NETWORK_PAGE_SIZE = 20
    }
}