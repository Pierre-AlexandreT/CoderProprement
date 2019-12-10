package fr.appsolute.tp.ui.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import fr.appsolute.tp.R
import fr.appsolute.tp.data.model.Character
import fr.appsolute.tp.ui.viewmodel.CharacterViewModel
import kotlinx.android.synthetic.main.fragment_fragment_character_detail.view.*

class FragmentCharacterDetail : Fragment() {

    private lateinit var characterViewModel: CharacterViewModel
    private var characterId = -1



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragment_character_detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.run {
            characterViewModel = ViewModelProvider(this, CharacterViewModel).get()
        } ?: throw IllegalStateException("Invalid Activity")
        characterId = arguments?.getInt(ARG_CHARACTER_ID_KEY)?: throw java.lang.IllegalStateException("No ID found")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterViewModel.getCharacter(characterId) {
            view.apply {
                this.character_name.text = it.name
            }
        }

    }

    companion object{
        const val ARG_CHARACTER_ID_KEY = "arg_character_id_key"
    }



}
