package com.learnbyheart.ytmusic.di

import com.learnbyheart.ytmusic.internet.NetworkMonitor
import com.learnbyheart.ytmusic.internet.NetworkMonitorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideNetworkMonitor(networkMonitorImpl: NetworkMonitorImpl): NetworkMonitor
}
