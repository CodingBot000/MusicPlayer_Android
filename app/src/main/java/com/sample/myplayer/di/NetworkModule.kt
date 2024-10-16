package com.sample.myplayer.di

import android.util.Log
import com.sample.myplayer.util.Constants
import com.sample.myplayer.util.JsonReaderApi
import com.sample.myplayer.util.JsonReaderApiImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(Constants.HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(Constants.HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(Constants.HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(HttpLoggingInterceptor {
                Log.v("HttpClientInterception", it)
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            })

        }.build()
    }


    @Singleton
    @Provides
    fun provideJsonUrlReaderApi(okhttp: OkHttpClient): JsonReaderApi
    {
        return JsonReaderApiImpl(okhttp)
    }

}

