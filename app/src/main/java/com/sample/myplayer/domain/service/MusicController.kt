package com.sample.myplayer.domain.service

import com.sample.myplayer.domain.model.Music
import com.sample.myplayer.state.PlayerState

interface MusicController {
    var mediaControllerCallback: (
        (
        playerState: PlayerState,
        currentMusic: Music?,
        currentPosition: Long,
        totalDuration: Long,
        isShuffleEnabled: Boolean,
        isRepeatOneEnabled: Boolean
    ) -> Unit
    )?

    fun addMediaItems(music: List<Music>)

    fun play(mediaItemIndex: Int)

    fun resume()

    fun pause()

    fun getCurrentPosition(): Long

    fun destroy()

    fun skipToNextMusic()

    fun skipToPreviousMusic()

    fun getCurrentMusic(): Music?

    fun seekTo(position: Long)

    fun isConnected(): Boolean?
}