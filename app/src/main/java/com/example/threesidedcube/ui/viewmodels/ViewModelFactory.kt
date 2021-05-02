package com.example.threesidedcube.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.threesidedcube.api.PokeMonRepository

/**
 * Factory for ViewModels
 */
class ViewModelFactory(private val repository: PokeMonRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokeMonListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokeMonListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
