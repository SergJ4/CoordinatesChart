package com.example.repository.di

import android.content.Context
import com.example.core.di.scopes.RepoScope
import com.example.core.interfaces.CoordinatesRepository
import com.example.core.interfaces.Logger
import com.example.repository.BuildConfig
import com.example.repository.CoordinatesRepositoryImpl
import com.example.repository.LoggerImpl
import com.example.repository.datasource.api.ApiDataSource
import com.example.repository.datasource.api.ConnectivityInterceptor
import com.example.repository.datasource.api.CoordinatesApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://demo.bankplus.ru/"
private const val HTTP_LOG_TAG = "coordinates_http"
private const val NETWORK_TIMEOUT = 6 //seconds

@Module
class RepoModule {

    @Provides
    @RepoScope
    fun coordinatesRetrofit(appContext: Context, logger: Logger): Retrofit =
        createRetrofit(appContext, logger)

    @Provides
    @RepoScope
    fun apiDataSource(coordinatesApi: CoordinatesApi, context: Context) =
        ApiDataSource(coordinatesApi, context)

    @Provides
    @RepoScope
    fun weatherRepo(
        apiDataSource: ApiDataSource,
        logger: Logger
    ): CoordinatesRepository =
        CoordinatesRepositoryImpl(
            apiDataSource,
            logger
        )

    @Provides
    @RepoScope
    fun provideLogger(): Logger = LoggerImpl()

    private fun createRetrofit(
        appContext: Context,
        logger: Logger
    ): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder()

        val connectivityInterceptor = ConnectivityInterceptor(appContext)

        okHttpBuilder.addInterceptor(connectivityInterceptor)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor { message ->
                logger.logDebug(message, HTTP_LOG_TAG)
            }
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpBuilder.addInterceptor(loggingInterceptor)
        }

        okHttpBuilder.connectTimeout(NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.writeTimeout(NETWORK_TIMEOUT.toLong(), TimeUnit.SECONDS)

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}