package com.example.core.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coordinate(val x: Float, val y: Float) : Parcelable