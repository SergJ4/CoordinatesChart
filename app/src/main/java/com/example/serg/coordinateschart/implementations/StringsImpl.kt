package com.example.serg.coordinateschart.implementations

import android.content.Context
import com.example.core.interfaces.Strings
import com.example.serg.coordinateschart.R

class StringsImpl(private val appContext: Context) : Strings {
    override fun networkConnectionError(): String = appContext.getString(R.string.connection_error)

    override fun unknownErrorString(): String = appContext.getString(R.string.unknown_error)

    override fun get(stringRes: Int): String = appContext.getString(stringRes)


}