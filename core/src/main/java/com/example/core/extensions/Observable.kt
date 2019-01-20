package com.example.core.extensions

import com.example.core.SchedulersProvider
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

fun <T> Single<T>.async() = this
    .subscribeOn(SchedulersProvider.io())
    .observeOn(SchedulersProvider.ui())

fun Completable.async() = this
    .subscribeOn(SchedulersProvider.io())
    .observeOn(SchedulersProvider.ui())

fun <T> Observable<T>.async() = this
    .subscribeOn(SchedulersProvider.io())
    .observeOn(SchedulersProvider.ui())