package fr.appsolute.tp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.appsolute.tp.data.repository.CharacterRepository

class CharacterViewModel private constructor(
    repository: CharacterRepository
) : ViewModel() {

    /**
     *  Return the paginated list of character from the API
     */
    val charactersPagedList = repository.getPaginatedList(viewModelScope)

    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharacterViewModel(CharacterRepository.instance) as T
        }
    }
}