package com.example.serg.coordinateschart.di

import com.example.input.InputFragment
import com.example.input.di.InputSubcomponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [InputSubcomponent::class])
internal abstract class FragmentModule {

    @Binds
    @IntoMap
    @ClassKey(InputFragment::class)
    internal abstract fun bindInputFragmentInjectorFactory(
        builder: InputSubcomponent.Builder
    ): AndroidInjector.Factory<*>
}