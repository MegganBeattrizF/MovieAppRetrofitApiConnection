package com.megganbz.movieappretrofitapiconnection.characters

import android.annotation.SuppressLint
import android.os.SystemClock
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
class CharactersAdapter(
    private var charactersList: ArrayList<Characters>? = arrayListOf(),
    private val listenerAddToFavorites: (Characters?, MutableList<Int>) -> Unit,
    private val listenerRemoveToFavorites: (Characters?) -> Unit
) : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {
    private val idCharacterList: MutableList<Int> = mutableListOf()
    private var mLastClickTime: Long = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_characters, parent, false)
        return CharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(charactersList?.get(position))
        holder.addFavoriteToggleButton.setOnCheckedChangeListener { _, _ ->
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {
                return@setOnCheckedChangeListener
            }
            mLastClickTime = SystemClock.elapsedRealtime()
            setFavoriteFavoriteCharacter(holder, position)
        }
    }

    private fun setFavoriteFavoriteCharacter(
        holder: CharactersViewHolder,
        position: Int
    ) {
        if (!FavoriteStatusChecker().isFavoriteItem(charactersList?.get(position)?.id)) {
            holder.addFavoriteToggleButton.setBackgroundResource(R.drawable.ic_round_favorite_24)
            idCharacterList.add(charactersList?.get(position)?.id ?: 0)
            listenerAddToFavorites(charactersList?.get(position), idCharacterList)
        } else {
            holder.addFavoriteToggleButton.setBackgroundResource(R.drawable.ic_round_favorite_border_24)
            listenerRemoveToFavorites(charactersList?.get(position))
            idCharacterList.remove(charactersList?.get(position)?.id)
        }
    }

    override fun getItemCount(): Int = charactersList?.size ?: 0

    fun updateListData(data: List<Characters>?) {
        data?.let {
            charactersList?.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun clearListData() {
        charactersList?.clear()
        notifyDataSetChanged()
    }

    inner class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageCharacter: ImageView = itemView.findViewById(R.id.imageViewCharacter)
        private val nameCharacter: TextView = itemView.findViewById(R.id.textViewCharacterName)
        val addFavoriteToggleButton: ToggleButton =
            itemView.findViewById(R.id.toggleButtonAddFavoriteCharacter)

        fun bind(character: Characters?) {
            character ?: return

            if (FavoriteStatusChecker().isFavoriteItem(character.id)) {
                idCharacterList.add(character.id ?: 0)
                addFavoriteToggleButton.setBackgroundResource(R.drawable.ic_round_favorite_24)
            } else {
                addFavoriteToggleButton.setBackgroundResource(R.drawable.ic_round_favorite_border_24)
            }
            Glide
                .with(itemView.context)
                .load(character.thumbnail?.path + "/" + PORTRAIT_ASPECT_RATIO + ".${character.thumbnail?.extension}")
                .into(imageCharacter)
            nameCharacter.text = character.name
        }
    }

    companion object {
        const val PORTRAIT_ASPECT_RATIO = "portrait_uncanny"
    }
}