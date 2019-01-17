package com.example.core.exception

import java.io.IOException

sealed class Failure : IOException() {
    var messageResource: Int? = null

    object NetworkConnection : Failure()
    data class ServerError(override val message: String?) : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}