package com.megganbz.movieappretrofitapiconnection.favorites

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.megganbz.movieappretrofitapiconnection.MainActivity
import com.megganbz.movieappretrofitapiconnection.R
import com.megganbz.movieappretrofitapiconnection.databinding.FragmentFavoritesBinding
import com.megganbz.movieappretrofitapiconnection.utils.Failure
import com.megganbz.movieappretrofitapiconnection.utils.GeneralException
import com.megganbz.movieappretrofitapiconnection.utils.Success


class FavoritesFragment : MainActivity.FragmentController(R.layout.fragment_favorites) {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var recyclerViewFavorites: RecyclerView
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observerCharactersList()
        setupRecyclerView()
    }

    private fun setupViews() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarFavorites)
        setProgressBarVisibility(true)
        binding.refreshFavoriteCharacters.setOnRefreshListener {
            favoritesAdapter.clearListData()
            viewModel.getFavoriteCharactersList()
        }
        viewModel.getFavoriteCharactersList()
    }

    private fun setupRecyclerView() {
        recyclerViewFavorites = binding.recyclerFavorites
        recyclerViewFavorites.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
    }

    private fun setProgressBarVisibility(visibility: Boolean) {
        binding.refreshFavoriteCharacters.isRefreshing = visibility
    }

    private fun observerCharactersList() {
        viewModel.favoriteCharactersList.observe(viewLifecycleOwner) {
            when (it) {
                is Success -> {
                    setProgressBarVisibility(false)
                    favoritesAdapter =
                        FavoritesAdapter(it.data?.toCollection(arrayListOf())) { characters ->
                            characters?.id?.let { it1 -> viewModel.removeCharactersById(it1) }
                        }
                    recyclerViewFavorites.adapter = favoritesAdapter
                }
                is Failure -> {
                    setProgressBarVisibility(false)
                    when (it.error) {
                        is GeneralException -> {

                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_favorites_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clearAllData -> {
                viewModel.removeAllCharacters()
                favoritesAdapter.clearListData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}