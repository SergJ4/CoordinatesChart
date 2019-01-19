package com.example.chart.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.chart.ChartFragment
import com.example.chart.ChartFragmentViewModel
import com.example.core.di.scopes.FragmentScope
import com.example.core.interfaces.Router
import dagger.Module
import dagger.Provides

@Module
class ChartFragmentModule(private val chartFragment: ChartFragment) {

    @Provides
    @FragmentScope
    fun provideViewModel(
        router: Router
    ): ChartFragmentViewModel {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                ChartFragmentViewModel(router) as T
        }
        return ViewModelProviders.of(chartFragment, factory).get(ChartFragmentViewModel::class.java)
    }
}