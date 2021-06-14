package rijks.feature.rijksstudio.data


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rijks.data.network.model.ArtObjectJson
import com.example.rijks.data.network.service.RijksRetrofitService
import retrofit2.HttpException
import rijks.feature.rijksstudio.data.RijksstudioRepositoryImp.Companion.RIJKS_NETWORK_PAGE_SIZE
import java.io.IOException


class RijksstudioPagingSource(
    private val rijksstudioRetrofit: RijksRetrofitService
) : PagingSource<Int, ArtObjectJson>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtObjectJson> {
        val position = params.key ?: RIJKS_STARTING_PAGE_INDEX
        return try {
            val response = rijksstudioRetrofit.getAllArtObjects("en", hashMapOf(
                "key" to "0fiuZFh4",
                "p" to "$position",
                "ps" to "$RIJKS_NETWORK_PAGE_SIZE"
            ))


            val artObjects = response.artObjects
            val nextKey = if (artObjects.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
//                params.loadSize / RIJKS_NETWORK_PAGE_SIZE
                position + 1

            }
            LoadResult.Page(
                    data = artObjects,
                    prevKey = if (position == RIJKS_STARTING_PAGE_INDEX) null else position - 1,
                    nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, ArtObjectJson>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}