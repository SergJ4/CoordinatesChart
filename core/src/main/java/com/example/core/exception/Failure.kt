package com.example.core.exception

sealed class Failure {
    object NetworkConnection : Failure()
    data class ServerError(val message: String) : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}