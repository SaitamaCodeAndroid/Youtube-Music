package com.learnbyheart.core.nowplaying.component

import android.app.Notification
import androidx.media3.session.MediaSession
import com.learnbyheart.core.model.Track

interface MediaNotificationManager {

    fun createNotification(session: MediaSession): Notification

    fun pushNotification(notification: Notification)
}
