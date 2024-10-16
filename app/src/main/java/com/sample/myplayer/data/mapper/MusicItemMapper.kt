package com.sample.myplayer.data.mapper

import androidx.media3.common.MediaItem
import com.sample.myplayer.domain.model.Music

fun convertMediaItemToMusic(item: MediaItem): Music {
    return with(item) {
        Music(
            id = mediaId,
            title = mediaMetadata.title.toString(),
            desc = mediaMetadata.subtitle.toString(),
            musicUrl = mediaId,
            albumThumbUrl = mediaMetadata.artworkUri.toString()
        )
    }
}
