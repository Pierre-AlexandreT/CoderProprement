package fr.appsolute.tp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import fr.appsolute.tp.data.model.Character
import fr.appsolute.tp.data.networking.HttpClientManager
import fr.appsolute.tp.data.networking.api.CharacterApi
import fr.appsolute.tp.data.networking.createApi
import fr.appsolute.tp.data.networking.datasource.CharacterDataSource
import kotlinx.coroutines.CoroutineScope

private class CharacterRepositoryImpl(
    private val api: CharacterApi
) : CharacterRepository {
    /**
     * Config for pagination
     */
    private val paginationConfig = PagedList.Config
        .Builder()
        // If you set true you will have to catch
        // the place holder case in the adapter
        .setEnablePlaceholders(false)
        .setPageSize(20)
        .build()

    override fun getPaginatedList(scope: CoroutineScope): LiveData<PagedList<Character>> {
        return LivePagedListBuilder(
            CharacterDataSource.Factory(api, scope),
            paginationConfig
        ).build()
    }
}

/**
 * Repository of model [Character]
 */
interface CharacterRepository {

    /**
     * Return a LiveData (Observable Design Pattern) of a Paged List of Character
     */
    fun getPaginatedList(scope: CoroutineScope): LiveData<PagedList<Character>>

    companion object {
        /**
         * Singleton for the interface [CharacterRepository]
         */
        val instance: CharacterRepository by lazy {
            // Lazy means "When I need it" so here this block will be launch
            // the first time you need the instance,
            // then, the reference will be stored in the value `instance`
            CharacterRepositoryImpl(HttpClientManager.instance.createApi())
        }
    }

}