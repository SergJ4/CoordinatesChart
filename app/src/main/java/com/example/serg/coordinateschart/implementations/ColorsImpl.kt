package com.example.serg.coordinateschart.implementations

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.core.di.scopes.AppScope
import com.example.core.interfaces.Colors
import javax.inject.Inject

@AppScope
class ColorsImpl @Inject constructor(private val appContext: Context) : Colors {

    override fun get(colorInt: Int): Int = ContextCompat.getColor(appContext, colorInt)
}