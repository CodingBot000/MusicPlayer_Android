package com.sample.myplayer.ui.component


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sample.myplayer.R
import com.sample.myplayer.domain.model.Music
import com.sample.myplayer.state.PlayerState
import com.sample.myplayer.ui.viewmodels.HomeEvent

@Composable
fun HomeBottomBar(
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit,
    playerState: PlayerState?,
    music: Music?,
    onBarClick: () -> Unit
) {

    var offsetX by remember { mutableFloatStateOf(0f) }

    AnimatedVisibility(
        visible = playerState != PlayerState.STOPPED,
        modifier = modifier
    ) {
        if (music != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = {
                                when {
                                    offsetX > 0 -> {
                                        onEvent(HomeEvent.SkipToPreviousMusic)
                                    }

                                    offsetX < 0 -> {
                                        onEvent(HomeEvent.SkipToNextMusic)
                                    }
                                }
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                val (x, _) = dragAmount
                                offsetX = x
                            }
                        )

                    }
                    .background(
                        if (!isSystemInDarkTheme()) {
                            Color.LightGray
                        } else Color.DarkGray
                    ),
            ) {
                HomeBottomBarItem(
                    music = music,
                    onEvent = onEvent,
                    playerState = playerState,
                    onBarClick = onBarClick
                )
            }
        }
    }
}


@Composable
fun HomeBottomBarItem(
    music: Music,
    onEvent: (HomeEvent) -> Unit,
    playerState: PlayerState?,
    onBarClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(64.dp)
            .clickable(onClick = { onBarClick() })

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = music.albumThumbUrl,
                contentDescription = music.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .offset(16.dp),
                error = painterResource(R.drawable.ic_music)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(vertical = 8.dp, horizontal = 32.dp),
            ) {
                Text(
                    music.title,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    music.desc,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = 0.60f
                        }

                )
            }

            AsyncImage(
                model = if (playerState == PlayerState.PLAYING) {
                    R.drawable.ic_round_pause
                } else {
                    R.drawable.ic_round_play_arrow
                },
                contentDescription = "Music Control",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(48.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(
                            bounded = false,
                            radius = 24.dp
                        )
                    ) {
                        if (playerState == PlayerState.PLAYING) {
                            onEvent(HomeEvent.PauseMusic)
                        } else {
                            onEvent(HomeEvent.ResumeMusic)
                        }
                    },

                colorFilter = ColorFilter.tint(if (playerState == PlayerState.PLAYING) Color.Red else Color.Green)
            )

        }
    }
}

