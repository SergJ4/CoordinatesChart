package com.example.input.di

import com.example.core.di.scopes.FragmentScope
import com.example.input.InputFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [InputFragmentModule::class])
@FragmentScope
interface InputSubcomponent : AndroidInjector<InputFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<InputFragment>() {

        abstract fun listModule(listFragmentModule: InputFragmentModule): Builder

        override fun seedInstance(instance: InputFragment) {
            listModule(InputFragmentModule(instance))
        }
    }
}