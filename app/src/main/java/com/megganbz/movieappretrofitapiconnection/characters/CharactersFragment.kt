package com.megganbz.movieappretrofitapiconnection.characters

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.megganbz.movieappretrofitapiconnection.MainActivity
import com.megganbz.movieappretrofitapiconnection.R
import com.megganbz.movieappretrofitapiconnection.utils.NetworkStatusChecker
import com.megganbz.movieappretrofitapiconnection.databinding.FragmentCharactersBinding

class CharactersFragment : MainActivity.FragmentController(R.layout.fragment_characters) {
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharactersViewModel by viewModels()

    private val networkStatusChecker by lazy {
        NetworkStatusChecker(activity?.getSystemService(ConnectivityManager::class.java))
    }

    private lateinit var recyclerViewCharacters: RecyclerView
    private lateinit var charactersAdapter: CharactersAdapter

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
        viewModel.getCharactersList()
    }

    private fun setupRecyclerView() {
        recyclerViewCharacters = binding.recyclerViewCharacters
        recyclerViewCharacters.layoutManager = GridLayoutManager(
            context,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
    }

    private fun observerCharactersList() {
        networkStatusChecker.performIfConnectedToInternet {
            viewModel.charactersList.observe(viewLifecycleOwner) {
                charactersAdapter = CharactersAdapter(it)
                recyclerViewCharacters.adapter = charactersAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}