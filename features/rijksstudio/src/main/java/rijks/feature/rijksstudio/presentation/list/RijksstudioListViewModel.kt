package rijks.feature.rijksstudio.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.rijks.domain.model.ArtObjectList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rijks.feature.rijksstudio.domain.RijksstudioRepository
import javax.inject.Inject


class RijksstudioListViewModel @Inject constructor(private val rijksstudioRepository: RijksstudioRepository) : ViewModel() {

    fun getArtObjects(): Flow<PagingData<ArtObjectList>> {
        return rijksstudioRepository.getAllArtObjects()
            .map { pagingData -> pagingData.map { ArtObjectList.ArtObjectItem(it) }}
            .map {
                it.insertSeparators{before, after ->
                    // Beginning of the list
                    if (before == null) {
                        return@insertSeparators ArtObjectList.ArtObjectHeader("Begin of list header")
                    }

                    // End of the list
                    if (after == null) {
                        return@insertSeparators null
                    }

                    else {
                        return@insertSeparators null
                    }
                }
            }
            .cachedIn(viewModelScope)
    }
}