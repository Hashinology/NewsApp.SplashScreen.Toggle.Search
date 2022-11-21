package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpViewModel()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val navHost =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment

        val navController = navHost.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{ controller, destination, arguments ->
            when (destination.id) {
                R.id.detailsFragment -> hideBottomNav()
                else -> showBottomNav()
            }

        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MainViewModel::class.java]

    }

    private fun hideBottomNav() {
        binding.bottomNavigationView.visibility = View.INVISIBLE
    }

    private fun showBottomNav() {
        binding.bottomNavigationView.visibility = View.VISIBLE
    }
}