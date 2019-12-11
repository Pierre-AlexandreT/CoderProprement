package fr.appsolute.tp.ui.viewmodel

import androidx.lifecycle.AndroidViewModel
import fr.appsolute.tp.RickAndMortyApplication
import fr.appsolute.tp.data.repository.EpisodeRepository


class EpisodeViewModel(application: RickAndMortyApplication) : AndroidViewModel(application) {
    val repository: EpisodeRepository = EpisodeRepository.newInstance(application)

    class Factory
}