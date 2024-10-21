package com.sample.myplayer.ui.home

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.rememberPagerState
import com.sample.myplayer.ui.component.BottomPlayInfoBar
import com.sample.myplayer.ui.component.CustomAlertDialog
import com.sample.myplayer.ui.component.MusicItem
import com.sample.myplayer.ui.component.SliderBanner
import com.sample.myplayer.ui.playdetail.PlayDetailScreen
import com.sample.myplayer.ui.theme.Gray_10
import com.sample.myplayer.ui.theme.Gray_20
import com.sample.myplayer.ui.theme.Gray_30
import com.sample.myplayer.ui.theme.Gray_50
import com.sample.myplayer.ui.viewmodels.HomeEvent
import com.sample.myplayer.ui.viewmodels.HomeUiState
import com.sample.myplayer.ui.viewmodels.MusicControllerUiState
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    onEvent: (HomeEvent) -> Unit,
    uiState: HomeUiState,
    musicControllerUiState: MusicControllerUiState,
    onBackPressed: () -> Unit

) {
    val isInitialized = rememberSaveable { mutableStateOf(false) }

    if (!isInitialized.value) {
        LaunchedEffect(key1 = Unit) {
            onEvent(HomeEvent.FetchMusic)
            isInitialized.value = true
        }
    }

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed,
    )
    val scope = rememberCoroutineScope()

    BackHandler {
        if (sheetState.isExpanded) {
            scope.launch {
                sheetState.collapse()
            }
        } else {
            onBackPressed()
        }
    }

    BottomSheetScaffold(
        scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState),
        sheetContent = {

            PlayDetailScreen(
                musicControllerUiState = musicControllerUiState,
                onClose = {
                    scope.launch {
                        sheetState.collapse()
                    }
                }
            )
        },
        sheetPeekHeight = 0.dp,
        sheetShape = MaterialTheme.shapes.large,
        sheetElevation = 50.dp,
        sheetBackgroundColor = MaterialTheme.colors.surface,
        content = { innerPadding ->

            val pagerState = rememberPagerState()
            Scaffold(
                topBar = { },
                content = { padding ->
                    HomeContent(
                        modifier = Modifier.padding(padding),
                        onEvent = onEvent,
                        uiState = uiState,
                        onClickBanner = {
                            scope.launch {
                                sheetState.expand()
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomAppBar(
                        backgroundColor = if (isSystemInDarkTheme()) Gray_30 else Gray_50,
                        elevation = 25.dp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(
                                topStart = 15.dp,
                                topEnd = 15.dp))
                            .height(80.dp)
                    ) {
                        BottomPlayInfoBar(
                            modifier = Modifier,
                            onEvent = onEvent,
                            music = musicControllerUiState.currentMusic,
                            playerState = musicControllerUiState.playerState,
                            currentTime = musicControllerUiState.currentPosition,
                            totalTime = musicControllerUiState.totalDuration,
                            onBarClick = {
                                scope.launch {
                                    sheetState.expand()
                                }
                            }
                        )
                    }
                },
                scaffoldState = rememberScaffoldState(),
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
                    .statusBarsPadding(),
                backgroundColor = MaterialTheme.colors.background,
            )
        },
        sheetGesturesEnabled = true
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit,
    uiState: HomeUiState,
    onClickBanner: () -> Unit
) {
    Column(modifier = modifier
        .fillMaxSize())
    {
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
                            item {
                                if (uiState.bannerList != null) {
                                    SliderBanner(banners = uiState.bannerList) {
                                        onEvent(HomeEvent.OnMusicSelected(it))
                                        onEvent(HomeEvent.PlayMusic)
                                        onClickBanner()
                                    }
                                }
                            }
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
                CustomAlertDialog(
                    showDialog = uiState.showDialog ?: false,
                    content = uiState.errorMessage,
                    confirmButtonName = "Read Local Data",
                    onDismiss = {
                        onEvent(HomeEvent.CloseAlert)
                        onEvent(HomeEvent.FetchLocalData)
                    })
            }

        }
    }
}