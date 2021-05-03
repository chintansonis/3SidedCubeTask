package com.example.threesidedcube.api.repository

import com.example.threesidedcube.api.RestClient
import com.example.threesidedcube.api.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.HttpException
import java.io.IOException


/**
 * Repository class that works with local and remote data sources.
 */
class PokeMonDetailRepository {


    // shared flow of results, which allows us to broadcast updates so
    // the subscriber will have the latest data
    private val pokemonDetailsResults = MutableSharedFlow<ResponseHandlerPokemonDetail>(replay = 1)

    /**
     *
     * by passing pokemonId, we retreive the pokemon details from webservice
     */

    suspend fun getPokemonDetailsStream(pokemonId: String): Flow<ResponseHandlerPokemonDetail> {
        requestPokeMonDetailFromWebService(pokemonId)
        return pokemonDetailsResults
    }


    private suspend fun requestPokeMonDetailFromWebService(pokemonId: String): Boolean {
        var successful = false
        try {
            val response = RestClient.getPokeMonWebService()
                .getPokemonDetails(pokemonId)
            pokemonDetailsResults.emit(ResponseHandlerPokemonDetail.Success(response))
            successful = true
        } catch (exception: IOException) {
            pokemonDetailsResults.emit(ResponseHandlerPokemonDetail.Error(exception))
        } catch (exception: HttpException) {
            pokemonDetailsResults.emit(ResponseHandlerPokemonDetail.Error(exception))
        }
        return successful
    }

}
