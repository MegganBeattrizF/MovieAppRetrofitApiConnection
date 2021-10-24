package com.megganbz.movieappretrofitapiconnection.characters

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.megganbz.movieappretrofitapiconnection.MainActivity
import com.megganbz.movieappretrofitapiconnection.R
import com.megganbz.movieappretrofitapiconnection.databinding.FragmentCharactersBinding
import com.megganbz.movieappretrofitapiconnection.utils.*

class CharactersFragment : MainActivity.FragmentController(R.layout.fragment_characters) {
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharactersViewModel by viewModels()
    private var currentPage: Int = 1
    private var offset: Int = 0
    private val limit = 20

    private val networkStatusChecker by lazy {
        NetworkStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }
    private lateinit var recyclerViewCharacters: RecyclerView
    private lateinit var charactersAdapter: CharactersAdapter
    private lateinit var layoutManager: GridLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observerCharactersList()
        setupRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setProgressBarVisibility(visibility: Boolean) {
        binding.refreshCharacters.isRefreshing = visibility
    }

    private fun setupViews() {
        setProgressBarVisibility(true)
        binding.refreshCharacters.setOnRefreshListener {
            currentPage = 1
            offset = 0
            charactersAdapter.clearData()
            viewModel.getCharactersList(limit, offset)
        }
        viewModel.getCharactersList(limit, offset)
    }

    private fun setupRecyclerView() {
        recyclerViewCharacters = binding.recyclerViewCharacters
        layoutManager = GridLayoutManager(
            context,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        updateDataList()
        recyclerViewCharacters.layoutManager = layoutManager
    }

    private fun updateDataList() {
        recyclerViewCharacters.addOnScrolledToEnd() {
            binding.refreshCharacters.isRefreshing = true

            offset = currentPage * limit
            viewModel.getCharactersList(limit, offset)
            currentPage += 1
        }
    }

    private fun observerCharactersList() {
        networkStatusChecker.performIfConnectedToInternet {
            viewModel.charactersList.observe(viewLifecycleOwner) {
                when (it) {
                    is Success -> {
                        setProgressBarVisibility(false)
                        if (currentPage > 1) {
                            charactersAdapter.updateList(it.data)
                        } else {
                            charactersAdapter =
                                CharactersAdapter(it.data?.toCollection(arrayListOf()))
                            recyclerViewCharacters.adapter = charactersAdapter
                        }
                    }
                    is Failure -> {
                        setProgressBarVisibility(false)
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