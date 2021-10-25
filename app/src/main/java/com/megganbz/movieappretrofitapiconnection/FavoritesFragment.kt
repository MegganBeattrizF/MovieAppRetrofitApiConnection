package com.megganbz.movieappretrofitapiconnection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.megganbz.domain.model.characters.Characters
import com.megganbz.domain.model.characters.Image
import com.megganbz.movieappretrofitapiconnection.databinding.FragmentFavoritesBinding


class FavoritesFragment : MainActivity.FragmentController(R.layout.fragment_favorites) {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var recyclerViewFavorites: RecyclerView
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var layoutManager: GridLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observerCharactersList()
        setupRecyclerView()
    }

    private fun setupViews() {}

    private fun setupRecyclerView() {
        recyclerViewFavorites = binding.recyclerFavorites
        recyclerViewFavorites.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        val list = ArrayList<Characters>()
        list.add(
            Characters(
                0,
                "Exemplo 01",
                Image(
                    "https://image.freepik.com/fotos-gratis/imagem-aproximada-em-tons-de-cinza-de-uma-aguia-careca-americana-em-um-fundo-escuro_181624-31795",
                    ".jpg"
                )
            )
        )
        favoritesAdapter = FavoritesAdapter(list)
        recyclerViewFavorites.adapter = favoritesAdapter
    }

    private fun observerCharactersList() {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}