package fr.appsolute.tp.data.repository

import androidx.paging.PagedList
import fr.appsolute.tp.RickAndMortyApplication
import fr.appsolute.tp.data.database.DatabaseManager
import fr.appsolute.tp.data.database.EpisodeDao
import fr.appsolute.tp.data.model.Character
import fr.appsolute.tp.data.networking.HttpClientManager
import fr.appsolute.tp.data.networking.api.CharacterApi
import fr.appsolute.tp.data.networking.createApi

private class EpiodeRepositoryImpl(
    private val api: CharacterApi,
    private val dao: EpisodeDao
) : EpisodeRepository {

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

}

/**
 * Repository of model [Character]
 */
interface EpisodeRepository {

    companion object {
        fun newInstance(application: RickAndMortyApplication): EpisodeRepository {
            return EpiodeRepositoryImpl(
                HttpClientManager.instance.createApi(),
                DatabaseManager.newInstance(application).database.episodeDao
            )
        }


    }

}