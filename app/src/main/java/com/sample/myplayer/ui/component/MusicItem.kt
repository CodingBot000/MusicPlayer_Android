package com.sample.myplayer.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sample.myplayer.R
import com.sample.myplayer.domain.model.Music

@Composable
fun MusicItem(
    onClick: () -> Unit,
    music: Music
) {
    val paddingTop = 15.dp

    Row( modifier = Modifier
        .clickable {
            onClick()
        }
        .fillMaxWidth()) {


        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(music.albumThumbUrl)
                .crossfade(true)
                .build(),
            contentDescription = music.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(170.dp)
                .padding(paddingTop)
                .clip(MaterialTheme.shapes.medium)
                .aspectRatio(1f),
            error = painterResource(R.drawable.ic_music)
        )

        Column(modifier = Modifier.padding(top = paddingTop))
        {
            Text(
                text = music.title,
                maxLines = 2,
                style = MaterialTheme.typography.subtitle1,
            )

            Text(
                text = music.desc,
                maxLines = 2,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}