package com.example.input.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.core.di.scopes.FragmentScope
import com.example.core.interfaces.Logger
import com.example.core.interfaces.Router
import com.example.core.interfaces.Strings
import com.example.input.InputFragment
import com.example.input.InputFragmentViewModel
import com.example.input.domain.LoadCoordinates
import dagger.Module
import dagger.Provides

@Module
class InputFragmentModule(private val inputFragment: InputFragment) {

    @Provides
    @FragmentScope
    fun provideViewModel(
        router: Router,
        strings: Strings,
        logger: Logger,
        loadCoordinates: LoadCoordinates
    ): InputFragmentViewModel {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                InputFragmentViewModel(router, loadCoordinates, strings, logger) as T
        }
        return ViewModelProviders.of(inputFragment, factory).get(InputFragmentViewModel::class.java)
    }
}