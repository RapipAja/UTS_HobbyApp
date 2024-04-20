package com.ubaya.hobbyapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.ubaya.hobbyapp.databinding.ActivityMainBinding
import com.ubaya.hobbyapp.R

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = (supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment).navController
        NavigationUI.setupActionBarWithNavController(this, navController)

        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.visibility = View.INVISIBLE

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

//    override fun onSupportNavigateUp(): Boolean {
////        return super.onSupportNavigateUp()
//        return NavigationUI.navigateUp(navController) || super.onSupportNavigateUp()
//    }

    fun navShow() {
        binding.bottomNav.visibility = View.VISIBLE
//        binding.navView.visibility = View.VISIBLE
    }

    fun navhide() {
        binding.bottomNav.visibility = View.INVISIBLE
//        binding.navView.visibility = View.INVISIBLE
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}