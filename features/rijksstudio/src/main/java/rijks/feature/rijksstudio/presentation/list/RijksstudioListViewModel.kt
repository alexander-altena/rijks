package rijks.feature.rijksstudio.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rijks.domain.model.ArtObject
import kotlinx.coroutines.flow.Flow
import rijks.feature.rijksstudio.domain.RijksstudioRepository
import javax.inject.Inject


class RijksstudioListViewModel @Inject constructor(private val rijksstudioRepository: RijksstudioRepository) : ViewModel() {

    fun getArtObjects(): Flow<PagingData<ArtObject>> {
        return rijksstudioRepository.getAllArtObjects()
            .cachedIn(viewModelScope)
    }
}