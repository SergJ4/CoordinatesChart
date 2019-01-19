package com.example.core.interfaces

interface Strings {

    fun unknownErrorString(): String

    fun get(stringRes: Int): String

    fun networkConnectionError(): String
}