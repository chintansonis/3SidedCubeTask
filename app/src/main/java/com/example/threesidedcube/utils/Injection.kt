package com.example.threesidedcube.utils
import androidx.lifecycle.ViewModelProvider
import com.example.threesidedcube.api.PokeMonRepository
import com.example.threesidedcube.api.PokemonWebService
import com.example.threesidedcube.api.RestClient
import com.example.threesidedcube.ui.viewmodels.ViewModelFactory

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    /**
     * Creates an instance of [PokeMonRepository] based on the [PokemonWebService] and a
     * [GithubLocalCache]
     */
    private fun provideGithubRepository(): PokeMonRepository {
        return PokeMonRepository()
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideGithubRepository())
    }
}
