package fr.appsolute.tp.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import fr.appsolute.tp.data.model.Character
import fr.appsolute.tp.ui.widget.holder.CharacterViewHolder
import fr.appsolute.tp.ui.widget.holder.OnCharacterClickListener

class CharacterAdapter(
    private val onCharacterClickListener: OnCharacterClickListener
) : PagedListAdapter<Character, CharacterViewHolder>(Companion) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.run { holder.bind(this, onCharacterClickListener) }
    }

    companion object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}