package com.example.repository

import android.util.Base64
import com.example.core.entity.Coordinate
import com.example.core.exception.Failure
import com.example.core.interfaces.CoordinatesRepository
import com.example.core.interfaces.Logger
import com.example.repository.datasource.api.ApiDataSource
import com.example.repository.datasource.api.ResponseToCoordinateMapper
import com.example.repository.datasource.api.SUCCESS_CODE
import io.reactivex.Single

class CoordinatesRepositoryImpl(
    private val apiDataSource: ApiDataSource,
    logger: Logger
) : CoordinatesRepository {

    override fun getCoordinates(count: Int): Single<List<Coordinate>> =
        apiDataSource
            .fetchCoordinates(count)
            .map { fullResponse ->
                when {
                    fullResponse.result == SUCCESS_CODE && fullResponse.body != null ->
                        ResponseToCoordinateMapper.map(fullResponse.body)

                    fullResponse.body?.result != null && !fullResponse.body.message.isNullOrBlank() ->
                        throw Failure.ServerError(message = fullResponse.body.message.decode())

                    TODO()
                }
            }
}

private fun String.decode(): String = String(Base64.decode(this, Base64.DEFAULT))
