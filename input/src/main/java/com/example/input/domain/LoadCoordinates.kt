package com.example.input.domain

import com.example.core.di.scopes.FragmentScope
import com.example.core.entity.Coordinate
import com.example.core.interfaces.CoordinatesRepository
import io.reactivex.Single
import javax.inject.Inject

@FragmentScope
class LoadCoordinates @Inject constructor(
    private val coordinatesRepository: CoordinatesRepository,
    private val inputValidator: InputValidator
) {

    operator fun invoke(count: Int): Single<List<Coordinate>> = inputValidator
        .validate(count)
        .flatMap { isValid ->
            if (isValid) {
                coordinatesRepository.getCoordinates(count)
            } else {
                Single.error(ValidationFailure)
            }
        }
}