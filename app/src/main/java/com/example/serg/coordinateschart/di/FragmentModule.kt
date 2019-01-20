package com.example.serg.coordinateschart.di

import com.example.chart.ChartFragment
import com.example.chart.di.ChartSubcomponent
import com.example.input.InputFragment
import com.example.input.di.InputSubcomponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [InputSubcomponent::class, ChartSubcomponent::class])
internal abstract class FragmentModule {

    @Binds
    @IntoMap
    @ClassKey(InputFragment::class)
    internal abstract fun bindInputFragmentInjectorFactory(
        builder: InputSubcomponent.Builder
    ): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ClassKey(ChartFragment::class)
    internal abstract fun bindChartFragmentInjectorFactory(
        builder: ChartSubcomponent.Builder
    ): AndroidInjector.Factory<*>
}