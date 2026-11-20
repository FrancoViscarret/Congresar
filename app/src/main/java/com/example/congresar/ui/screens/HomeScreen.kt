package com.example.congresar.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.congresar.data.Location
import com.example.congresar.data.locations
import kotlin.math.absoluteValue

private enum class CarouselTab(val label: String) {
    LEYES("Leyes"),
    NOTICIAS("Noticias"),
}

@Composable
fun HomeScreen(navController: NavHostController) {

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            var selectedTab by remember { mutableStateOf(CarouselTab.LEYES) }

            // TODO: replace with the real data source per category once it exists.
            val items: List<Location> = remember(selectedTab) {
                when (selectedTab) {
                    CarouselTab.LEYES -> locations
                    CarouselTab.NOTICIAS -> locations.reversed()
                }
            }

            val pagerState = rememberPagerState(pageCount = { items.size })

            // Every tab starts again from the first card.
            LaunchedEffect(items) {
                if (items.isNotEmpty()) pagerState.scrollToPage(0)
            }

            Column(modifier = Modifier.fillMaxSize()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        // Carousel takes 33% of the available height.
                        .fillMaxHeight(0.33f),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize(),
                        // Full-bleed cards: no padding at all, the image uses the whole width.
                        contentPadding = PaddingValues(0.dp)
                    ) { page ->
                        Box(
                            modifier = Modifier
                                // Only the settled card needs to be on top; reading currentPage
                                // (not the offset fraction) here avoids per-frame recomposition.
                                .zIndex(if (page == pagerState.currentPage) 1f else 0f)
                                .fillMaxSize()
                                .graphicsLayer {
                                    val pageOffset = (pagerState.currentPage - page) +
                                        pagerState.currentPageOffsetFraction
                                    val absOffset = pageOffset.absoluteValue.coerceIn(0f, 1f)

                                    // Deck style: neighbours shrink and fade behind the current card.
                                    scaleX = 1f - 0.06f * absOffset
                                    scaleY = 1f - 0.06f * absOffset
                                    alpha = 1f - 0.30f * absOffset

                                    shape = RectangleShape
                                    clip = true
                                    shadowElevation = (1f - absOffset) * 10.dp.toPx()
                                }
                        ) {
                            Image(
                                painter = painterResource(id = items[page].image),
                                contentDescription = items[page].title ?: "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    // Tabs over the image: 16dp apart and 16dp from the top edge.
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CarouselTab.entries.forEach { tab ->
                            val selected = tab == selectedTab
                            OutlinedButton(
                                onClick = { selectedTab = tab },
                                shape = RoundedCornerShape(6.dp),
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = if (selected) MaterialTheme.colorScheme.primary
                                    else Color.White.copy(alpha = 0.7f)
                                ),
                                contentPadding = PaddingValues(
                                    horizontal = 20.dp,
                                    vertical = 8.dp
                                )
                            ) {
                                Text(
                                    text = tab.label,
                                    fontWeight = if (selected) FontWeight.SemiBold
                                    else FontWeight.Normal,
                                    color = if (selected) MaterialTheme.colorScheme.primary
                                    else Color.White
                                )
                            }
                        }
                    }

                    // Dots sit 16dp above the bottom edge of the carousel/image.
                    PageIndicator(
                        pageCount = items.size,
                        pagerState = pagerState,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                // TODO: pending sections from the mockup
                // (Aprobado/Rechazado, 3 Puntos Clave, Cambios con Ley Anterior).
            }
        }
    }
}

@Composable
private fun PageIndicator(
    pageCount: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val selected = pagerState.currentPage == index
            val dotSize by animateDpAsState(
                targetValue = if (selected) 9.dp else 7.dp,
                label = "dotSize"
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(dotSize)
                    .clip(CircleShape)
                    .background(
                        if (selected) Color.White
                        else Color.White.copy(alpha = 0.45f)
                    )
            )
        }
    }
}
