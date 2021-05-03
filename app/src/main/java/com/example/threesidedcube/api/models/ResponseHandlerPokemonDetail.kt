package com.example.threesidedcube.api.models

import java.lang.Exception

/**
 * ResponseHandler from a search, which contains List<Repo> holding query data,
 * and a String of network error state.
 */
sealed class ResponseHandlerPokemonDetail {
    data class Success(val data: ResponsePokemonDetail) : ResponseHandlerPokemonDetail()
    data class Error(val error: Exception) : ResponseHandlerPokemonDetail()
}
