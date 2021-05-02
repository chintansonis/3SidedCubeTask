package com.example.threesidedcube.api.models

import java.lang.Exception

/**
 * ResponseHandler from a search, which contains List<Repo> holding query data,
 * and a String of network error state.
 */
sealed class ResponseHandler {
    data class Success(val data: List<ResultsItem>) : ResponseHandler()
    data class Error(val error: Exception) : ResponseHandler()
}
