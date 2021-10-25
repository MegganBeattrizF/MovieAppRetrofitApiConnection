package com.megganbz.movieappretrofitapiconnection.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.megganbz.domain.model.movies.Cast
import com.megganbz.movieappretrofitapiconnection.R
import de.hdodenhof.circleimageview.CircleImageView

class ActorsAdapter(
    private var actorsList: ArrayList<Cast>? = arrayListOf()
) : RecyclerView.Adapter<ActorsAdapter.ActorsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_actors, parent, false)
        return ActorsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActorsViewHolder, position: Int) {
        holder.bind(actorsList?.get(position))
    }

    override fun getItemCount(): Int = actorsList?.size ?: 0

    class ActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val actorImage: CircleImageView = itemView.findViewById(R.id.actor_image)
        fun bind(cast: Cast?) {
            if (cast?.profile_path.isNullOrEmpty()) {
                actorImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        itemView.context,
                        R.drawable.ic_no_perfil
                    )
                )
            } else {
                Glide
                    .with(itemView)
                    .load("https://image.tmdb.org/t/p/w342${cast?.profile_path}")
                    .into(actorImage)
            }
        }
    }
}