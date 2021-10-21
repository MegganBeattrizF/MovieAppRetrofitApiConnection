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

    private fun setupViews() {
        viewModel.getCharactersList(20, 0)
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
        recyclerViewCharacters.addOnScrolledToEnd {
            val limit = 20
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
                        if (currentPage > 1) {
                            charactersAdapter.updateList(it.data)
                        } else {
                            charactersAdapter =
                                CharactersAdapter(it.data?.toCollection(arrayListOf()))
                            recyclerViewCharacters.adapter = charactersAdapter
                        }


                    }
                    is Failure -> {
                        when (it.error) {
                            is CredentialException -> {
                                Toast.makeText(
                                    context,
                                    it.error.getLocalizedMessage(requireContext()),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is NetworkException -> {
                                Toast.makeText(
                                    context,
                                    it.error.getLocalizedMessage(requireContext()),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is AuthorizationException -> {
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