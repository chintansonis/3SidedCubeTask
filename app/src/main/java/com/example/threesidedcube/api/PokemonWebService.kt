package com.example.threesidedcube.api

import com.example.threesidedcube.api.models.ResponsePokeMonList
import com.example.threesidedcube.api.models.ResponsePokemonDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonWebService {
    /**
     * Get PokemonList from webservice
     */
    @GET("v2/pokemon/?")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") itemsPerPage: Int
    ): ResponsePokeMonList


    /**
     * Get Pokemondetails from webservice
     */
    @GET("v2/pokemon/{pokemonId}/")
    suspend fun getPokemonDetails(@Path("pokemonId") pokemonId:String): ResponsePokemonDetail
}