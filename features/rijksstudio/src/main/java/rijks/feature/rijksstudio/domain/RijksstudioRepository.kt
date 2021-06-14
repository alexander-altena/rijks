package rijks.feature.rijksstudio.domain

import androidx.paging.PagingData
import com.example.rijks.common.Resource
import com.example.rijks.domain.model.ArtObject
import com.example.rijks.domain.model.ArtObjectDetail
import kotlinx.coroutines.flow.Flow

interface RijksstudioRepository {

    fun getAllArtObjects(): Flow<PagingData<ArtObject>>
    suspend fun getArtObjectDetail(objectId: String) : Resource<ArtObjectDetail>

}