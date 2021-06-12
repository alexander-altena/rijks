package rijks.feature.rijksstudio

import com.example.rijks.data.network.model.ArtObjectDetailJson
import com.example.rijks.data.network.model.ArtObjectDetailResponseJson
import com.example.rijks.data.network.model.ArtObjectJson
import com.example.rijks.data.network.model.ArtObjectResponseJson
import com.example.rijks.data.network.service.RijksRetrofitService
import java.io.IOException

class FakeRetrofitService  : RijksRetrofitService {
    private val artObjects = mutableListOf<ArtObjectJson>()
    var failureMsg: String? = null
    private lateinit var artObjectDetailJson: ArtObjectDetailResponseJson

    fun setArtObjects(artObjectJson: ArtObjectJson){
        artObjects.add(artObjectJson)
    }

    fun clearArtObjects(){
        artObjects.clear()
    }


    override suspend fun getAllArtObjects(
        culture: String,
        options: HashMap<String, String>
    ): ArtObjectResponseJson {
        failureMsg?.let {
            throw IOException(it)
        }

        return ArtObjectResponseJson(0, artObjects)
    }

    override suspend fun getArtObjectDetails(
        culture: String,
        objectNumber: String,
        key: String
    ): ArtObjectDetailResponseJson {
        failureMsg?.let {
            throw IOException(it)
        }
        return artObjectDetailJson
    }
}