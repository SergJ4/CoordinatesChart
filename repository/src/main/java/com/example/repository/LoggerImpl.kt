package com.example.repository

import com.example.core.BuildConfig
import com.example.core.interfaces.Logger
import timber.log.Timber
import javax.inject.Inject

class LoggerImpl @Inject constructor() : Logger {

    init {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
    
    override fun logDebug(message: String, tag: String) {
        if (BuildConfig.DEBUG) {
            Timber.tag(tag).d(message)
        }
    }

    override fun logErrorIfDebug(error: Throwable) {
        if (BuildConfig.DEBUG) {
            Timber.e(error)
        }
    }
}