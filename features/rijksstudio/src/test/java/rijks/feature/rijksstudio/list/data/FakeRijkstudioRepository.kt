package rijks.feature.rijksstudio.list.data

import androidx.paging.PagingData
import com.example.rijks.common.Resource
import com.example.rijks.domain.model.ArtObject
import com.example.rijks.domain.model.ArtObjectDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import rijks.feature.rijksstudio.domain.RijksstudioRepository

class FakeRijkstudioRepository : RijksstudioRepository {

    private lateinit var pagingData: PagingData<ArtObject>
    private var artObjectDetail : ArtObjectDetail? = null

    fun setArtObjectDetail(artObjectDetail: ArtObjectDetail){
        this.artObjectDetail = artObjectDetail
    }

    fun clearPaginaData(){
        pagingData = PagingData.empty()
    }

    fun clearObjectDetail(){
        artObjectDetail = null
    }

    override fun getAllArtObjects(): Flow<PagingData<ArtObject>> {
        return flow {
            emit(pagingData)
        }
    }

    override suspend fun getArtObjectDetail(objectId: String): Resource<ArtObjectDetail> {
      return if (objectId.isEmpty() || artObjectDetail == null) Resource.error("Error", null)
        else{
            Resource.success(artObjectDetail)
        }
    }
}