package com.sample.myplayer.ui


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sample.myplayer.ui.home.HomeScreen
import com.sample.myplayer.ui.viewmodels.HomeViewModel
import com.sample.myplayer.ui.viewmodels.MusicControllerUiState

@Composable
fun HolderScreen(
    navController: NavHostController,
    musicControllerUiState: MusicControllerUiState,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = Screens.SPLASH_SCREEN, modifier = modifier) {
        composable(route = Screens.SPLASH_SCREEN) {
            SplashScreen {
                navController.navigate(Screens.MAIN_SCREEN) {
                    popUpTo(Screens.SPLASH_SCREEN) { inclusive = true }
                }
            }
        }
        composable(route = Screens.MAIN_SCREEN) {
            val homeViewModel: HomeViewModel = hiltViewModel()

            HomeScreen(
                onEvent = homeViewModel::onEvent,
                uiState = homeViewModel.homeUiState,
                musicControllerUiState = musicControllerUiState,
                onBackPressed = onBackPressed
            )
        }

    }
}
