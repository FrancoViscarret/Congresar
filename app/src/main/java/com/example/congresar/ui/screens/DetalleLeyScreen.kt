package com.example.congresar.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.congresar.domain.model.Diputado
import com.example.congresar.domain.model.EstadoLey
import com.example.congresar.domain.model.Ley
import com.example.congresar.navigation.AppDestinations
import com.example.congresar.presentation.detalleley.DetalleLeyViewModel
import com.example.congresar.ui.components.DiputadoCard
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.launch

private val VoteGreen = Color(0xFF2ECC71)
private val VoteRed = Color(0xFFE74C3C)
private val VoteYellow = Color(0xFFF1C40F)
private val VoteDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

@Composable
fun DetalleLeyScreen(
    navController: NavController,
    leyId: Int? = null,
) {
    val vm: DetalleLeyViewModel = hiltViewModel()
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(leyId) { vm.load(leyId) }

    val ley = uiState.ley

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            if (ley == null) {
                Text(
                    text = if (uiState.isLoading) {
                        "Cargando ley…"
                    } else {
                        "No se encontró la ley"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            } else {
                ley.rubro
                    ?.joinToString(" / ")
                    ?.takeIf { it.isNotBlank() }
                    ?.let { rubro -> RubroPill(text = rubro) }

                Text(
                    text = ley.nroDeLey
                        ?.let { numero -> "Ley N° $numero: ${ley.titulo}" }
                        ?: ley.titulo,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                val expedienteLine = listOfNotNull(
                    ley.expediente.takeIf { it.isNotBlank() }?.let { "Expediente: $it" },
                    ley.periodo.takeIf { it.isNotBlank() },
                ).joinToString("   •   ")

                if (expedienteLine.isNotBlank()) {
                    Text(
                        text = expedienteLine,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                VoteCard(ley = ley)

                LeyTabsCarousel(
                    ley = ley,
                    diputados = uiState.diputados,
                    isLoadingDiputados = uiState.isLoading,
                    onDiputadoClick = { diputado ->
                        navController.navigate(AppDestinations.perfil(diputado.id))
                    },
                )
            }
        }
    }

}

@Composable
private fun VoteCard(ley: Ley) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {

            EstadoPill(estado = ley.estado)

            val votacion = ley.votacion
            if (votacion == null) {
                Text(
                    text = "Aún no fue votada",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            } else {
                Text(
                    text = "Votado el ${votacion.fecha.format(VoteDateFormatter)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "Cámara de Diputados de la Nación • " + when (ley.estado) {
                        EstadoLey.APROBADA -> "Aprobación General y Particular"
                        EstadoLey.RECHAZADA -> "Rechazo General y Particular"
                        EstadoLey.EN_TRATAMIENTO -> "En tratamiento"
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )

                VoteBar(
                    afirmativos = votacion.afirmativos,
                    negativos = votacion.negativos,
                    abstenciones = votacion.abstenciones,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    VoteCount(VoteGreen, votacion.afirmativos, "Afirmativos")
                    VoteCount(VoteRed, votacion.negativos, "Negativos")
                    VoteCount(VoteYellow, votacion.abstenciones, "Abst.")
                }
            }
        }
    }
}

@Composable
private fun EstadoPill(estado: EstadoLey) {
    val accent = when (estado) {
        EstadoLey.APROBADA -> VoteGreen
        EstadoLey.RECHAZADA -> VoteRed
        EstadoLey.EN_TRATAMIENTO -> VoteYellow
    }

    Surface(
        shape = RoundedCornerShape(percent = 50),
        color = accent.copy(alpha = 0.12f),
        border = BorderStroke(1.dp, accent),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            when (estado) {
                EstadoLey.APROBADA -> Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(18.dp),
                )

                EstadoLey.RECHAZADA -> Icon(
                    imageVector = Icons.Rounded.Cancel,
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(18.dp),
                )

                EstadoLey.EN_TRATAMIENTO -> Unit
            }

            Text(
                text = "ESTADO: " + when (estado) {
                    EstadoLey.APROBADA -> "APROBADO"
                    EstadoLey.RECHAZADA -> "RECHAZADO"
                    EstadoLey.EN_TRATAMIENTO -> "EN TRATAMIENTO"
                },
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp,
                color = accent,
            )
        }
    }
}

