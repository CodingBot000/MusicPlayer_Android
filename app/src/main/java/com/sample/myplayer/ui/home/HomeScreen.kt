package com.sample.myplayer.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sample.myplayer.ui.component.MusicItem
import com.sample.myplayer.ui.viewmodels.HomeEvent
import com.sample.myplayer.ui.viewmodels.HomeUiState


@Composable
fun HomeScreen(
    onEvent: (HomeEvent) -> Unit,
    uiState: HomeUiState,
) {
    val isInitialized = rememberSaveable { mutableStateOf(false) }

    if (!isInitialized.value) {
        LaunchedEffect(key1 = Unit) {
            onEvent(HomeEvent.FetchMusic)
            isInitialized.value = true
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            val appBarColor = MaterialTheme.colors.surface.copy(alpha = 0.87f)
            Spacer(
                Modifier
                    .background(appBarColor)
                    .fillMaxWidth()
                    .windowInsetsTopHeight(WindowInsets.statusBars)
            )

            when {
                uiState.loading == true -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.onBackground,
                            modifier = Modifier
                                .size(100.dp)
                                .fillMaxHeight()
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                }

                uiState.loading == false && uiState.errorMessage == null -> {
                    if (uiState.musicList != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.BottomCenter
                        )
                        {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colors.background)
                                    .align(Alignment.TopCenter),
                                contentPadding = PaddingValues(bottom = 60.dp)
                            ) {
                                items(uiState.musicList) {
                                    MusicItem(
                                        onClick = {
                                            onEvent(HomeEvent.OnMusicSelected(it))
                                            onEvent(HomeEvent.PlayMusic)
                                        },
                                        music = it
                                    )
                                }
                            }
                        }
                    }
                }

                uiState.errorMessage != null -> {
                }

            }
        }
    }
}

