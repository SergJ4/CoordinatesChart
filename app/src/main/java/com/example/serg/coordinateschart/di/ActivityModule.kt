package com.example.serg.coordinateschart.di

import com.example.serg.coordinateschart.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [MainActivitySubcomponent::class])
abstract class ActivityModule {

    @Binds
    @IntoMap
    @ClassKey(MainActivity::class)
    internal abstract fun bindMainActivityInjectorFactory(
        builder: MainActivitySubcomponent.Builder
    ): AndroidInjector.Factory<*>
}