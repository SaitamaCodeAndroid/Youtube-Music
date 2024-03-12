package com.learnbyheart.core.nowplaying.di

import com.learnbyheart.core.nowplaying.component.MediaNotificationManager
import com.learnbyheart.core.nowplaying.component.MediaNotificationManagerImpl
import com.learnbyheart.core.nowplaying.component.MediaPlayer
import com.learnbyheart.core.nowplaying.component.MediaPlayerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MediaAppModule {

    @Binds
    @Singleton
    abstract fun bindMediaPlayer(mediaPlayerImpl: MediaPlayerImpl): MediaPlayer

    @Binds
    @Singleton
    abstract fun bindNotificationManager(
        notificationManagerImpl: MediaNotificationManagerImpl
    ): MediaNotificationManager
}
