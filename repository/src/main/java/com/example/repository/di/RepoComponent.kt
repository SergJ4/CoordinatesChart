package com.example.repository.di

import android.content.Context
import com.example.core.di.scopes.RepoScope
import dagger.BindsInstance
import dagger.Component
import java.util.logging.Logger

@RepoScope
@Component(
    modules = [RepoModule::class]
)
interface RepoComponent {

    fun provideLogger(): Logger

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): RepoComponent
    }
}