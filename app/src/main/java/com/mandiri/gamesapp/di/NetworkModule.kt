package com.mandiri.gamesapp.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.mandiri.gamesapp.data.games.remote.GamesService
import com.mandiri.gamesapp.network.NetworkConnectionInterceptor
import com.mandiri.gamesapp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Named(LOGGING_INTERCEPTOR)
    fun provideLoggingInterceptor(): Interceptor {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    @Provides
    @Named(CHUCKER_INTERCEPTOR)
    fun provideChuckerInterceptor(
        @ApplicationContext appContext: Context
    ): Interceptor = ChuckerInterceptor.Builder(appContext)
        .collector(ChuckerCollector(appContext))
        .maxContentLength(MAX_CONTENT_LENGTH)
        .redactHeaders(emptySet())
        .alwaysReadResponseBody(true)
        .build()

    @Provides
    @Singleton
    @Named(OK_HTTP)
    fun provideOkHttpClient(
        @ApplicationContext appContext: Context,
        @Named(LOGGING_INTERCEPTOR) loggingInterceptor: Interceptor,
        @Named(CHUCKER_INTERCEPTOR) chuckerInterceptor: Interceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(chuckerInterceptor)
        .addInterceptor(NetworkConnectionInterceptor(appContext))
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    @Named(RETROFIT)
    fun provideRetrofit(@Named(OK_HTTP) client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideGamesService(@Named(RETROFIT) retrofit: Retrofit): GamesService {
        return retrofit.create(GamesService::class.java)
    }

    companion object {
        const val OK_HTTP = "OK_HTTP"
        const val RETROFIT = "RETROFIT"
        const val LOGGING_INTERCEPTOR = "LoggingInterceptor"
        const val CHUCKER_INTERCEPTOR = "ChuckerInterceptor"
        const val TIME_OUT = 30L
        const val MAX_CONTENT_LENGTH = 250000L
    }
}