@Composable
private fun VoteBar(
    afirmativos: Int,
    negativos: Int,
    abstenciones: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(percent = 50)),
    ) {
        if (afirmativos > 0) {
            Box(
                modifier = Modifier
                    .weight(afirmativos.toFloat())
                    .fillMaxHeight()
                    .background(VoteGreen)
            )
        }
        if (negativos > 0) {
            Box(
                modifier = Modifier
                    .weight(negativos.toFloat())
                    .fillMaxHeight()
                    .background(VoteRed)
            )
        }
        if (abstenciones > 0) {
            Box(
                modifier = Modifier
                    .weight(abstenciones.toFloat())
                    .fillMaxHeight()
                    .background(VoteYellow)
            )
        }
    }
}

@Composable
private fun VoteCount(
    color: Color,
    count: Int,
    label: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(
            modifier = Modifier
                .size(7.dp)
                .background(color = color, shape = CircleShape)
        )
        Text(
            text = "$count $label",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = color,
        )
    }
}


@Composable
fun RubroPill(
    text: String,
    modifier: Modifier = Modifier,
    accentColor: Color = Color(0xFF00A2FF),    // Light cyan/blue color
    backgroundColor: Color = Color(0xFF0A1828)  // Dark navy background
) {
    Surface(
        modifier = modifier,
        shape = CircleShape, // Fully rounded pill shape
        color = backgroundColor,
        border = BorderStroke(width = 1.dp, color = accentColor)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Bullet dot
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .background(color = accentColor, shape = CircleShape)
            )

            // Text
            Text(
                text = text.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
        }
    }
}

private enum class LeyTab(
    val label: String,
    val heading: String,
    val icon: ImageVector,
) {
    DESCRIPCION("Descripción", "Descripción", Icons.AutoMirrored.Rounded.MenuBook),
    CAMBIOS("Cambios", "Cambios propuestos", Icons.Rounded.EditNote),
    DIPUTADOS("Diputados", "Diputados", Icons.Rounded.Groups),
}

/**
 * Tabbed carousel below the vote card: description, proposed changes and the
 * list of deputies. Voting details per deputy will be wired later.
 */
@Composable
private fun LeyTabsCarousel(
    ley: Ley,
    diputados: List<Diputado>,
    isLoadingDiputados: Boolean,
    onDiputadoClick: (Diputado) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { LeyTab.entries.size })
    val scope = rememberCoroutineScope()

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
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
            LeyTab.entries.forEach { tab ->
                val selected = tab.ordinal == pagerState.currentPage
                val segmentColor by animateColorAsState(
                    targetValue = if (selected) Color.White else Color.Transparent,
                    label = "leyTabBackground"
                )
                val contentColor by animateColorAsState(
                    targetValue = if (selected) Color(0xFF0D0D0D) else Color.White,
                    label = "leyTabContent"
                )

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(percent = 50))
                        .background(segmentColor)
                        .clickable {
                            if (tab.ordinal == pagerState.currentPage) return@clickable
                            scope.launch { pagerState.animateScrollToPage(tab.ordinal) }
                        }
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 6.dp,
                        alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = null,
                        tint = contentColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = tab.label,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (selected) FontWeight.SemiBold
                        else FontWeight.Medium,
                        color = contentColor,
                        maxLines = 1
                    )
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            verticalAlignment = Alignment.Top,
            pageSpacing = 12.dp
        ) { page ->
            when (LeyTab.entries[page]) {
                LeyTab.DESCRIPCION -> LeyTabSurface {
                    Text(
                        text = ley.descripcion,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                LeyTab.CAMBIOS -> LeyTabSurface {
                    val cambios = ley.cambiosIntroducidos.orEmpty()
                    if (cambios.isEmpty()) {
                        Text(
                            text = "No se registraron cambios propuestos.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = LeyTab.CAMBIOS.heading,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            cambios.forEach { cambio ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = "•",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = cambio,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }

                LeyTab.DIPUTADOS -> LeyTabSurface {
                    if (diputados.isEmpty()) {
                        Text(
                            text = if (isLoadingDiputados) {
                                "Cargando diputados…"
                            } else {
                                "No hay diputados para mostrar."
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = LeyTab.DIPUTADOS.heading,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            diputados.forEach { diputado ->
                                DiputadoCard(
                                    diputado = diputado,
                                    onClick = { onDiputadoClick(diputado) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LeyTabSurface(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            content()
        }
    }
}