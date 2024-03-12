package com.learnbyheart.core.nowplaying.component

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaStyleNotificationHelper
import com.learnbyheart.core.nowplaying.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val CHANNEL_ID = "media_playback"

class MediaNotificationManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MediaNotificationManager {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @OptIn(UnstableApi::class)
    override fun createNotification(session: MediaSession): Notification {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                context.resources.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationManager.createNotificationChannel(notificationChannel)
        }

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(com.learnbyheart.core.ui.R.drawable.img_music_thumbnail)
            .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
            .setStyle(MediaStyleNotificationHelper.MediaStyle(session))
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(RemoteViews(context.packageName, R.layout.layout_media_notification_small))
            .build()
    }

    override fun pushNotification(notification: Notification) {
        notificationManager.notify(1, notification)
    }
}
