package com.example.threesidedcube.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.threesidedcube.R
import com.example.threesidedcube.api.models.ResponseHandler
import com.example.threesidedcube.api.models.ResultsItem
import com.example.threesidedcube.ui.HomeActivity
import com.example.threesidedcube.ui.adapters.PokeMonRecyclerAdapter
import com.example.threesidedcube.ui.viewmodels.PokeMonListViewModel
import com.example.threesidedcube.utils.Functions
import com.example.threesidedcube.utils.Injection
import com.example.threesidedcube.utils.showOkDialog
import kotlinx.android.synthetic.main.fragment_search_pokemons.*


class PokeMonSearchListFragment : Fragment() {
    private lateinit var pokemonListViewModel: PokeMonListViewModel
    private lateinit var pokeMonRecyclerAdapter: PokeMonRecyclerAdapter

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
        // get the view model
        pokemonListViewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
            .get(PokeMonListViewModel::class.java)

        if (pokemonListViewModel.repoResult.value == null) {
            if (Functions.isInternetConnected(requireActivity() as HomeActivity)) {
                pokemonListViewModel.searchRepo("")
            } else {
                showOkDialog(
                    (requireActivity() as HomeActivity).getString(R.string.ok),
                    "",
                    (requireActivity() as HomeActivity).resources.getString(R.string.err_no_internet_connected),
                    false
                )
                showEmptyList(true)
            }
        }

        emptyList.setOnClickListener {
            /**
             * implement jetpack navigation in order to navigate the pokemon detail screen
             * References: https://github.com/android/architecture-components-samples/tree/main/NavigationBasicSample
             */
            findNavController().navigate(R.id.action_pokeMonSearchListFragment_to_pokeMonDetailFragment)
        }
        /**
         * setup screlling for pagination where load data in chunks of 20.
         */
        setupScrollListener()

        /**
         * initilization of adapter
         */
        initAdapters()

        /**
         * querying result by search
         */
        initlizeEditTextOnChangeListner()
    }

    private fun initlizeEditTextOnChangeListner() {
        edtSearchPokemon.doOnTextChanged { searchedText, start, count, after ->
            pokeMonRecyclerAdapter.filter.filter(searchedText)
        }
    }

    private fun initAdapters() {
        // add dividers between RecyclerView's row items
        val decoration =
            DividerItemDecoration(requireActivity() as HomeActivity, DividerItemDecoration.VERTICAL)
        recyclerPokemonList.addItemDecoration(decoration)

        pokeMonRecyclerAdapter =
            PokeMonRecyclerAdapter(requireActivity() as HomeActivity, emptyList())
        recyclerPokemonList.adapter = pokeMonRecyclerAdapter


        /**
         * Response handler
         */
        pokemonListViewModel.repoResult.observe(requireActivity() as HomeActivity) { result ->
            when (result) {
                is ResponseHandler.Success -> {
                    Log.d("System out", "result from api" + result.data.size)
                    showEmptyList(result.data.isEmpty())
                    pokeMonRecyclerAdapter.notifyupdatedPokeMonList(result.data as ArrayList<ResultsItem>)
                }
                is ResponseHandler.Error -> {
                    Toast.makeText(
                        requireActivity() as HomeActivity,
                        "\uD83D\uDE28 Wooops $result.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    showEmptyList(true)
                }
            }
        }
    }


    /**
     * notify ui with empty data or recyclerview as per network result capicity
     */
    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            recyclerPokemonList.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            recyclerPokemonList.visibility = View.VISIBLE
        }
    }

    private fun setupScrollListener() {
        val layoutManager = recyclerPokemonList.layoutManager as LinearLayoutManager
        recyclerPokemonList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                /**
                 * if user entered value for searching pokemon, search result would be displayed based on loaded data, therefore by checking edittext length function return from
                 * here in order to prevent pagination call
                 */
                if (edtSearchPokemon.text.toString().isNotEmpty()) return

                pokemonListViewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }
}