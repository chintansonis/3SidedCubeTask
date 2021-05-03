package com.example.threesidedcube.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.viewpager.widget.ViewPager
import com.example.threesidedcube.R
import com.example.threesidedcube.api.models.ResponseHandlerPokemonDetail
import com.example.threesidedcube.api.models.ResponsePokemonDetail
import com.example.threesidedcube.ui.HomeActivity
import com.example.threesidedcube.ui.adapters.ViewPagerAdapter
import com.example.threesidedcube.ui.viewmodels.PokeMonDetailViewModel
import com.example.threesidedcube.utils.Functions
import com.example.threesidedcube.utils.Injection
import com.example.threesidedcube.utils.showOkDialog
import kotlinx.android.synthetic.main.include_statics.*
import kotlinx.android.synthetic.main.include_viewpager_layout.*


class PokeMonDetailFragment : Fragment() {
    private var pokemonId = ""
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
        pokeMonDetailViewModel =
            ViewModelProvider(this, Injection.provideViewModelFactoryPokeMonDetail())
                .get(PokeMonDetailViewModel::class.java)

        /**
         * receive url from fragment arguments
         */
        pokemonId =
            arguments?.getSerializable("pokemonId") as String
        Log.d("System out", "pokemonId " + pokemonId)
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
                    initViewpagerAdapter(result.data)
                    setBaseStateData(result.data)
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

    private fun setBaseStateData(data: ResponsePokemonDetail) {
        txtNameOfPokeMon.text = data.forms?.get(0)?.name
        txtHeightValue.setText(data.height.toString())
        txtWeightValue.setText(data.weight.toString())
        txtBaseExpValue.setText(data.baseExperience.toString())


        /**
         * set base stastics data
         */
        textViewHP.text = data.stats?.get(0)?.baseStat.toString()
        textViewAttack.text = data.stats?.get(1)?.baseStat.toString()
        textViewDefense.text = data.stats?.get(2)?.baseStat.toString()
        textViewSpAtk.text = data.stats?.get(3)?.baseStat.toString()
        textViewSpDef.text = data.stats?.get(4)?.baseStat.toString()
        textViewSpeed.text = data.stats?.get(5)?.baseStat.toString()

        var total = 0
        for (i in 0..data.stats!!.size - 1) {
            total += data.stats.get(i).baseStat
        }
        textViewTotal.setText(total.toString())


        /**
         * set progress bar
         */
        progressBarHP.progress = data.stats.get(0)?.baseStat
        progressBarAttack.progress = data.stats?.get(1)?.baseStat
        progressBarDefense.progress = data.stats?.get(2)?.baseStat
        progressBarSpAtk.progress = data.stats?.get(3)?.baseStat
        progressBarSpDef.progress = data.stats?.get(4)?.baseStat
        progressBarSpeed.progress = data.stats?.get(5)?.baseStat
        progressBarTotal.progress=total
    }

    private fun initViewpagerAdapter(data: ResponsePokemonDetail) {
        /**
         * add imageutl to list
         */
        val imageList = ArrayList<String>()
        imageList.add(data.sprites.frontDefault)
        imageList.add(data.sprites.backDefault)
        imageList.add(data.sprites.frontShiny)

        val viewPagerAdapter = ViewPagerAdapter(activity as HomeActivity, imageList)
        pagerSlider.setAdapter(viewPagerAdapter)
    }


}