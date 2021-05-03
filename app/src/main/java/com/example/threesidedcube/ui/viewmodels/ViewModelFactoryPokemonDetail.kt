package com.example.threesidedcube.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.threesidedcube.api.repository.PokeMonDetailRepository
import com.example.threesidedcube.api.repository.PokeMonRepository

/**
 * Factory for ViewModels
 */
class ViewModelFactoryPokemonDetail(private val repository: PokeMonDetailRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokeMonDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PokeMonDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
