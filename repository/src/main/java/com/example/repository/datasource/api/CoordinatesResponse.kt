package com.example.repository.datasource.api

import com.google.gson.annotations.SerializedName

internal const val SUCCESS_CODE = 0

data class CoordinatesResponse(
    @SerializedName("response")
    val body: Response?,

    val result: Int?
)

data class Response(
    val result: Int?,
    val points: List<Point>?,
    val message: String?
)

data class Point(val x: Float, val y: Float)