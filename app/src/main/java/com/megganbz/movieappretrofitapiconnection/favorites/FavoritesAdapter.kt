package com.megganbz.movieappretrofitapiconnection.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.megganbz.data.utils.FavoriteStatusChecker
import com.megganbz.domain.model.characters.Characters
import com.megganbz.movieappretrofitapiconnection.R

@SuppressLint("NotifyDataSetChanged")
class FavoritesAdapter(
    private val favoriteList: ArrayList<Characters>? = arrayListOf(),
    private val listenerRemoveFavorite: (Characters?) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_favorite, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(favoriteList?.get(position))
        holder.addFavoriteToggleButton.setOnCheckedChangeListener { _, _ ->
            if (FavoriteStatusChecker().isFavoriteItem(favoriteList?.get(position)?.id)) {
                holder.addFavoriteToggleButton.setBackgroundResource(R.drawable.ic_round_favorite_border_24)
                listenerRemoveFavorite(favoriteList?.get(position))
                clearListDataById(position)
            }
        }
    }

    private fun clearListDataById(favoriteId: Int) {
        if (favoriteId != -1) {
            favoriteList?.removeAt(favoriteId)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = favoriteList?.size ?: 0

    fun clearListData() {
        favoriteList?.clear()
        notifyDataSetChanged()
    }

    class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageCharacter: ImageView = itemView.findViewById(R.id.imageViewFavoritePoster)
        private val nameCharacter: TextView =
            itemView.findViewById(R.id.textViewFavoriteCharacterName)
        val addFavoriteToggleButton: ToggleButton =
            itemView.findViewById(R.id.toggleButtonFavoriteCharacter)

        fun bind(characters: Characters?) {
            characters ?: return
            if (FavoriteStatusChecker().isFavoriteItem(characters.id)) {
                addFavoriteToggleButton.setBackgroundResource(R.drawable.ic_round_favorite_24)
            } else {
                addFavoriteToggleButton.setBackgroundResource(R.drawable.ic_round_favorite_border_24)
            }
            Glide
                .with(itemView.context)
                .load(characters.thumbnail?.path + "/" + Portrait_Aspect_Ratio + ".${characters.thumbnail?.extension}")
                .into(imageCharacter)
            nameCharacter.text = characters.name
        }

        companion object {
            const val Portrait_Aspect_Ratio = "landscape_incredible"
        }
    }
}