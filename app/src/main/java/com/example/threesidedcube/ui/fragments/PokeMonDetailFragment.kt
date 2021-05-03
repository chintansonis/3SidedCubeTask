package com.example.threesidedcube.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.threesidedcube.R
import com.example.threesidedcube.api.models.ResponseHandler
import com.example.threesidedcube.api.models.ResponseHandlerPokemonDetail
import com.example.threesidedcube.api.models.ResultsItem
import com.example.threesidedcube.ui.HomeActivity
import com.example.threesidedcube.ui.viewmodels.PokeMonDetailViewModel
import com.example.threesidedcube.utils.Functions
import com.example.threesidedcube.utils.Injection
import com.example.threesidedcube.utils.showOkDialog


class PokeMonDetailFragment : Fragment() {
  private var pokemonId=""
    private lateinit var pokeMonDetailViewModel: PokeMonDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemon_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initviews()
    }

    private fun initviews() {
// get the view model
        pokeMonDetailViewModel = ViewModelProvider(this, Injection.provideViewModelFactoryPokeMonDetail())
            .get(PokeMonDetailViewModel::class.java)

        /**
         * receive url from fragment arguments
         */
        pokemonId =
            arguments?.getSerializable("pokemonId") as String
        Log.d("System out","pokemonId "+pokemonId)
        pokeMonDetailViewModel.passValueOfPokemonId(pokemonId)

        if (pokeMonDetailViewModel.repoResult.value == null) {
            if (Functions.isInternetConnected(requireActivity() as HomeActivity)) {
                pokeMonDetailViewModel.callPokemonDetailWebService()
            } else {
                showOkDialog(
                    (requireActivity() as HomeActivity).getString(R.string.ok),
                    "",
                    (requireActivity() as HomeActivity).resources.getString(R.string.err_no_internet_connected),
                    false
                )
            }
        }
        setData()

    }

    private fun setData() {
        /**
         * Response handler
         */
            pokeMonDetailViewModel.repoResult.observe(requireActivity() as HomeActivity) { result ->
            when (result) {
                is ResponseHandlerPokemonDetail.Success -> {

                }
                is ResponseHandlerPokemonDetail.Error -> {
                    Toast.makeText(
                        requireActivity() as HomeActivity,
                        "\uD83D\uDE28 Wooops $result.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


}