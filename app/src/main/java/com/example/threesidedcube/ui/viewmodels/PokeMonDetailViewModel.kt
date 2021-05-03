package com.example.threesidedcube.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.threesidedcube.api.repository.PokeMonDetailRepository
import com.example.threesidedcube.api.models.ResponseHandlerPokemonDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for the [PokeMonDetailFragment] screen.
 * The ViewModel works with the [PokeMonDetailRepository] to get the data.
 */
class PokeMonDetailViewModel(private val repository: PokeMonDetailRepository) : ViewModel() {
    private val pokemonIdLiveData = MutableLiveData<String>()
    val repoResult: LiveData<ResponseHandlerPokemonDetail> =pokemonIdLiveData.switchMap { pokeMonIdString ->
        liveData {
            val repos = repository.getPokemonDetailsStream(pokeMonIdString).asLiveData(Dispatchers.Main)
            emitSource(repos)
        }
    }

    fun passValueOfPokemonId(pokemonId: String) {
        pokemonIdLiveData.value=pokemonId
    }

    fun callPokemonDetailWebService() {
            viewModelScope.launch {
                repository.getPokemonDetailsStream(pokemonIdLiveData.value!!)
            }
        }
}
