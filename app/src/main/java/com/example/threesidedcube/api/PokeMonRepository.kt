package com.example.threesidedcube.api

import android.util.Log
import com.example.threesidedcube.api.models.ResponseHandler
import com.example.threesidedcube.api.models.ResponsePokeMonList
import com.example.threesidedcube.api.models.ResultsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.HttpException
import java.io.IOException

/**
 * initial offset value for pagination
 */
private const val POKEMON_STARTING_PAGE_INDEX = 20

/**
 * Repository class that works with local and remote data sources.
 */
class PokeMonRepository {

    // keep the list of all results received
    private val inMemoryCache = mutableListOf<ResultsItem>()

    // shared flow of results, which allows us to broadcast updates so
    // the subscriber will have the latest data
    private val searchResults = MutableSharedFlow<ResponseHandler>(replay = 1)

    // keep the last requested page. When the request is successful, increment the offset number.
    private var lastRequestedPage = POKEMON_STARTING_PAGE_INDEX

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false


    companion object {
        private const val POKEMON_PAGE_LIMIT = 20
    }

/**
     * Search repositories whose names match the query, exposed as a stream of data that will emit
     * every time we get more data from the network.
     */

    suspend fun getSearchResultStream(query: String): Flow<ResponseHandler> {
        lastRequestedPage = 20
    Log.d("System out","first check test"+lastRequestedPage)
        inMemoryCache.clear()
        requestAndSaveData(query)

        return searchResults
    }

    suspend fun requestMore() {
        if (isRequestInProgress) return
        lastRequestedPage=lastRequestedPage+20
        requestAndSaveData("")
    }

    private suspend fun requestAndSaveData(query: String): Boolean {
        isRequestInProgress = true
        var successful = false
        Log.d("System out","third check test"+lastRequestedPage)
        try {
            val response = RestClient.getPokeMonWebService().getPokemonList(lastRequestedPage, POKEMON_PAGE_LIMIT)
            Log.d("poekmonRepository", "response $response")
            val repos = response.results ?: emptyList()
            inMemoryCache.addAll(repos)
            searchResults.emit(ResponseHandler.Success(inMemoryCache))
            successful = true
        } catch (exception: IOException) {
            searchResults.emit(ResponseHandler.Error(exception))
        } catch (exception: HttpException) {
            searchResults.emit(ResponseHandler.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }

    /*private fun reposByName(query: String): List<Repo> {
        // from the in memory cache select only the repos whose name or description matches
        // the query. Then order the results.
        return inMemoryCache.filter {
            it.name.contains(query, true) ||
                (it.description != null && it.description.contains(query, true))
        }.sortedWith(compareByDescending<Repo> { it.stars }.thenBy { it.name })
    }*/


}
