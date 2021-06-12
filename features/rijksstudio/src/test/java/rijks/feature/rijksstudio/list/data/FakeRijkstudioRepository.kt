package rijks.feature.rijksstudio.list.data

import androidx.paging.PagingData
import com.example.rijks.data.network.model.ArtObjectJson
import com.example.rijks.domain.model.ArtObject
import com.example.rijks.domain.model.ArtObjectDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import rijks.feature.rijksstudio.list.domain.RijksstudioRepository
import java.io.IOException

class FakeRijkstudioRepository : RijksstudioRepository {

    private var shouldReturnNetworkError = false
    private lateinit var pagingData: PagingData<ArtObject>

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    fun setPagingData(pagina: PagingData<ArtObject>){
       pagingData = pagina
    }

    fun clearPaginaData(){
        pagingData = PagingData.empty()
    }

    override fun getAllArtObjects(): Flow<PagingData<ArtObject>> {
        return flow {
            emit(Result.success(pagingData)

            )
        }
    }

    override suspend fun getArtObjectDetail(objectId: String): ArtObjectDetail {
        TODO("Not yet implemented")
    }
}