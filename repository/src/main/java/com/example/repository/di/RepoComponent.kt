package com.example.repository.di

import android.content.Context
import com.example.core.di.scopes.RepoScope
import com.example.core.interfaces.CoordinatesRepository
import com.example.core.interfaces.FileRepository
import com.example.core.interfaces.Logger
import dagger.BindsInstance
import dagger.Component

@RepoScope
@Component(
    modules = [RepoModule::class]
)
interface RepoComponent {

    fun provideLogger(): Logger

    fun provideCoordinatesRepo(): CoordinatesRepository

    fun provideFileRepo(): FileRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): RepoComponent
    }
}