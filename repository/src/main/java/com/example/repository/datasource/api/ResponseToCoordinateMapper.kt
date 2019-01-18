package com.example.repository.datasource.api

import com.example.core.entity.Coordinate
import com.example.core.exception.Failure
import com.example.repository.R

object ResponseToCoordinateMapper {

    fun map(response: Response): List<Coordinate> {
        if (response.points == null || response.points.isEmpty()) {
            throw Failure.ServerError("").apply { messageResource = R.string.unknown_error }
        }

        return response
            .points
            .map { Coordinate(it.x, it.y) }
    }
}