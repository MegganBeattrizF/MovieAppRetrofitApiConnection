package com.megganbz.movieappretrofitapiconnection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.megganbz.domain.model.characters.Characters

class FavoritesAdapter(
    private val favoriteList: ArrayList<Characters>? = arrayListOf()
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_favorite, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(favoriteList?.get(position))
    }

    override fun getItemCount(): Int = favoriteList?.size ?: 0

    class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageCharacter: ImageView = itemView.findViewById(R.id.imageViewFavoritePoster)
        private val nameCharacter: TextView =
            itemView.findViewById(R.id.textViewFavoriteCharacterName)
        private val addFavoriteToggleButton: ToggleButton =
            itemView.findViewById(R.id.toggleButtonFavoriteCharacter)

        fun bind(characters: Characters?) {
            characters ?: return
            addFavoriteToggleButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    addFavoriteToggleButton.setBackgroundResource(R.drawable.ic_round_favorite_24)
                } else {
                    addFavoriteToggleButton.setBackgroundResource(R.drawable.ic_round_favorite_border_24)
                }
            }
            Glide
                .with(itemView.context)
                .load(characters.thumbnail?.path + ".${characters.thumbnail?.extension}")
                .into(imageCharacter)
            nameCharacter.text = characters.name
        }
    }
}