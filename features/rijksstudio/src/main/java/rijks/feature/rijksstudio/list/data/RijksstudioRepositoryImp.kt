package rijks.feature.rijksstudio.list.data

import androidx.paging.*
import com.example.rijks.data.database.ArtObjectDao
import com.example.rijks.data.database.RemoteKeysDao
import com.example.rijks.data.database.RijksDatabase
import com.example.rijks.data.network.service.RijksRetrofitService
import com.example.rijks.domain.model.ArtObject
import com.example.rijks.domain.model.ArtObjectDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rijks.feature.rijksstudio.list.domain.RijksstudioRepository
import javax.inject.Inject

class RijksstudioRepositoryImp @Inject constructor(private val service: RijksRetrofitService, private val database: RijksDatabase, private val artObjectDao: ArtObjectDao, private val remoteKeysDao: RemoteKeysDao) :
    RijksstudioRepository {

    @ExperimentalPagingApi
    override fun getAllArtObjects() = Pager(
        config = PagingConfig(RIJKS_NETWORK_PAGE_SIZE),
        remoteMediator = RijksstudioRemoteMediator(database, artObjectDao, remoteKeysDao, service)
    ){
        artObjectDao.artObjects()
    }.flow.map { paginData ->
        paginData.map {
            ArtObject(it.id, it.objectNumber, it.title, it.principalOrFirstMaker?: "", it.imageUrl)
        }
    }



    override suspend fun getArtObjectDetail(objectId: String): ArtObjectDetail {
        return ArtObjectDetail("", "", "", "")

    }

    companion object{
        const val RIJKS_NETWORK_PAGE_SIZE = 10
    }
}