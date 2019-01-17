package com.example.serg.coordinateschart.implementations

import com.example.core.interfaces.Router
import ru.terrakok.cicerone.Screen

internal class RouterImpl(private val router: ru.terrakok.cicerone.Router) :
    Router {

    override fun goTo(screenName: String, data: Any?) {
        router.navigateTo(convertNameToScreen(screenName, data))
    }

    override fun rootScreen(screenName: String, data: Any?) {
        router.newRootScreen(convertNameToScreen(screenName, data))
    }

    override fun back() = router.exit()

    private fun convertNameToScreen(screenName: String, data: Any?): Screen =
        when (screenName) {
            else -> throw IllegalArgumentException("unknown screen name: $screenName")
        }
}