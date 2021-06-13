package rijks.feature.rijksstudio

import com.example.rijks.data.network.model.ArtObjectDetailJson
import com.example.rijks.data.network.model.ArtObjectDetailResponseJson
import com.example.rijks.data.network.model.ArtObjectJson
import com.example.rijks.data.network.model.ArtObjectResponseJson
import com.example.rijks.data.network.service.RijksRetrofitService
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException

class FakeRetrofitService  : RijksRetrofitService {
    private val artObjects = mutableListOf<ArtObjectJson>()
    var failureMsg: String? = null
    private lateinit var artObjectDetail: ArtObjectDetailJson

    fun setArtObjects(artObjectJson: ArtObjectJson){
        artObjects.add(artObjectJson)
    }

    fun clearArtObjects(){
        artObjects.clear()
    }

    fun setArtObjectDetailResonse(artObjectDetailResponseJson: ArtObjectDetailJson){
        artObjectDetail = artObjectDetailResponseJson
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
    ): Response<ArtObjectDetailResponseJson> {
        failureMsg?.let {
          return Response.error(1, ResponseBody.create(null, ""))
        }
        return Response.success(ArtObjectDetailResponseJson(artObjectDetail))
    }
}