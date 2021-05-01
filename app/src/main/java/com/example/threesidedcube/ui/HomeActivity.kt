package com.example.threesidedcube.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import com.example.threesidedcube.R

class HomeActivity : AppCompatActivity() {
    private val navController by lazy {
        Navigation.findNavController(
            this,
            R.id.pokemon_nav_host_fragment
        )
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}