package com.learnbyheart.core.nowplaying

import android.content.ComponentName
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.ui.PlayerView
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.learnbyheart.core.model.TrackDisplayData
import com.learnbyheart.core.nowplaying.service.PlaybackService
import com.learnbyheart.core.ui.Grey808080
import com.learnbyheart.core.ui.Grey898989

@OptIn(UnstableApi::class)
@Composable
fun NowPlayingScreen(
    /*exoPlayer: ExoPlayer? = null,
    mediaItemIndex: Int = 0,
    playWhenReady: Boolean = true,
    playbackPosition: Long = 0,*/
) {
    val data = TrackDisplayData(
        id = "",
        name = "Where Are Ü Now",
        musicUrl = "https://p.scdn.co/mp3-preview/baf97fea2e3e1c97092ac69426691b703d20d0e2?cid=5782832bc0c14ac5a4b74b7aff9a8307",
        image = "https://i.scdn.co/image/ab67616d0000b27357fc4730e06c9ab20c1e073b",
        artists = "Justin Bieber"
    )

    val lifecycleOwner = LocalLifecycleOwner.current

    var playWhenReady = true
    var mediaItemIndex = 0
    var playbackPosition = 0L

    var controllerFuture: ListenableFuture<MediaController>? = null
    var lifecycleEvent by remember {
        mutableStateOf(Lifecycle.Event.ON_ANY)
    }

    DisposableEffect(key1 = lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            lifecycleEvent = event
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var tabSelected by remember { mutableIntStateOf(0) }
        val tabList = NowPlayingType.entries

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(R.drawable.ic_collapse),
                    contentDescription = null,
                    tint = Color.White
                )
            }

            TabRow(
                modifier = Modifier
                    .width(150.dp)
                    .clip(RoundedCornerShape(20.dp)),
                selectedTabIndex = tabSelected,
                indicator = {
                    SecondaryIndicator(
                        color = Color.Transparent
                    )
                },
                divider = {
                    HorizontalDivider(color = Color.Transparent)
                },
                containerColor = Color.Black,
                contentColor = Color.White
            ) {

                tabList.forEachIndexed { index, tab ->
                    Tab(
                        modifier = Modifier
                            .height(40.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .conditional(tabSelected == index) {
                                background(color = Color.Cyan)
                            },
                        selected = tabSelected == index,
                        text = {
                            Text(
                                text = tab.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        onClick = { tabSelected = index }
                    )
                }
            }

            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(R.drawable.ic_more),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        /*AsyncImage(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    start = 24.dp,
                    end = 24.dp
                )
                .size(320.dp)
                .clip(RoundedCornerShape(16.dp)),
            model = data.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = com.learnbyheart.core.ui.R.drawable.img_music_thumbnail)
        )*/

        AndroidView(
            factory = { context ->
                val sessionToken = SessionToken(
                    context,
                    ComponentName(context, PlaybackService::class.java)
                )
                controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
                PlayerView(context).also { playerView ->
                    playerView.useController = false
                    controllerFuture?.addListener({
                        playerView.player = controllerFuture!!.get().also { controller ->
                            controller.setMediaItems(
                                listOf(MediaItem.fromUri(data.musicUrl)),
                                mediaItemIndex,
                                playbackPosition
                            )
                            controller.setAudioAttributes(
                                AudioAttributes
                                    .Builder()
                                    .setContentType(AUDIO_CONTENT_TYPE_MUSIC)
                                    .build(),
                                true
                            )
                            controller.playWhenReady = playWhenReady
                            controller.prepare()
                        }
                    }, MoreExecutors.directExecutor())
                }
            },
            update = { playerView ->
                when (lifecycleEvent) {
                    Lifecycle.Event.ON_START -> {

                    }

                    Lifecycle.Event.ON_STOP -> {
                        controllerFuture?.let { MediaController.releaseFuture(it) }
                        playerView.player?.release()
                    }

                    else -> {
                        /** Do nothing */
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
        )

        Text(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    start = 32.dp,
                    end = 32.dp
                )
                .align(Alignment.Start),
            text = data.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            modifier = Modifier
                .padding(
                    top = 4.dp,
                    start = 32.dp,
                    end = 32.dp
                )
                .align(Alignment.Start),
            text = data.name,
            fontSize = 18.sp,
            color = Grey898989
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(
                    top = 21.dp,
                    start = 8.dp,
                    end = 16.dp
                )
        ) {

            TrackBehaviorItem(
                behaviorName = stringResource(R.string.now_playing_behavior_save),
                behaviorIcon = R.drawable.ic_playlist_add
            )

            TrackBehaviorItem(
                behaviorName = stringResource(R.string.now_playing_behavior_share),
                behaviorIcon = R.drawable.ic_share
            )

            TrackBehaviorItem(
                behaviorName = stringResource(R.string.now_playing_behavior_download),
                behaviorIcon = R.drawable.ic_download
            )
        }

        var sliderPosition by remember { mutableFloatStateOf(0.5f) }

        Slider(
            modifier = Modifier.padding(
                top = 21.dp,
                start = 24.dp,
                end = 24.dp
            ),
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Grey898989
            ),
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "0:00",
                fontSize = 12.sp,
                color = Grey898989
            )

            Text(
                text = "2:33",
                fontSize = 12.sp,
                color = Grey898989
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 21.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            PlaybackAction(actionIcon = R.drawable.ic_shuffle_off)

            PlaybackAction(actionIcon = R.drawable.ic_skip_previous)

            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(color = Color.White)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {

                PlaybackAction(
                    actionIcon = R.drawable.ic_play,
                    actionTint = Color.Black,
                )
            }

            PlaybackAction(actionIcon = R.drawable.ic_skip_next)

            PlaybackAction(actionIcon = R.drawable.ic_repeat)
        }
    }
}

@Composable
private fun TrackBehaviorItem(
    behaviorName: String,
    behaviorIcon: Int,
    onClicked: () -> Unit = {},
) {

    Box(modifier = Modifier
        .padding(start = 8.dp)
        .clip(RoundedCornerShape(28.dp))
        .background(color = Grey808080)
        .clickable { onClicked() }
    ) {

        Row(
            modifier = Modifier.padding(
                vertical = 4.dp,
                horizontal = 12.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                painter = painterResource(behaviorIcon),
                contentDescription = null,
                tint = Color.White
            )

            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = behaviorName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun PlaybackAction(
    actionIcon: Int,
    actionTint: Color = Color.White,
    onActionClicked: () -> Unit = {},
) {

    IconButton(onClick = { onActionClicked() }) {
        Icon(
            modifier = Modifier.size(40.dp),
            painter = painterResource(actionIcon),
            contentDescription = null,
            tint = actionTint,
        )
    }
}

private fun Modifier.conditional(
    condition: Boolean,
    modifier: Modifier.() -> Modifier,
): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}

@Preview
@Composable
private fun NowPlayingScreenPreview() {
    NowPlayingScreen()
}
