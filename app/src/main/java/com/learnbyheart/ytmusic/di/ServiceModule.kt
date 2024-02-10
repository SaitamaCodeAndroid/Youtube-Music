package com.learnbyheart.ytmusic.di

import com.learnbyheart.spotify.BuildConfig
import com.learnbyheart.ytmusic.data.remote.service.AuthenticationService
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
    fun provideAuthenticationService(): AuthenticationService = Retrofit.Builder()
        .baseUrl(BuildConfig.AUTHENTICATION_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()
}
