package com.example.threesidedcube.api

import com.example.threesidedcube.api.models.ResponsePokeMonList
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonWebService {
    /**
     * Get PokemonList from webservice
     */
    @GET("v2/pokemon/?")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") itemsPerPage: Int
    ): ResponsePokeMonList
}