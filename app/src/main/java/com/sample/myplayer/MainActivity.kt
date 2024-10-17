package com.sample.myplayer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sample.myplayer.data.service.MusicService
import com.sample.myplayer.ui.Screens
import com.sample.myplayer.ui.SplashScreen
import com.sample.myplayer.ui.home.HomeScreen
import com.sample.myplayer.ui.playdetail.PlayDetailScreen
import com.sample.myplayer.ui.theme.MusicPlayerTheme
import com.sample.myplayer.ui.viewmodels.HomeViewModel
import com.sample.myplayer.ui.viewmodels.PlayDetailViewModel
import com.sample.myplayer.ui.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val sharedViewModel: SharedViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val systemUiController = rememberSystemUiController()

            val useDarkIcons = !isSystemInDarkTheme()

            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = useDarkIcons
                )
            }
            MusicPlayerTheme {
                val navController = rememberNavController()
                val musicControllerUiState = sharedViewModel.musicControllerUiState

                NavHost(navController = navController, startDestination = Screens.SPLASH_SCREEN) {
                    composable(route = Screens.SPLASH_SCREEN) {
                        SplashScreen {
                            navController.navigate(Screens.MAIN_SCREEN) {
                                popUpTo(Screens.SPLASH_SCREEN) { inclusive = true }
                            }
                        }
                    }
                    composable(route = Screens.MAIN_SCREEN) {
                        val mainViewModel: HomeViewModel = hiltViewModel()

                        Box(modifier = Modifier.fillMaxSize()) {
                            HomeScreen(
                                sharedViewModel = sharedViewModel,
                                onEvent = mainViewModel::onEvent,
                                uiState = mainViewModel.homeUiState,
                                music = musicControllerUiState.currentMusic,
                                playerState = musicControllerUiState.playerState,
                                onClickPlayInfoBar = {

                                }

                            )
                        }
                    }

                    composable(route = Screens.PLAY_DETAIL_SCREEN) {
                        val playDetailViewModel :PlayDetailViewModel = hiltViewModel()
                        PlayDetailScreen(
                            onEvent = playDetailViewModel::onEvent,
                            musicControllerUiState = musicControllerUiState,
                            onNavigateUp = { navController.navigateUp() }
                        )
                    }
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        sharedViewModel.destroyMediaController()
        stopService(Intent(this, MusicService::class.java))
    }
}


