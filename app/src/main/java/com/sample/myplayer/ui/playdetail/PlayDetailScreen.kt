package com.sample.myplayer.ui.playdetail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Forward10
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Replay10
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sample.myplayer.R
import com.sample.myplayer.domain.model.Music
import com.sample.myplayer.state.PlayerState
import com.sample.myplayer.ui.component.EmptyView
import com.sample.myplayer.ui.viewmodels.MusicControllerUiState
import com.sample.myplayer.ui.viewmodels.PlayMusicEvent
import com.sample.myplayer.util.convertRunningTime

@ExperimentalMaterialApi
@Composable
fun PlayDetailScreen(
    onEvent: (PlayMusicEvent) -> Unit,
    musicControllerUiState: MusicControllerUiState,
    onNavigateUp: () -> Unit,
) {
    if (musicControllerUiState.currentMusic == null) {
        EmptyView()
        return
    }

//    val swipeableState = rememberSwipeableState(initialValue = 0)
    val endAnchor = LocalConfiguration.current.screenHeightDp * LocalDensity.current.density
    val anchors = mapOf(
        0f to 0, endAnchor to 1
    )

    val backgroundColor = MaterialTheme.colors.background

    val dominantColor by remember { mutableStateOf(Color.Transparent) }

    val iconResId =
        if (musicControllerUiState.playerState == PlayerState.PLAYING) R.drawable.ic_round_pause else R.drawable.ic_round_play_arrow

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        PlayDetailScreenContent(
            music = musicControllerUiState.currentMusic,
            dominantColor = dominantColor,
            currentTime = musicControllerUiState.currentPosition,
            totalTime = musicControllerUiState.totalDuration,
            playPauseIcon = iconResId,
            playOrToggleSong = {
                onEvent(if (musicControllerUiState.playerState == PlayerState.PLAYING) PlayMusicEvent.PausePlayMusic else PlayMusicEvent.ResumePlayMusic)
            },
            playNextSong = { onEvent(PlayMusicEvent.SkipToNextPlayMusic) },
            playPreviousSong = { onEvent(PlayMusicEvent.SkipToPreviousPlayMusic) },
            onSliderChange = { newPosition ->
                onEvent(PlayMusicEvent.SeekPlayMusicToPosition(newPosition.toLong()))
            },
            onForward = {
                onEvent(PlayMusicEvent.SeekPlayMusicToPosition(musicControllerUiState.currentPosition + 10 * 1000))
            },
            onRewind = {
                musicControllerUiState.currentPosition.let { currentPosition ->
                    onEvent(PlayMusicEvent.SeekPlayMusicToPosition(if (currentPosition - 10 * 1000 < 0) 0 else currentPosition - 10 * 1000))
                }
            },
            onClose = { onNavigateUp() }
        )
    }

}

@Composable
fun PlayDetailScreenContent(
    music: Music,
    dominantColor: Color,
    currentTime: Long,
    totalTime: Long,
    @DrawableRes playPauseIcon: Int,
    playOrToggleSong: () -> Unit,
    playNextSong: () -> Unit,
    playPreviousSong: () -> Unit,
    onSliderChange: (Float) -> Unit,
    onRewind: () -> Unit,
    onForward: () -> Unit,
    onClose: () -> Unit
) {
    val sliderColors = if (isSystemInDarkTheme()) {
        SliderDefaults.colors(
            thumbColor = MaterialTheme.colors.onBackground,
            activeTrackColor = MaterialTheme.colors.onBackground,
            inactiveTrackColor = MaterialTheme.colors.onBackground.copy(
                alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity
            ),
        )
    } else SliderDefaults.colors(
        thumbColor = dominantColor,
        activeTrackColor = dominantColor,
        inactiveTrackColor = dominantColor.copy(
            alpha = ProgressIndicatorDefaults.IndicatorBackgroundOpacity
        ),
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
                Column {
                    IconButton(
                        onClick = onClose
                    ) {
                        Image(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(LocalContentColor.current)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(music.albumThumbUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = music.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            error = painterResource(R.drawable.ic_music)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = music.title,
                            style = MaterialTheme.typography.h5,
                            color = MaterialTheme.colors.onBackground,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(music.desc,
                            style = MaterialTheme.typography.subtitle1,
                            color = MaterialTheme.colors.onBackground,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.graphicsLayer {
                                alpha = 0.60f
                            })

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp)
                        ) {

                            Slider(
                                value = currentTime.toFloat(),
                                modifier = Modifier.fillMaxWidth(),
                                valueRange = 0f..totalTime.toFloat(),
                                colors = sliderColors,
                                onValueChange = onSliderChange,
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    convertRunningTime(currentTime),
                                    style = MaterialTheme.typography.body2
                                )

                                Text(
                                    convertRunningTime(totalTime),
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.SkipPrevious,
                                contentDescription = "Skip Previous",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable(onClick = playPreviousSong)
                                    .padding(12.dp)
                                    .size(32.dp)
                            )
                            Icon(
                                imageVector = Icons.Rounded.Replay10,
                                contentDescription = "Replay 10 seconds",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable(onClick = onRewind)
                                    .padding(12.dp)
                                    .size(32.dp)
                            )
                            Icon(
                                painter = painterResource(playPauseIcon),
                                contentDescription = "Play",
                                tint = MaterialTheme.colors.background,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colors.onBackground)
                                    .clickable(onClick = playOrToggleSong)
                                    .size(64.dp)
                                    .padding(8.dp)
                            )
                            Icon(
                                imageVector = Icons.Rounded.Forward10,
                                contentDescription = "Forward 10 seconds",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable(onClick = onForward)
                                    .padding(12.dp)
                                    .size(32.dp)
                            )
                            Icon(
                                imageVector = Icons.Rounded.SkipNext,
                                contentDescription = "Skip Next",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable(onClick = playNextSong)
                                    .padding(12.dp)
                                    .size(32.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}




