package fr.appsolute.tp.data.networking.datasource

import androidx.paging.PageKeyedDataSource
import androidx.paging.PageKeyedDataSource.LoadInitialParams
import fr.appsolute.tp.data.model.Character
import fr.appsolute.tp.data.networking.HttpClientManager
import fr.appsolute.tp.data.networking.api.CharacterApi
import fr.appsolute.tp.data.networking.createApi
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class CharacterDataSourceTest {

    private val testDispatcher = newSingleThreadContext("UI context")

    private lateinit var api: CharacterApi
    private lateinit var dataSource: CharacterDataSource

    private val loadInitialCallback =
        object : PageKeyedDataSource.LoadInitialCallback<Int, Character>() {
            override fun onResult(
                data: MutableList<Character>,
                position: Int,
                totalCount: Int,
                previousPageKey: Int?,
                nextPageKey: Int?
            ) {
                runBlocking(Dispatchers.Main) {
                    assertEquals("data should have 20 element", 20, data.count())
                    assertEquals("position should be 0", 0, position)
                    assertEquals("totalCount should be 493", 493, totalCount)
                    assertNull("No previous page", previousPageKey)
                    assertEquals("Next key should be 2", 2, nextPageKey)
                }
            }

            override fun onResult(
                data: MutableList<Character>,
                previousPageKey: Int?,
                nextPageKey: Int?
            ) {
                runBlocking(Dispatchers.Main) {
                    assertEquals("data must have 20 elements", 20, data.count())
                    assertNull("No previous page", previousPageKey)
                    assertEquals("Next key should be 2", 2, nextPageKey)
                }
            }
        }

    private val loadCallback = object : PageKeyedDataSource.LoadCallback<Int, Character>() {
        override fun onResult(data: MutableList<Character>, adjacentPageKey: Int?) {
            runBlocking(Dispatchers.Main) {
                assertEquals("data should have 20 element", 20, data.count())
                assertEquals("Next key should be 3", 3, adjacentPageKey)
            }
        }
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        api = HttpClientManager.instance.createApi()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.close()
    }

    @Test
    fun `load initial with place holder`() = runBlocking(Dispatchers.IO) {
        dataSource = createDataSource()
        dataSource.loadInitial(
            params = LoadInitialParams(20, true),
            callback = loadInitialCallback
        )
    }

    @Test
    fun `load initial without place holder`() = runBlocking(Dispatchers.IO) {
        dataSource = createDataSource()
        dataSource.loadInitial(
            params = LoadInitialParams(20, false),
            callback = loadInitialCallback
        )
    }


    @Test
    fun loadAfter() = runBlocking(Dispatchers.IO) {
        dataSource = createDataSource()
        dataSource.loadAfter(
            params = PageKeyedDataSource.LoadParams(2, 20),
            callback = loadCallback
        )
    }

    private fun CoroutineScope.createDataSource() =
        CharacterDataSource.Factory(api, this).create() as CharacterDataSource
}