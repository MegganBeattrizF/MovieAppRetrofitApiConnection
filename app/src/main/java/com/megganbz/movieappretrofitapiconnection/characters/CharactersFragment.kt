package com.megganbz.movieappretrofitapiconnection.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.megganbz.movieappretrofitapiconnection.MainActivity
import com.megganbz.movieappretrofitapiconnection.R
import com.megganbz.movieappretrofitapiconnection.databinding.FragmentCharactersBinding

class CharactersFragment : MainActivity.FragmentController(R.layout.fragment_characters) {
    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!
    val viewModel: CharactersViewModel by viewModels()
    private lateinit var recyclerViewCharacters : RecyclerView
    private var charactersList = ArrayList<Characters>()
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater,container, false)
        return binding.root
    }

    private fun setupRecyclerView() {
        recyclerViewCharacters = binding.recyclerViewCharacters
        recyclerViewCharacters.layoutManager = GridLayoutManager(
            context,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        val character1 = Characters("Bia", Image("https://www.recreio.com.br/images/large/2021/06/02/wandavision-1230176.jpg"))
        charactersList.add(character1)
        charactersList.add(character1)
        charactersList.add(character1)
        charactersAdapter = CharactersAdapter(charactersList)

        recyclerViewCharacters.adapter = charactersAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}