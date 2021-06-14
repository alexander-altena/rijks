package rijks.feature.rijksstudio.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rijks.common.Resource
import com.example.rijks.domain.model.ArtObjectDetail
import kotlinx.coroutines.launch
import rijks.feature.rijksstudio.domain.RijksstudioRepository
import javax.inject.Inject

class RijksstudioDetailViewModel @Inject constructor(private val rijksstudioRepository: RijksstudioRepository) : ViewModel()  {
    private val _artObjectDetail = MutableLiveData<Resource<ArtObjectDetail>>()

    val artObjectDetail: LiveData<Resource<ArtObjectDetail>>
        get() = _artObjectDetail

    fun loadArtObjectDetail(objectId: String){
        viewModelScope.launch {
            _artObjectDetail.postValue(Resource.loading(null))
            val response = rijksstudioRepository.getArtObjectDetail(objectId)
            println("the detail object is ${response.data?.description}")
            println("the detail object is ${response.data?.description}")
           _artObjectDetail.postValue(response)
        }
    }
}