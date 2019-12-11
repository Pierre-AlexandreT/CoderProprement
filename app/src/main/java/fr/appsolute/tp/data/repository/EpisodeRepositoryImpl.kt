package fr.appsolute.tp.data.repository

import fr.appsolute.tp.data.database.DatabaseManager
import fr.appsolute.tp.data.database.EpisodeDao
import fr.appsolute.tp.data.model.Character
import fr.appsolute.tp.data.model.Episode
import fr.appsolute.tp.data.networking.HttpClientManager
import fr.appsolute.tp.data.networking.api.EpisodeApi
import fr.appsolute.tp.data.networking.createApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private class EpisodeRepositoryImpl(
    private val api: EpisodeApi,
    private val dao: EpisodeDao
) : EpisodeRepository {

    private val episodeMutableList: MutableList<Episode> = mutableListOf()

    override suspend fun getEpisodeList(episodesId: List<Int>): List<Episode>? {
        val episodeList: List<Episode>? = withContext(Dispatchers.Default) {
            try {
                dao.selectAll()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        if (episodeList == null) {

            return withContext(Dispatchers.IO) {
                try {

                    val response = api.getAllEpisode(1)
                    check(response.isSuccessful) { "Response is not a success : code = ${response.code()}" }
                    response.body() ?: throw IllegalStateException("Body is null")
                    val page = response.body()!!.information.pages
                    var i = 1
                    while (i < page + 1) {
                        val responseList = api.getAllEpisode(i).body()?.results
                        responseList?.run {
                            episodeMutableList.addAll(responseList)
                        } ?: throw java.lang.IllegalStateException("Response is null")
                        i++
                    }

                    dao.insertAll(episodeMutableList)

                    episodeMutableList.filter {
                        episodesId.contains(it.id)
                    }


                    episodeMutableList.toList()

                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        } else return episodeList.filter {
            episodesId.contains(it.id)
        }


    }
}

/**
 * Repository of model [Character]
 */
interface EpisodeRepository {

    /**
     * Return a LiveData (Observable Design Pattern) of a Paged List of Episode
     */
    suspend fun getEpisodeList(episodesId: List<Int>): List<Episode>?

    companion object {
        fun newInstance(): EpisodeRepository {
            return EpisodeRepositoryImpl(
                HttpClientManager.instance.createApi(),
                DatabaseManager.getInstance().database.episodeDao
            )
        }
    }
}