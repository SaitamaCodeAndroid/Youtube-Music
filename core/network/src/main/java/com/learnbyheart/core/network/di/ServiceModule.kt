package com.learnbyheart.core.network.di

import com.learnbyheart.core.network.BuildConfig
import com.learnbyheart.core.network.service.AuthenticationService
import com.learnbyheart.core.network.service.MusicService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addNetworkInterceptor { chain ->
                val request = chain
                    .request()
                    .newBuilder()
                    .build()
                chain.proceed(request)
            }.build()
    }

    @Provides
    @Singleton
    fun provideAuthenticationService(
        okHttpClient: OkHttpClient
    ): AuthenticationService = Retrofit.Builder()
        .baseUrl(BuildConfig.AUTHENTICATION_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()

    @Provides
    @Singleton
    fun provideMusicService(
        okHttpClient: OkHttpClient
    ): MusicService = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()
}
