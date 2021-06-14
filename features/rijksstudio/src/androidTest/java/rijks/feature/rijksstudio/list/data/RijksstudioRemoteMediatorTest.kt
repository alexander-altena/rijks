package rijks.feature.rijksstudio.list.data

import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rijks.data.database.ArtObjectEntity
import com.example.rijks.data.database.RijksDatabase
import com.example.rijks.data.network.model.ArtObjectJson
import com.example.rijks.data.network.model.ArtWebImageJson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import rijks.feature.rijksstudio.FakeRetrofitService
import rijks.feature.rijksstudio.data.RijksstudioRemoteMediator
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalPagingApi
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class RijksstudioRemoteMediatorTest{
    private lateinit var database: RijksDatabase

    private val fakeObjects = listOf(
        ArtObjectJson("0", "name_0", "title", true, "maker", "makerLong", true, true, ArtWebImageJson(0,0,"url")),
        ArtObjectJson("1", "name_1", "title", true, "maker", "makerLong", true, true, ArtWebImageJson(0,0,"url"))
    )
    private val fakeApi = FakeRetrofitService()

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RijksDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun tearDown(){
        database.clearAllTables()
        fakeApi.failureMsg = null
        // Clear out posts after each test run.
        fakeApi.clearArtObjects()
        database.close()
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runBlocking {
        // Add mock results for the API to return.
        fakeObjects.forEach { art -> fakeApi.setArtObjects(art) }
        val remoteMediator = RijksstudioRemoteMediator(
            database,
            database.artObjectsDao(),
            database.remoteKeysDao(),
            fakeApi
        )
        val pagingState = PagingState<Int, ArtObjectEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertFalse { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runBlocking {
        // To test endOfPaginationReached, don't set up the mockApi to return post
        // data here.
        val remoteMediator = RijksstudioRemoteMediator(
            database,
            database.artObjectsDao(),
            database.remoteKeysDao(),
            fakeApi
        )
        val pagingState = PagingState<Int, ArtObjectEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertTrue { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }

    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runBlocking {
        // Set up failure message to throw exception from the mock API.
        fakeApi.failureMsg = "Throw test failure"
        val remoteMediator = RijksstudioRemoteMediator(
            database,
            database.artObjectsDao(),
            database.remoteKeysDao(),
            fakeApi
        )
        val pagingState = PagingState<Int, ArtObjectEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue {result is RemoteMediator.MediatorResult.Error }
    }

}