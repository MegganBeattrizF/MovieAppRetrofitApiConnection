package com.megganbz.movieappretrofitapiconnection

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.megganbz.movieappretrofitapiconnection.characters.CharactersFragment
import com.megganbz.movieappretrofitapiconnection.databinding.ActivityMainBinding
import com.megganbz.movieappretrofitapiconnection.movies.MoviesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewBinding()

        setupNavController()
        setupBottomNavigation()

        binding.textMoviesMenu.setOnClickListener {
            binding.textMoviesMenu.typeface =
                ResourcesCompat.getFont(applicationContext, R.font.roboto_bold)
            binding.textCharactersMenu.typeface =
                ResourcesCompat.getFont(applicationContext, R.font.roboto)
            binding.textCharactersMenu.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
            binding.textMoviesMenu.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22F)
            navController.navigate(R.id.action_charactersFragment_to_moviesFragment)
        }
        binding.textCharactersMenu.setOnClickListener {
            binding.textCharactersMenu.typeface =
                ResourcesCompat.getFont(applicationContext, R.font.roboto_bold)
            binding.textMoviesMenu.typeface =
                ResourcesCompat.getFont(applicationContext, R.font.roboto)
            binding.textCharactersMenu.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22F)
            binding.textMoviesMenu.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
            navController.navigate(R.id.action_moviesFragment_to_charactersFragment)
        }
    }

    private fun setupNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_fragment_container
        ) as NavHostFragment
        navController = navHostFragment.findNavController()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun setupViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun showMenuSearchBar() {
        binding.toolbar.isVisible = true
        binding.toolbar.animate()
            .translationY(0F)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }

    fun hideMenuSearchBar() {
        binding.toolbar.isVisible = false
        binding.toolbar.animate()
            .translationY(-binding.toolbar.height.toFloat())
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }

    open class FragmentController(contentLayoutId: Int) : Fragment(contentLayoutId) {
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            setupMenuSearchBar()
        }

        private fun setupMenuSearchBar() {
            when (this) {
                is MoviesFragment -> (activity as MainActivity).showMenuSearchBar()
                is CharactersFragment -> (activity as MainActivity).showMenuSearchBar()
                else -> (activity as MainActivity).hideMenuSearchBar()
            }
        }
    }
}