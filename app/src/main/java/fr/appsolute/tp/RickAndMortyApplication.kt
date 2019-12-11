@file:Suppress("unused")

package fr.appsolute.tp

import android.app.Application
import fr.appsolute.tp.data.database.DatabaseManager

class RickAndMortyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initDatabase()
    }

    private fun initDatabase() {
        DatabaseManager.getInstance(this)
    }
}