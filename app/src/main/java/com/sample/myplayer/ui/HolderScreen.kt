package com.sample.myplayer.ui


import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sample.myplayer.ui.home.HomeScreen
import com.sample.myplayer.ui.viewmodels.HomeViewModel
import com.sample.myplayer.ui.viewmodels.SharedViewModel


@Composable
fun HolderScreen(
    sharedViewModel: SharedViewModel = hiltViewModel(),
    navController: NavHostController,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalSharedViewModel provides sharedViewModel) {
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
                    onBackPressed = onBackPressed
                )
            }

        }
    }
}


val LocalSharedViewModel = compositionLocalOf<SharedViewModel> { error("No SharedViewModel provided") }