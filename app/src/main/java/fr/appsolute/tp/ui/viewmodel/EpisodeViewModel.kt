package fr.appsolute.tp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.appsolute.tp.data.repository.EpisodeRepository
import kotlinx.coroutines.launch


class EpisodeViewModel(
    private val repository: EpisodeRepository
) : ViewModel() {

    fun getEpisodeList(idList: List<Int>) {
        viewModelScope.launch {
            repository.getEpisodeList(idList)
        }

    }

    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EpisodeViewModel(EpisodeRepository.newInstance()) as T
        }
    }


}