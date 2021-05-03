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
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */

    suspend fun getPokemonDetailsStream(pokemonId: String): Flow<ResponseHandlerPokemonDetail> {
        requestPokeMonDetailFromWebService()
        return pokemonDetailsResults
    }


    private suspend fun requestPokeMonDetailFromWebService(): Boolean {
        var successful = false
        try {
            val response = RestClient.getPokeMonWebService()
                .getPokemonDetails("21")
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
