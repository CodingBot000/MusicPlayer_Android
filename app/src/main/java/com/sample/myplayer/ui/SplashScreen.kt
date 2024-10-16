package com.sample.myplayer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sample.myplayer.R
import com.sample.myplayer.ui.component.RainBowTextColorAnimation
import com.sample.myplayer.ui.theme.rainbowColors
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(outSplash: () -> Unit) {

    LaunchedEffect(key1 = true) {
        delay(3000)
        outSplash()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Splash Image",
            modifier = Modifier.size(200.dp)
        )

        RainBowTextColorAnimation(
            text = "I am Splash",
            fontSize = 44.sp,
            rainbowColors = rainbowColors
        )

    }

}
