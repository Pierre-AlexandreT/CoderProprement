package fr.appsolute.tp.data.networking.api

import fr.appsolute.tp.data.model.Character
import fr.appsolute.tp.data.model.PaginatedResult
import fr.appsolute.tp.data.networking.HttpClientManager
import fr.appsolute.tp.data.networking.createApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class CharacterApiTest {

    private lateinit var instance: HttpClientManager
    private lateinit var api: CharacterApi


    @Before
    fun setUp() {
        instance = HttpClientManager.instance
        api = instance.createApi()
    }


    @Test
    fun getAllCharacter() = runBlocking {

        // region Test initialisation
        val paginatedInformation = PaginatedResult.Information(
            493,
            25,
            "https://rickandmortyapi.com/api/character/?page=2",
            ""
        )
        val firstCharacter = Character(
            1,
            "Rick Sanchez",
            "Alive",
            "Human",
            "",
            "Male",
            Character.Place(
                "Earth (C-137)",
                "https://rickandmortyapi.com/api/location/1"
            ),
            Character.Place(
                "Earth (Replacement Dimension)",
                "https://rickandmortyapi.com/api/location/20"
            ),
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2",
                "https://rickandmortyapi.com/api/episode/3",
                "https://rickandmortyapi.com/api/episode/4",
                "https://rickandmortyapi.com/api/episode/5",
                "https://rickandmortyapi.com/api/episode/6",
                "https://rickandmortyapi.com/api/episode/7",
                "https://rickandmortyapi.com/api/episode/8",
                "https://rickandmortyapi.com/api/episode/9",
                "https://rickandmortyapi.com/api/episode/10",
                "https://rickandmortyapi.com/api/episode/11",
                "https://rickandmortyapi.com/api/episode/12",
                "https://rickandmortyapi.com/api/episode/13",
                "https://rickandmortyapi.com/api/episode/14",
                "https://rickandmortyapi.com/api/episode/15",
                "https://rickandmortyapi.com/api/episode/16",
                "https://rickandmortyapi.com/api/episode/17",
                "https://rickandmortyapi.com/api/episode/18",
                "https://rickandmortyapi.com/api/episode/19",
                "https://rickandmortyapi.com/api/episode/20",
                "https://rickandmortyapi.com/api/episode/21",
                "https://rickandmortyapi.com/api/episode/22",
                "https://rickandmortyapi.com/api/episode/23",
                "https://rickandmortyapi.com/api/episode/24",
                "https://rickandmortyapi.com/api/episode/25",
                "https://rickandmortyapi.com/api/episode/26",
                "https://rickandmortyapi.com/api/episode/27",
                "https://rickandmortyapi.com/api/episode/28",
                "https://rickandmortyapi.com/api/episode/29",
                "https://rickandmortyapi.com/api/episode/30",
                "https://rickandmortyapi.com/api/episode/31"
            ),
            "https://rickandmortyapi.com/api/character/1",
            "2017-11-04T18:48:46.250Z"
        )
        // endregion

        api.getAllCharacter(1).apply {
            assertTrue("Request must be a success", this.isSuccessful)
            val data: PaginatedResult<Character> =
                this.body() ?: throw IllegalStateException("Body is null")
            assertEquals(
                "Same pagination info", paginatedInformation, data.information
            )
            assertEquals(
                "First Character must be Rick", firstCharacter, data.results.first()
            )
            println("${this.body()?.results?.count()}")
        }

        return@runBlocking
    }
}