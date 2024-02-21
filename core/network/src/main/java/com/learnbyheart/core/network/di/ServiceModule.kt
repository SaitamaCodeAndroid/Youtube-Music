package com.learnbyheart.core.network.di

import com.learnbyheart.core.network.BuildConfig
import com.learnbyheart.core.network.service.MusicService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {

    @Singleton
    @Provides
    fun provideAuthenticationService(): MusicService = Retrofit.Builder()
        .baseUrl(BuildConfig.AUTHENTICATION_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()
}
