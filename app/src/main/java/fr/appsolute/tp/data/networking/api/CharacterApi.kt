package fr.appsolute.tp.data.networking.api

import fr.appsolute.tp.data.model.Character
import fr.appsolute.tp.data.model.PaginatedResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Definition of end points for Character Api
 */
interface CharacterApi {

    /**
     * Suspended function (must be call in a coroutine) to get paginated result to fetch characters
     *
     * @param page : Int for the current page to fetch
     *
     * @return A [Response] of [PaginatedResult] of [Character]
     */
    @GET(GET_ALL_CHARACTER_PATH)
    suspend fun getAllCharacter(
        @Query("page") page: Int
    ): Response<PaginatedResult<Character>>

    companion object {
        const val GET_ALL_CHARACTER_PATH = "character/"
    }

}