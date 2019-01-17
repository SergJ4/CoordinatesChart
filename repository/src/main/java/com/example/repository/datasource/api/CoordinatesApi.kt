package com.example.repository.datasource.api

import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query

const val VERSION_QUERY_PARAM = "version"
const val COUNT_QUERY_PARAM = "count"

const val COORDINATES_PATH = "mobws/json/pointsList"

interface CoordinatesApi {

    @POST(COORDINATES_PATH)
    fun fetchCoordinates(
        @Query(VERSION_QUERY_PARAM) version: String = "1.1",
        @Query(COUNT_QUERY_PARAM) count: Int
    ): Single<CoordinatesResponse>
}