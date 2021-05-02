package com.example.threesidedcube.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.threesidedcube.api.PokeMonRepository
import com.example.threesidedcube.api.models.ResponseHandler
import com.example.threesidedcube.api.models.ResponsePokeMonList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for the [PokeMonSearchListFragment] screen.
 * The ViewModel works with the [PokeMonRepository] to get the data.
 */
class PokeMonListViewModel(private val repository: PokeMonRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()

    val repoResult: LiveData<ResponseHandler> = queryLiveData.switchMap { queryString ->
        liveData {
            val repos = repository.getSearchResultStream().asLiveData(Dispatchers.Main)
            emitSource(repos)
        }
    }

    /**
     * Search a repository based on a query string.
     */
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }


    /**
     * load more data when list scrolled by checking totalitemcount of recyclerview
     */
    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            viewModelScope.launch {
                repository.requestMore()
            }
        }
    }
}
