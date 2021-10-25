package com.megganbz.movieappretrofitapiconnection.movies

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.megganbz.domain.model.movies.Crew
import com.megganbz.domain.movies.MovieDetails
import com.megganbz.movieappretrofitapiconnection.R
import com.megganbz.movieappretrofitapiconnection.databinding.ActivityDescriptionMovieBinding
import com.megganbz.movieappretrofitapiconnection.utils.*

class DescriptionMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescriptionMovieBinding
    private val viewModel: DescriptionMovieViewModel by viewModels()
    private val networkStatusChecker by lazy {
        NetworkStatusChecker(getSystemService(ConnectivityManager::class.java))
    }

    private lateinit var recyclerViewActors: RecyclerView
    private lateinit var actorsAdapter: ActorsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewBinding()
        setupView()
        setupObservers()
        setupRecyclerView()
    }

    private fun setupViewBinding() {
        binding = ActivityDescriptionMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun setupView() {
        val itemId = intent.getIntExtra("itemId", 0)
        viewModel.getMovieDescription(itemId)
        viewModel.getMovieCast(itemId)
        viewModel.getMovieCrew(itemId)
    }

    private fun setupRecyclerView() {
        recyclerViewActors = binding.recyclerViewActors
        recyclerViewActors.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )
    }

    private fun setupObservers() {
        observerMovieDetails()
        observeMovieCast()
        observerMovieCrew()
    }

    private fun observerMovieDetails() {
        networkStatusChecker.performIfConnectedToInternet {
            viewModel.moviesDescription.observe(this) {
                when (it) {
                    is Success -> {
                        setupLayout(it.data)
                    }
                    is Failure -> {
                        when (it.error) {
                            is NetworkException -> {

                            }
                            is GeneralException -> {

                            }
                        }
                    }
                }
            }
        }
    }

    private fun observeMovieCast() {
        networkStatusChecker.performIfConnectedToInternet {
            viewModel.cast.observe(this) {
                when (it) {
                    is Success -> {
                        actorsAdapter = ActorsAdapter(it.data?.toCollection(arrayListOf()))
                        recyclerViewActors.adapter = actorsAdapter
                    }
                    is Failure -> {
                        when (it.error) {
                            is NetworkException -> {

                            }
                            is GeneralException -> {

                            }
                        }
                    }
                }
            }
        }
    }

    private fun observerMovieCrew() {
        networkStatusChecker.performIfConnectedToInternet {
            viewModel.crew.observe(this) {
                when (it) {
                    is Success -> {
                        setupDirectorDetails(it.data)
                    }
                    is Failure -> {
                        when (it.error) {
                            is NetworkException -> {

                            }
                            is GeneralException -> {

                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupDirectorDetails(crewList: List<Crew>?) {
        val crew = crewList?.get(0)
        if (crew?.profile_path.isNullOrEmpty()) {
            binding.imageViewDirector.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_no_perfil
                )
            )
        } else {
            Glide
                .with(this)
                .load("https://image.tmdb.org/t/p/w342${crew?.profile_path}")
                .into(binding.imageViewDirector)
        }

        binding.textViewDirectorNameValue.text = crew?.name
    }

    private fun setupLayout(data: MovieDetails?) {
        data ?: return
        binding.textViewTitleValue.text = data.title
        binding.textViewMediaVotesValue.text = data.vote_average.toString()
        binding.ratingBarMoviePopularity.rating = (data.vote_average.div(2)).toFloat()
        binding.textViewDurationValue.text = data.runtime.toString()
        binding.textViewMovieDescription.text = data.overview
        binding.textViewDateReleaseValue.text = data.release_date
        binding.textViewBudgetValue.text = data.budget.toString()
        binding.textViewRevenueValue.text = data.revenue.toString()
        Glide
            .with(this)
            .load("https://image.tmdb.org/t/p/original${data.poster_path}")
            .into(binding.imageViewMovieCover)
    }
}