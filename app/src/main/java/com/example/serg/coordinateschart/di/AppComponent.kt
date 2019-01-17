package com.example.serg.coordinateschart.di

import android.content.Context
import com.example.core.di.scopes.AppScope
import com.example.core.interfaces.CoordinatesRepository
import com.example.core.interfaces.Logger
import com.example.repository.di.RepoComponent
import com.example.serg.coordinateschart.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@AppScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityModule::class,
        AppModule::class
    ],
    dependencies = [RepoComponent::class]
)
interface AppComponent {
    fun inject(app: App)

    fun coordinatesRepository(): CoordinatesRepository

    fun logger(): Logger

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(context: Context): Builder

        fun repoComponent(repoComponent: RepoComponent): Builder

        fun build(): AppComponent
    }
}