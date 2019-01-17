package com.example.repository

import com.example.core.entity.Coordinate
import com.example.core.interfaces.CoordinatesRepository
import com.example.core.interfaces.Logger
import com.example.repository.datasource.api.ApiDataSource
import io.reactivex.Single

class CoordinatesRepositoryImpl(
    apiDataSource: ApiDataSource,
    logger: Logger
) : CoordinatesRepository {

    override fun getCoordinates(count: Int): Single<List<Coordinate>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}