package com.sample.myplayer.ui.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.sample.myplayer.domain.model.Music
import com.sample.myplayer.ui.theme.Black
import com.sample.myplayer.ui.theme.Gray_20
import com.sample.myplayer.ui.theme.Gray_70
import com.sample.myplayer.ui.theme.Orange_60
import com.sample.myplayer.util.clickableSingle
import kotlinx.coroutines.delay

@Composable
fun SliderBanner(
    context: Context = LocalContext.current,
    banners: List<Music>,
    onClick: (Music) -> Unit)
{

    val pagerState = rememberPagerState()
    LaunchedEffect(banners) {
        if (banners.isNotEmpty()) {
            while (true) {
                delay(4000)
                val nextPage = (pagerState.currentPage + 1) % (banners.size)
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
    )
    {

        HorizontalPager(
            count = banners.size,
            state = pagerState
        ) { page ->
            val data = banners[page]
            Box(
                modifier = Modifier.fillMaxWidth()
                    .height(300.dp)
                    .clickableSingle {
                        onClick(data)
                    }
            )
            {
                AsyncImage(
                    model = ImageRequest
                        .Builder(context)
                        .data(data.albumThumbUrl)
                        .build(),
                    modifier = Modifier.fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )


                Text(
                    text = data.title,
                    color = Black,
                    textAlign = TextAlign.Right,
                    maxLines = 2,
                    modifier = Modifier.align(alignment = Alignment.BottomEnd)
                        .background(color = Orange_60)
                        .padding(horizontal = 10.dp)
                        .clip(shape = RoundedCornerShape(topStart = 15.dp))
                )

            }
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .align(Alignment.BottomCenter)
            .padding(bottom = 20.dp)
        ) {
        HorizontalTabs(
            items = List(banners.size) { it },
            pagerState = pagerState
        )
        }

    }
}

@Composable
private fun HorizontalTabs(
    items: List<Int>,
    pagerState: PagerState,
) {
    val dotRadius = 4.dp
    val dotSpacing = 8.dp

    Box(
        modifier = Modifier
            .height(dotRadius * 2)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(dotSpacing),
        ) {
            items?.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .size(dotRadius * 2)
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index) Gray_70 else Gray_20
                        ),
                )
            }
        }
    }
}
