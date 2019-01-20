package com.example.chart.di

import com.example.chart.ChartFragment
import com.example.core.di.scopes.FragmentScope
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [ChartFragmentModule::class])
@FragmentScope
interface ChartSubcomponent : AndroidInjector<ChartFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ChartFragment>() {

        abstract fun listModule(listFragmentModule: ChartFragmentModule): Builder

        override fun seedInstance(instance: ChartFragment) {
            listModule(ChartFragmentModule(instance))
        }
    }
}