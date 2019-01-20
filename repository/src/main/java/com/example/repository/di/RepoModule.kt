package com.example.repository.di

import android.content.Context
import com.example.core.di.scopes.RepoScope
import com.example.core.interfaces.CoordinatesRepository
import com.example.core.interfaces.FileRepository
import com.example.core.interfaces.Logger
import com.example.repository.BuildConfig
import com.example.repository.CoordinatesRepositoryImpl
import com.example.repository.FileRepositoryImpl
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
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

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
    fun coordinatesRepo(retrofit: Retrofit): CoordinatesRepository {
        val coordinatesApi = retrofit.create(CoordinatesApi::class.java)
        val apiDataSource = ApiDataSource(coordinatesApi)
        return CoordinatesRepositoryImpl(apiDataSource)
    }

    @Provides
    @RepoScope
    fun provideLogger(): Logger = LoggerImpl()

    private fun createRetrofit(
        appContext: Context,
        logger: Logger
    ): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder()

        okHttpBuilder.installUnsafeSSLCertificateManager()

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

    @Provides
    @RepoScope
    fun provideFileRepo(appContext: Context): FileRepository = FileRepositoryImpl(appContext)
}

// HIGHLY INSECURE!! DON'T DO THIS IN PRODUCTION!!
private fun OkHttpClient.Builder.installUnsafeSSLCertificateManager() {
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {

        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

        @Throws(CertificateException::class)
        override fun checkClientTrusted(
            chain: Array<java.security.cert.X509Certificate>,
            authType: String
        ) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(
            chain: Array<java.security.cert.X509Certificate>,
            authType: String
        ) {
        }
    })

    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, java.security.SecureRandom())
    val sslSocketFactory = sslContext.getSocketFactory()

    sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
    hostnameVerifier { _, _ -> true }
}
