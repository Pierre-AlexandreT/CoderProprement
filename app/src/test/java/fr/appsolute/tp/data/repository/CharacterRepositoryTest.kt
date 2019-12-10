package fr.appsolute.tp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import fr.appsolute.tp.test.getBlockingValue
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class CharacterRepositoryTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()
    private val testDispatcher = newSingleThreadContext("UI context")
    private lateinit var repository: CharacterRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = CharacterRepository.instance
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.close()
    }

    @Test
    fun getPaginatedListTest() = runBlocking {
        val value = repository.getPaginatedList(this).getBlockingValue(
            timeOut = 10
        )
        assertTrue(
            "Size should 0 or 20",
            value?.count()?.equals(0) ?: false || value?.count()?.equals(20) ?: false
        )
    }
}