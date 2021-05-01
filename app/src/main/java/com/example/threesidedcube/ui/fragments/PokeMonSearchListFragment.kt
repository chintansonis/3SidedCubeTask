package com.example.threesidedcube.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.threesidedcube.R
import kotlinx.android.synthetic.main.fragment_search_pokemons.*
import kotlin.collections.emptyList


class PokeMonSearchListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_pokemons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initviews()
    }

    private fun initviews() {
   emptyList.setOnClickListener {
       findNavController().navigate(R.id.action_pokeMonSearchListFragment_to_pokeMonDetailFragment)
   }
    }



}