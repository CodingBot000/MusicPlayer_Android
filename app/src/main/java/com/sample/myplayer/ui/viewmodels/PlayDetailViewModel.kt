package com.sample.myplayer.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.sample.myplayer.domain.service.MusicController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed class PlayMusicEvent {
    data object PausePlayMusic : PlayMusicEvent()
    data object ResumePlayMusic : PlayMusicEvent()
    data object SkipToNextPlayMusic : PlayMusicEvent()
    data object SkipToPreviousPlayMusic : PlayMusicEvent()
    data class SeekPlayMusicToPosition(val position: Long) : PlayMusicEvent()
}

@HiltViewModel
class PlayDetailViewModel @Inject constructor(
    private val musicController: MusicController,
) : ViewModel() {
    fun onEvent(event: PlayMusicEvent) {
        when (event) {
            PlayMusicEvent.PausePlayMusic -> pauseMusic()
            PlayMusicEvent.ResumePlayMusic -> resumeMusic()
            is PlayMusicEvent.SeekPlayMusicToPosition -> seekToPosition(event.position)
            PlayMusicEvent.SkipToNextPlayMusic -> skipToNextMusic()
            PlayMusicEvent.SkipToPreviousPlayMusic -> skipToPreviousMusic()
        }
    }

    private fun pauseMusic() {
        musicController.pause()
    }

    private fun resumeMusic() {
        musicController.resume()
    }

    private fun skipToNextMusic()  {
        musicController.skipToNextMusic()
        musicController.getCurrentMusic()
    }

    private fun skipToPreviousMusic() {
        musicController.skipToPreviousMusic()
        musicController.getCurrentMusic()
    }
    private fun seekToPosition(position: Long) {
        musicController.seekTo(position)
    }

}
