package com.example.congresar.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.congresar.data.Ley
import com.example.congresar.ui.components.HomeScreenDetailCard
import com.example.congresar.ui.components.PageIndicator
import com.example.congresar.ui.viewmodels.HomeViewModel
import kotlin.math.absoluteValue

private enum class CarouselTab(val label: String) {
    LEYES("Leyes"),
    NOTICIAS("Noticias"),
}

@Composable
fun HomeScreen(navController: NavHostController) {

    val vm: HomeViewModel = hiltViewModel()
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableStateOf(CarouselTab.LEYES) }

    val items: List<Ley> = when (selectedTab) {
        CarouselTab.LEYES -> uiState.leyes
        // TODO: replace with the noticias list once the Noticia model exists.
        CarouselTab.NOTICIAS -> emptyList()
    }

    val pagerState = rememberPagerState(pageCount = { items.size })

    // Every tab starts again from the first card.
    LaunchedEffect(items) {
        if (items.isNotEmpty()) pagerState.scrollToPage(0)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Column(modifier = Modifier.fillMaxSize()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        // Carousel takes 33% of the available height.
                        .fillMaxHeight(0.40f),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    if (items.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Sin contenido por ahora",
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    } else {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxSize()
                        ) { page ->
                            Box(
                                modifier = Modifier
                                    .zIndex(if (page == pagerState.currentPage) 1f else 0f)
                                    .fillMaxSize()
                                    .graphicsLayer {
                                        val pageOffset = (pagerState.currentPage - page) +
                                                pagerState.currentPageOffsetFraction
                                        val absOffset = pageOffset.absoluteValue.coerceIn(0f, 1f)

                                        // Deck style: neighbours shrink and fade behind the
                                        // current card.
                                        scaleX = 1f - 0.06f * absOffset
                                        scaleY = 1f - 0.06f * absOffset
                                        alpha = 1f - 0.30f * absOffset

                                        shape = RectangleShape
                                        clip = true
                                        shadowElevation = (1f - absOffset) * 10.dp.toPx()
                                    }
                            ) {
                                Image(
                                    painter = painterResource(id = items[page].miniatura),
                                    contentDescription = items[page].titulo,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
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

                val currentItem = uiState.leyes[pagerState.currentPage]
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(). padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {

                        HomeScreenDetailCard(currentItem.estado)
                        Spacer(modifier = Modifier.height(8.dp))

                        HomeScreenDetailCard(currentItem.descripcion)
                        Spacer(modifier = Modifier.height(8.dp))

                        HomeScreenDetailCard(currentItem.fechaPresentacion)
                        Spacer(modifier = Modifier.height(8.dp))


                    }
                }
            }
        }

    }
}