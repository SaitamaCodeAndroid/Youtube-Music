package com.learnbyheart.core.nowplaying.service

import android.content.Intent
import android.os.Bundle
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.ForwardingPlayer
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaNotification
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ConnectionResult
import androidx.media3.session.MediaSession.ConnectionResult.AcceptedResultBuilder
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.ListenableFuture
import com.learnbyheart.core.nowplaying.R
import com.learnbyheart.core.nowplaying.component.MediaNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val COMMAND_PREVIOUS = "previous"
private const val COMMAND_PLAY = "play"

@AndroidEntryPoint
class PlaybackService : MediaSessionService(), MediaSession.Callback {

    @Inject
    lateinit var notificationManager: MediaNotificationManager

    private var mediaSession: MediaSession? = null

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()

        val audioAttribute = AudioAttributes
            .Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()
        val player = ExoPlayer.Builder(this).build().also {
            it.setAudioAttributes(audioAttribute, true)
        }
        val forwardingPlayer = object : ForwardingPlayer(player) {
            override fun play() {
                super.play()
            }

            override fun setPlayWhenReady(playWhenReady: Boolean) {
                super.setPlayWhenReady(playWhenReady)
            }
        }

        val mediaNotificationProvider = object : MediaNotification.Provider {
            override fun createNotification(
                mediaSession: MediaSession,
                customLayout: ImmutableList<CommandButton>,
                actionFactory: MediaNotification.ActionFactory,
                onNotificationChangedCallback: MediaNotification.Provider.Callback
            ): MediaNotification {
                val notification = notificationManager.createNotification(mediaSession)
                notificationManager.pushNotification(notification)
                return MediaNotification(1, notification)
            }

            override fun handleCustomCommand(
                session: MediaSession,
                action: String,
                extras: Bundle
            ): Boolean {
                TODO("Not yet implemented")
            }

        }
        setMediaNotificationProvider(mediaNotificationProvider)

        val previousButton = CommandButton.Builder()
            .setDisplayName("Previous")
            .setIconResId(R.drawable.ic_skip_previous)
            .setSessionCommand(SessionCommand(COMMAND_PREVIOUS, Bundle()))
            .build()
        val playButton = CommandButton.Builder()
            .setDisplayName("Next")
            .setIconResId(R.drawable.ic_skip_next)
            .setSessionCommand(SessionCommand(COMMAND_PLAY, Bundle()))
            .build()

        mediaSession = MediaSession.Builder(this, forwardingPlayer)
            .setCustomLayout(ImmutableList.of(previousButton, playButton))
            .setCallback(CustomMediaSessionCallback())
            .build()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        if (mediaSession?.player?.playWhenReady == true) {
            mediaSession?.player?.pause()
        }
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    private inner class CustomMediaSessionCallback: MediaSession.Callback {
        @OptIn(UnstableApi::class)
        override fun onConnect(
            session: MediaSession,
            controller: MediaSession.ControllerInfo
        ): ConnectionResult {
            val sessionCommands = ConnectionResult.DEFAULT_SESSION_COMMANDS.buildUpon()
                .add(SessionCommand(COMMAND_PREVIOUS, Bundle.EMPTY))
                .add(SessionCommand(COMMAND_PLAY, Bundle.EMPTY))
                .build()
            return AcceptedResultBuilder(session)
                .setAvailableSessionCommands(sessionCommands)
                .build()
        }

        override fun onCustomCommand(
            session: MediaSession,
            controller: MediaSession.ControllerInfo,
            customCommand: SessionCommand,
            args: Bundle
        ): ListenableFuture<SessionResult> {

            return super.onCustomCommand(session, controller, customCommand, args)
        }
    }
}
