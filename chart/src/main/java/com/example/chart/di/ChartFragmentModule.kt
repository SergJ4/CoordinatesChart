package com.example.chart.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.chart.ChartFragment
import com.example.chart.ChartFragmentViewModel
import com.example.chart.domain.SaveBitmap
import com.example.core.di.scopes.FragmentScope
import com.example.core.interfaces.Logger
import com.example.core.interfaces.Router
import com.example.core.interfaces.Strings
import dagger.Module
import dagger.Provides

@Module
class ChartFragmentModule(private val chartFragment: ChartFragment) {

    @Provides
    @FragmentScope
    fun provideViewModel(
        router: Router,
        saveBitmap: SaveBitmap,
        strings: Strings,
        logger: Logger
    ): ChartFragmentViewModel {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                ChartFragmentViewModel(router, saveBitmap, strings, logger) as T
        }
        return ViewModelProviders.of(chartFragment, factory).get(ChartFragmentViewModel::class.java)
    }
}