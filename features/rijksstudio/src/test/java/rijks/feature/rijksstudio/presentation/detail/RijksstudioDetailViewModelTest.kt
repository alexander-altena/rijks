package rijks.feature.rijksstudio.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.rijks.common.Resource
import com.example.rijks.domain.model.ArtObjectDetail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import rijks.feature.rijksstudio.MainCoroutineRule
import rijks.feature.rijksstudio.getOrAwaitValue
import rijks.feature.rijksstudio.list.data.FakeRijkstudioRepository
import kotlin.test.assertEquals


@ExperimentalCoroutinesApi
class RijksstudioDetailViewModelTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: RijksstudioDetailViewModel
    private lateinit var fakeRijkstudioRepository: FakeRijkstudioRepository

    @Before
    fun setup() {
        fakeRijkstudioRepository = FakeRijkstudioRepository()
        viewModel = RijksstudioDetailViewModel(fakeRijkstudioRepository)
    }

    @After
    fun tearDown(){
        fakeRijkstudioRepository.clearPaginaData()
        fakeRijkstudioRepository.clearObjectDetail()
    }

    @Test
    fun `fetching invalid objectId returns error`() {
        viewModel.loadArtObjectDetail("")

        val artObject = viewModel.artObjectDetail.getOrAwaitValue()

        assertEquals(Resource.error("Error", null), artObject)

    }

    @Test
    fun `fetching valid objectId returns artObject`() {
        val art = ArtObjectDetail("id", "maker", "description", "url")
        fakeRijkstudioRepository.setArtObjectDetail(art)

        viewModel.loadArtObjectDetail("valid")

        val artObject = viewModel.artObjectDetail.getOrAwaitValue()

        assertEquals(Resource.success(art), artObject)

    }


}