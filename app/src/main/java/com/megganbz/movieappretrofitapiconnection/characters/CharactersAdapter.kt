package com.megganbz.movieappretrofitapiconnection.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.megganbz.domain.model.characters.Characters
import com.megganbz.movieappretrofitapiconnection.R


class CharactersAdapter(
    private var charactersList: ArrayList<Characters>? = arrayListOf()
) : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_characters, parent, false)
        return CharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(charactersList?.get(position))
    }

    override fun getItemCount(): Int = charactersList?.size ?: 0

    fun updateList(data: List<Characters>?) {
        data?.let {
            charactersList?.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun clearData() {
        charactersList?.clear()
        notifyDataSetChanged()
    }

    inner class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageCharacter: ImageView = itemView.findViewById(R.id.imageViewCharacter)
        private val nameCharacter: TextView = itemView.findViewById(R.id.textViewCharacterName)
        private val addFavoriteToggleButton: ToggleButton =
            itemView.findViewById(R.id.toggleButtonAddFavoriteCharacter)

        fun bind(character: Characters?) {
            character ?: return
            addFavoriteToggleButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    addFavoriteToggleButton.setBackgroundResource(R.drawable.ic_round_favorite_24)
                } else {
                    addFavoriteToggleButton.setBackgroundResource(R.drawable.ic_round_favorite_border_24)
                }
            }
            Glide
                .with(itemView.context)
                .load(character.thumbnail?.path + ".${character.thumbnail?.extension}")
                .into(imageCharacter)
            nameCharacter.text = character.name
        }
    }
}