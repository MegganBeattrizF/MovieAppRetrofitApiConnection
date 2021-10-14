package com.megganbz.movieappretrofitapiconnection.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.megganbz.domain.model.Characters
import com.megganbz.movieappretrofitapiconnection.R

class CharactersAdapter(
    private var charactersList: List<Characters>
) : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_characters, parent, false)
        return CharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(charactersList[position])
    }

    override fun getItemCount(): Int = charactersList.size

    inner class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageCharacter: ImageView = itemView.findViewById(R.id.imageViewCharacter)
        private val nameCharacter: TextView = itemView.findViewById(R.id.textViewCharacterName)

        fun bind(character: Characters) {
            Glide
                .with(itemView.context)
                .load(character.thumbnail?.path + ".${character.thumbnail?.extension}")
                .into(imageCharacter)
            nameCharacter.text = character.name
        }
    }
}