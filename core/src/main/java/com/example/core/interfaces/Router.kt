package com.example.core.interfaces

const val CHART_SCREEN = "chart"
const val INPUT_SCREEN = "input"

interface Router {

    fun goTo(screenName: String, data: Any? = null)

    fun rootScreen(screenName: String, data: Any? = null)

    fun back()
}