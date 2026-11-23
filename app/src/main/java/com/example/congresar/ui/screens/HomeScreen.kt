package com.example.congresar.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.congresar.domain.model.Noticia
import com.example.congresar.navigation.AppDestinations
import com.example.congresar.ui.components.CongresarDatePicker
import com.example.congresar.ui.components.LawDetailsCard
import com.example.congresar.ui.components.NewsDetailCard
import com.example.congresar.ui.components.NewsDetailDialog
import com.example.congresar.ui.components.PageIndicator
import com.example.congresar.ui.viewmodels.HomeViewModel
import java.time.Instant
import java.time.ZoneOffset
import kotlin.math.absoluteValue
import kotlinx.coroutines.launch

private enum class CarouselTab(val label: String, val icon: ImageVector?) {
    LEYES("Leyes", null),
    NOTICIAS("Noticias", Icons.Outlined.Newspaper),
}

private data class CarouselItem(val miniatura: Int, val titulo: String)

@Composable
fun HomeScreen(navController: NavHostController) {

    val vm: HomeViewModel = hiltViewModel()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    var showDatePicker by remember { mutableStateOf(false) }
    // News opened in the readable dialog, null when the dialog is closed.
    var expandedNoticia by remember { mutableStateOf<Noticia?>(null) }
    val scope = rememberCoroutineScope()

    var selectedTab by remember { mutableStateOf(CarouselTab.LEYES) }

    var relatedLeyId by remember { mutableStateOf<Int?>(null) }

    val noticias = relatedLeyId
        ?.let { leyId -> uiState.noticias.filter { leyId in it.relatedTo } }
        ?: uiState.noticias

    val items: List<CarouselItem> = when (selectedTab) {
        CarouselTab.LEYES -> uiState.leyes.map { ley ->
            CarouselItem(ley.miniatura, ley.titulo)

        }

        CarouselTab.NOTICIAS -> noticias.map { CarouselItem(it.miniatura, it.titulo) }
    }

    val pagerState = rememberPagerState(pageCount = { items.size })

    // Noticias starts on the first related card; Leyes reopens on the law being watched.
    LaunchedEffect(selectedTab, items) {
        if (items.isNotEmpty()) {
            val targetPage = when (selectedTab) {
                CarouselTab.LEYES -> uiState.leyes.indexOfFirst { it.id == relatedLeyId }
                    .takeIf { it >= 0 } ?: 0

                CarouselTab.NOTICIAS -> 0
            }
            pagerState.scrollToPage(targetPage.coerceIn(0, items.lastIndex))
        }
    }

    if (showDatePicker) {
        var initialDateSelected: Long? = null

        uiState.currentLawId?.let { lawId ->

            initialDateSelected = uiState.leyes
                .firstOrNull { it.id == lawId }
                ?.fechaPresentacion
                ?.atStartOfDay(ZoneOffset.UTC)
                ?.toInstant()
                ?.toEpochMilli()

        }
        val diasSeleccionables = uiState.leyes.map { ley ->
            ley.fechaPresentacion
        }.toSet()

        val selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val date =
                    Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneOffset.UTC).toLocalDate()

                return date in diasSeleccionables
            }
        }
        val datePickerState =
            rememberDatePickerState(
                initialSelectedDateMillis = initialDateSelected,
                yearRange = 2024..2028, selectableDates = selectableDates
            )
        CongresarDatePicker(
            datePickerState = datePickerState,
            onDismiss = { showDatePicker = false },
            onDateSelected = { millis ->
                showDatePicker = false

                val selectedDate = millis?.let {
                    Instant.ofEpochMilli(it).atZone(ZoneOffset.UTC).toLocalDate()
                }
                val lawIndex = selectedDate
                    ?.let { date -> uiState.leyes.indexOfFirst { it.fechaPresentacion == date } }
                    ?: -1

                if (lawIndex >= 0) {
                    scope.launch { pagerState.scrollToPage(lawIndex) }
                }
            }
        )
    }

    expandedNoticia?.let { noticia ->
        val leyesRelacionadasDialogo = uiState.leyes
            .filter { ley -> ley.id in noticia.relatedTo }
            .map { ley -> ley.titulo }

        NewsDetailDialog(
            noticia = noticia,
            relatedLaws = leyesRelacionadasDialogo,
            onDismiss = { expandedNoticia = null }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (showDatePicker) {
                    Modifier.blur(10.dp)
                } else {
                    Modifier
                }
            )
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

                        .fillMaxHeight(0.50f),
                    contentAlignment = Alignment.BottomCenter
                ) {

                    if (items.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (selectedTab == CarouselTab.NOTICIAS && relatedLeyId != null) {
                                    "Sin noticias relacionadas con esta ley"
                                } else {
                                    "Sin contenido por ahora"
                                },
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
                                    .clickable {
                                        when (selectedTab) {
                                            // Laws open their detail screen.
                                            CarouselTab.LEYES -> {
                                                uiState.leyes.getOrNull(page)?.let { ley ->
                                                    navController.navigate(
                                                        AppDestinations.detalleLey(ley.id)
                                                    )
                                                }
                                            }

                                            // News open the readable dialog.
                                            CarouselTab.NOTICIAS -> {
                                                noticias.getOrNull(page)?.let {
                                                    expandedNoticia = it
                                                }
                                            }
                                        }
                                    }
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

                                // Fade the bottom of the image so the title stays readable.
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                0.55f to Color.Transparent,
                                                1f to Color.Black.copy(alpha = 0.65f)
                                            )
                                        )
                                )

                                Text(
                                    text = items[page].titulo,
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(start = 16.dp, end = 16.dp, bottom = 30.dp),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    // Segmented pill control over the image.
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 16.dp)
                            .clip(RoundedCornerShape(percent = 50))
                            .background(Color(0xFF0D0D0D))
                            .border(
                                width = 1.dp,
                                color = Color.White.copy(alpha = 0.10f),
                                shape = RoundedCornerShape(percent = 50)
                            )
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CarouselTab.entries.forEach { tab ->
                            val selected = tab == selectedTab
                            val segmentColor by animateColorAsState(
                                targetValue = if (selected) Color.White else Color.Transparent,
                                label = "tabBackground"
                            )
                            val contentColor by animateColorAsState(
                                targetValue = if (selected) Color(0xFF0D0D0D) else Color.White,
                                label = "tabContent"
                            )

                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(percent = 50))
                                    .background(segmentColor)
                                    .clickable {
                                        if (tab == selectedTab) return@clickable

                                        // Entering Noticias keeps the law currently on
                                        // screen, so its related news are the ones listed.
                                        if (tab == CarouselTab.NOTICIAS) {
                                            relatedLeyId = uiState.leyes
                                                .getOrNull(pagerState.currentPage)
                                                ?.id
                                        }
                                        selectedTab = tab
                                    }
                                    .padding(horizontal = 24.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                tab.icon?.let { icon ->
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        tint = contentColor,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Text(
                                    text = tab.label,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (selected) FontWeight.SemiBold
                                    else FontWeight.Medium,
                                    color = contentColor
                                )
                            }
                        }
                    }

                    // Dots sit 16dp above the bottom edge of the carousel/image.

                }
                PageIndicator(
                    pageCount = items.size,
                    pagerState = pagerState,
                    modifier = Modifier.padding(16.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    item {
                        when (selectedTab) {
                            // One card per law field.
                            CarouselTab.LEYES -> {
                                val ley = uiState.leyes
                                    .getOrNull(pagerState.currentPage)
                                    ?: return@item

                                LawDetailsCard(ley = ley, onDatePickerClick = {
                                    // Remember which law opened the picker so it starts
                                    // on that law's presentation date.
                                    vm.onItemLawChange(ley.id)
                                    showDatePicker = true
                                })
                                Spacer(modifier = Modifier.height(8.dp))

                            }

                            // News card (title, summary and related laws) plus its source data.
                            CarouselTab.NOTICIAS -> {
                                val noticia = noticias
                                    .getOrNull(pagerState.currentPage)
                                    ?: return@item

                                // Resolve the related law ids to the law titles shown
                                // by NewsDetailCard.
                                val leyesRelacionadas = uiState.leyes
                                    .filter { ley -> ley.id in noticia.relatedTo }
                                    .map { ley -> ley.titulo }

                                NewsDetailCard(
                                    noticia = noticia,
                                    relatedLaws = leyesRelacionadas,
                                    onClick = { expandedNoticia = noticia }
                                )
                                Spacer(modifier = Modifier.height(8.dp))


                            }
                        }
                    }

                }
            }
        }

    }
}