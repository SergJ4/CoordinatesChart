package com.example.serg.coordinateschart.di

import android.content.Context
import com.example.core.di.scopes.AppScope
import com.example.core.interfaces.Colors
import com.example.core.interfaces.Logger
import com.example.core.interfaces.Strings
import com.example.serg.coordinateschart.implementations.ColorsImpl
import com.example.serg.coordinateschart.implementations.RouterImpl
import com.example.serg.coordinateschart.implementations.StringsImpl
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

@Module
class AppModule {

    @Provides
    @AppScope
    fun provideCicerone() = Cicerone.create()

    @Provides
    @AppScope
    fun provideCiceroneRouter(cicerone: Cicerone<Router>) =
        cicerone.router

    @Provides
    @AppScope
    fun provideNavigationHolder(cicerone: Cicerone<Router>) =
        cicerone.navigatorHolder

    @Provides
    @AppScope
    fun provideRouter(ciceroneRouter: ru.terrakok.cicerone.Router): com.example.core.interfaces.Router {
        return RouterImpl(ciceroneRouter)
    }

    @Provides
    @AppScope
    fun colors(appContext: Context): Colors = ColorsImpl(appContext)

    @Provides
    @AppScope
    fun strings(appContext: Context): Strings = StringsImpl(appContext)

    @Provides
    @AppScope
    fun logger(): Logger = com.example.repository.LoggerImpl()
}