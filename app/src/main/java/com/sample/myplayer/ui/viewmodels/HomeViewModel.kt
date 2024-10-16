package com.sample.myplayer.ui.viewmodels


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.myplayer.domain.model.Music
import com.sample.myplayer.domain.repository.MusicRepository
import com.sample.myplayer.domain.service.MusicController
import com.sample.myplayer.state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


data class HomeUiState(
    val loading: Boolean? = false,
    val musicList: List<Music>? = emptyList(),
    val selectedMusic: Music? = null,
    val errorMessage: String? = null
)

sealed class HomeEvent {
    data object PlayMusic : HomeEvent()
    data object PauseMusic : HomeEvent()
    data object ResumeMusic : HomeEvent()
    data object FetchMusic : HomeEvent()
    data object SkipToNextMusic : HomeEvent()
    data object SkipToPreviousMusic : HomeEvent()
    data class OnMusicSelected(val selectedMusic: Music) : HomeEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
    private val musicController: MusicController
) : ViewModel() {
    var homeUiState by mutableStateOf(HomeUiState())
        private set

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.PlayMusic -> playMusic()

            HomeEvent.PauseMusic -> pauseMusic()

            HomeEvent.ResumeMusic -> resumeMusic()

            HomeEvent.FetchMusic -> fetchMusicList()

            is HomeEvent.OnMusicSelected -> homeUiState =
                homeUiState.copy(selectedMusic = event.selectedMusic)

            is HomeEvent.SkipToNextMusic -> skipToNextMusic()

            is HomeEvent.SkipToPreviousMusic -> skipToPreviousMus()
        }
    }

    private fun fetchMusicList() {
        homeUiState = homeUiState.copy(loading = true)

        viewModelScope.launch {
            musicRepository.getMusicList().catch {
                homeUiState = homeUiState.copy(
                    loading = false,
                    errorMessage = it.message
                )
            }.collect {
                homeUiState = when (it) {
                    is Resource.Success -> {
                        it.data?.let { list ->
                            musicController.addMediaItems(list)
                        }

                        homeUiState.copy(
                            loading = false,
                            musicList = it.data
                        )
                    }

                    is Resource.Loading -> {
                        homeUiState.copy(
                            loading = true,
                            errorMessage = null
                        )
                    }

                    is Resource.Error -> {
                        homeUiState.copy(
                            loading = false,
                            errorMessage = it.message
                        )
                    }
                }
            }
        }
    }

    private fun playMusic() {
        homeUiState.apply {
            musicList?.indexOf(selectedMusic)?.let { music ->
                musicController.play(music)
            }

        }
    }

    private fun pauseMusic() {
        musicController.pause()
    }

    private fun resumeMusic() {
        musicController.resume()
    }

    private fun skipToNextMusic() {
        musicController.skipToNextMusic()
        musicController.getCurrentMusic()?.let {
            homeUiState = homeUiState.copy(selectedMusic = musicController.getCurrentMusic())

        }.runCatching {

        }
    }

    private fun skipToPreviousMus() {
        musicController.skipToPreviousMusic()
        musicController.getCurrentMusic()?.let {
            homeUiState = homeUiState.copy(selectedMusic = musicController.getCurrentMusic())
        }.runCatching {

        }
    }
}
