package com.megganbz.movieappretrofitapiconnection.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.megganbz.domain.movies.Movie
import com.megganbz.movieappretrofitapiconnection.R

class MoviesAdapter(
    private var moviesList: ArrayList<Movie>? = arrayListOf()
) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_movie, parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(moviesList?.get(position))
    }

    fun updateList(data: List<Movie>?) {
        data?.let {
            moviesList?.addAll(it)
            notifyDataSetChanged()
        }

    }

    fun getItemClickId(position: Int): Int {
        val movie = moviesList?.get(position)
        return movie?.id ?: 0
    }

    fun clearData() {
        moviesList?.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = moviesList?.size ?: 0

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewMoviePoster: ImageView =
            itemView.findViewById(R.id.imageViewMoviePoster)
        private val textViewMovieTitle: TextView = itemView.findViewById(R.id.textViewMovieTitle)
        private val textViewOriginTitle: TextView = itemView.findViewById(R.id.textViewOriginTitle)
        private val textViewVotesValue: TextView = itemView.findViewById(R.id.textViewVotesValue)
        private val textViewMediaValue: TextView = itemView.findViewById(R.id.textViewMediaValue)
        private val ratingBarPopularity: RatingBar = itemView.findViewById(R.id.ratingBarPopularity)
        fun bind(movie: Movie?) {
            movie ?: return
            textViewMovieTitle.text = movie.title
            textViewOriginTitle.text = movie.original_title
            textViewVotesValue.text = movie.vote_count.toString()
            textViewMediaValue.text = movie.vote_average.toString()
            ratingBarPopularity.rating = (movie.vote_average.div(2)).toFloat()
            Glide
                .with(itemView.context)
                .load("https://image.tmdb.org/t/p/w342${movie.poster_path}")
                .into(imageViewMoviePoster)
        }
    }
}