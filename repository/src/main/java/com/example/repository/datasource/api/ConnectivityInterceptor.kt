package com.example.repository.datasource.api

import android.content.Context
import android.net.ConnectivityManager
import com.example.core.exception.Failure
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Перехватчик для определения наличия сетевого подключения на девайсе
 * и наличие ошибок диапазона 400-599 в ответе.
 * Все сгенерированные здесь исключения далее по цепочке попадают
 * в [ErrorsInterceptor]
 */
internal class ConnectivityInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //Проверяем наличие сети
        if (!context.hasNetwork()) {
            throw Failure.NetworkConnection
        }

        val builder = chain.request().newBuilder()
        val response = chain.proceed(builder.build())
        //Проверяем код ответа на наличие ошибок
        if ((response.code() >= 400) and (response.code() < 600)) {
            throw Failure.NetworkConnection
        }
        return response
    }
}

private fun Context.hasNetwork(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    val activeNetworkInfo = connectivityManager?.activeNetworkInfo
    return activeNetworkInfo?.isConnected ?: false
}