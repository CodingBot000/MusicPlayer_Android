package com.sample.myplayer.ui.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material.ripple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sample.myplayer.R
import com.sample.myplayer.domain.model.Music
import com.sample.myplayer.state.PlayerState
import com.sample.myplayer.ui.theme.Gray_40
import com.sample.myplayer.ui.theme.Orange_20
import com.sample.myplayer.ui.viewmodels.HomeEvent

@Composable
fun BottomPlayInfoBar(
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit,
    playerState: PlayerState?,
    music: Music?,
    currentTime: Long,
    totalTime: Long,
    onBarClick: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        BottomPlayInfoItem(
            music = music,
            onEvent = onEvent,
            playerState = playerState,
            currentTime = currentTime,
            totalTime = totalTime,
            onBarClick = onBarClick,

        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomPlayInfoItem(
    music: Music?,
    onEvent: (HomeEvent) -> Unit,
    playerState: PlayerState?,
    currentTime: Long,
    totalTime: Long,
    onBarClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .height(78.dp)
            .clickable(onClick = { onBarClick() })
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = music?.albumThumbUrl,
                contentDescription = music?.title ?: "Empty Music",
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
                    music?.title ?: "Select Music",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    music?.desc ?: "",
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

            Icon(
                imageVector = Icons.Rounded.SkipPrevious,
                contentDescription = "Skip Previous",
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(12.dp)
                    .size(32.dp)
                    .clickable(onClick = { onEvent(HomeEvent.SkipToPreviousMusic) })
            )

            Image(
                painter = painterResource(
                    id = if (playerState == PlayerState.PLAYING) {
                        R.drawable.ic_bottom_pause_circle
                    } else {
                        R.drawable.ic_bottom_play_arrow
                    }
                ),
                contentDescription = "Music Control",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(
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
                colorFilter = ColorFilter.tint(
                    if (playerState == PlayerState.PLAYING) Orange_20 else Gray_40
                )
            )

            Icon(
                imageVector = Icons.Rounded.SkipNext,
                contentDescription = "Skip Next",
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(12.dp)
                    .size(32.dp)
                    .clickable(onClick = {
                        onEvent(HomeEvent.SkipToNextMusic)
                    })
            )
        }

        Slider(
            value = currentTime.toFloat(),
            onValueChange = {

            },
            thumb = {},
            valueRange = 0f..totalTime.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp), colors = androidx.compose.material3.SliderDefaults.colors(
                activeTrackColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                inactiveTrackColor = androidx.compose.material3.MaterialTheme.colorScheme.surface
            )
        )
    }
}

