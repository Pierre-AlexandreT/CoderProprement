package fr.appsolute.tp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.observe
import fr.appsolute.tp.R
import fr.appsolute.tp.data.model.Character
import fr.appsolute.tp.ui.adapter.CharacterAdapter
import fr.appsolute.tp.ui.viewmodel.CharacterViewModel
import fr.appsolute.tp.ui.widget.holder.OnCharacterClickListener
import kotlinx.android.synthetic.main.fragment_character_list.view.*

class CharacterListFragment : Fragment(), OnCharacterClickListener {

    private lateinit var characterViewModel: CharacterViewModel
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            characterViewModel = ViewModelProvider(this, CharacterViewModel).get()
        } ?: throw IllegalStateException("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // We need to inject the OnCharacterClickListener in the constructor of the adapter
        characterAdapter = CharacterAdapter(this)
        view.character_list_recycler_view.apply {
            adapter = characterAdapter
        }
        characterViewModel.charactersPagedList.observe(this) {
            characterAdapter.submitList(it)
        }
    }

    // Implementation of OnCharacterClickListener
    override fun invoke(view: View, character: Character) {
        Toast.makeText(view.context, character.name, Toast.LENGTH_SHORT).show()
    }
}
