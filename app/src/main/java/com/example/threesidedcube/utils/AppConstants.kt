package com.example.threesidedcube.utils

object AppConstants {


    private val BASE_HOST = String.format("%s","https://pokeapi.co/api/")
    private const val IMAGE_URL="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"

    fun getBaseHost(): String {
        return BASE_HOST
    }

    /**
     * base image url for pokemon
     */
    fun getBaseImageUrl(): String {
        return IMAGE_URL
    }
}