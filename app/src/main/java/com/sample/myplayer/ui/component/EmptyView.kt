package com.sample.myplayer.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sample.myplayer.ui.theme.rainbowColors

@Composable
fun EmptyView(title: String = "Data Error") {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = "Warning Icon",
            modifier = Modifier.size(200.dp),
            tint = Color.White
        )


        RainBowTextColorAnimation(
            text = title,
            fontSize = 44.sp,
            rainbowColors = rainbowColors
        )

    }

}
