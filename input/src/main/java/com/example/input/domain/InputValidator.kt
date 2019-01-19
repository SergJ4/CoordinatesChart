package com.example.input.domain

import com.example.core.di.scopes.FragmentScope
import com.example.core.exception.Failure
import io.reactivex.Single
import javax.inject.Inject

@FragmentScope
class InputValidator @Inject constructor() {

    fun validate(count: Int): Single<Boolean> = Single.just(count > 0)
}

object ValidationFailure : Failure.FeatureFailure()