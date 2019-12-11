package fr.appsolute.tp.data.database

import androidx.room.*
import fr.appsolute.tp.RickAndMortyApplication
import fr.appsolute.tp.data.model.Episode


@Database(
    entities = [Episode::class],
    version = 1,
    exportSchema = false
)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract val episodeDao: EpisodeDao
}

private class DatabaseManagerImpl(applicationContext: RickAndMortyApplication) : DatabaseManager {
    override val database: RickAndMortyDatabase =
        Room.databaseBuilder(
            applicationContext,
            RickAndMortyDatabase::class.java,
            "RickAndMorty.db"
        ).build()
}

interface DatabaseManager {

    val database: RickAndMortyDatabase

    companion object {
        fun newInstance(applicationContext: RickAndMortyApplication): DatabaseManager =
            DatabaseManagerImpl(applicationContext)
    }
}

@Dao
interface EpisodeDao {

    @Query("SELECT * FROM episode")
    fun selectAll(): List<Episode>


    @Insert
    fun insertAll(entities: List<Episode>)
}