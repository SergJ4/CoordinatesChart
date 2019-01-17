package com.example.core.interfaces

import com.example.core.entity.Coordinate
import io.reactivex.Single

interface CoordinatesRepository {

    fun getCoordinates(count: Int): Single<List<Coordinate>>
}