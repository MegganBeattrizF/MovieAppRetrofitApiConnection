package com.megganbz.movieappretrofitapiconnection.movies

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.megganbz.movieappretrofitapiconnection.MainActivity
import com.megganbz.movieappretrofitapiconnection.R
import com.megganbz.movieappretrofitapiconnection.databinding.FragmentMoviesBinding
import com.megganbz.movieappretrofitapiconnection.utils.*

class MoviesFragment : MainActivity.FragmentController(R.layout.fragment_movies) {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesViewModel by viewModels()
    private var currentPage: Int = 1

    private val networkStatusChecker by lazy {
        NetworkStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }
    private lateinit var recyclerViewMovies: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observerMoviesList()
        setupRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupViews() {
        binding.refreshMovies.setOnRefreshListener {
            currentPage = 1
            moviesAdapter.clearData()
            viewModel.getPopularMoviesList(currentPage)
        }
        viewModel.getPopularMoviesList(currentPage)
    }

    private fun setupRecyclerView() {
        recyclerViewMovies = binding.recyclerViewMovies
        layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.VERTICAL, false
        )
        updateDataList()
        recyclerViewMovies.layoutManager = layoutManager
    }

    private fun updateDataList() {
        recyclerViewMovies.addOnScrolledToEnd() {
            binding.refreshMovies.isRefreshing = true
            currentPage += 1
            viewModel.getPopularMoviesList(currentPage)
        }
    }

    private fun observerMoviesList() {
        networkStatusChecker.performIfConnectedToInternet {
            viewModel.moviesList.observe(viewLifecycleOwner) {
                when (it) {
                    is Success -> {
                        binding.refreshMovies.isRefreshing = false
                        if (currentPage > 1) {
                            moviesAdapter.updateList(it.data)
                        } else {
                            moviesAdapter = MoviesAdapter(it.data?.toCollection(arrayListOf()))
                            recyclerViewMovies.adapter = moviesAdapter
                        }
                    }
                    is Failure -> {
                        when (it.error) {
                            is NetworkException -> {
                                Toast.makeText(
                                    context,
                                    it.error.getLocalizedMessage(requireContext()),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is GeneralException -> {
                                Toast.makeText(
                                    context,
                                    it.error.getLocalizedMessage(requireContext()),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}