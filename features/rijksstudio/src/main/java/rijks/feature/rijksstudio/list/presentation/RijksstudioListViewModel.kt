package rijks.feature.rijksstudio.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rijks.domain.model.ArtObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import rijks.feature.rijksstudio.list.domain.RijksstudioRepository
import javax.inject.Inject


class RijksstudioListViewModel @Inject constructor(private val rijksstudioRepository: RijksstudioRepository) : ViewModel() {

    fun getArtObjects(): Flow<PagingData<ArtObject>> {
        return rijksstudioRepository.getAllArtObjects()
            .cachedIn(viewModelScope).catch { error ->
                println(" er is iets mis gegaan $error")
            }
    }
}