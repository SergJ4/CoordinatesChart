package com.example.repository.datasource.api

import io.reactivex.Single

/**
 * This class provides access to server data
 * Also could be DB data source, but it is unnecessary in this demo project
 */
class ApiDataSource(private val coordinatesApi: CoordinatesApi) {

    fun fetchCoordinates(count: Int): Single<CoordinatesResponse> =
        coordinatesApi.fetchCoordinates(count = count)
}