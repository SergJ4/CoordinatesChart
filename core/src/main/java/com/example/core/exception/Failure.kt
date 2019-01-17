package com.example.core.exception

import java.io.IOException

sealed class Failure : IOException() {
    object NetworkConnection : Failure()
    object ServerError : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}