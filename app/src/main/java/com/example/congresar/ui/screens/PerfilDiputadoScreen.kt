package com.example.congresar.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.congresar.R
import com.example.congresar.domain.model.DiputadoPerfil
import com.example.congresar.domain.model.SentidoVoto
import com.example.congresar.domain.model.VotacionDiputado
import com.example.congresar.navigation.AppDestinations
import com.example.congresar.presentation.perfil.PerfilDiputadoViewModel
import com.example.congresar.ui.theme.BrandTint
import com.example.congresar.ui.theme.VoteAbsent
import com.example.congresar.ui.theme.VoteAbsentContainer
import com.example.congresar.ui.theme.VoteAbstention
import com.example.congresar.ui.theme.VoteAbstentionContainer
import com.example.congresar.ui.theme.VoteAffirmative
import com.example.congresar.ui.theme.VoteAffirmativeContainer
import com.example.congresar.ui.theme.VoteNegative
import com.example.congresar.ui.theme.VoteNegativeContainer

private const val VOTACIONES_INICIALES = 2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilDiputadoScreen(
    navController: NavController,
    viewModel: PerfilDiputadoViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.perfil_title),
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(R.string.perfil_back),
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                ),
            )
        },
    ) { paddingValues ->
        val perfil = state.perfil
        when {
            state.isLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.perfil_loading),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            perfil == null -> {
                Text(
                    text = stringResource(R.string.perfil_not_found),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(32.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
            }

            else -> {
                PerfilContent(
                    perfil = perfil,
                    onChatClick = {
                        navController.navigate(AppDestinations.chat(perfil.diputado.id))
                    },
                    modifier = Modifier.padding(paddingValues),
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PerfilContent(
    perfil: DiputadoPerfil,
    onChatClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val diputado = perfil.diputado
    val nombreVisible = "${diputado.nombre} ${diputado.apellido}"
    var votacionesExpandidas by rememberSaveable { mutableStateOf(false) }
    val votacionesOrdenadas = perfil.votaciones.sortedByDescending { fechaClave(it.fecha) }
    val votacionesVisibles = if (votacionesExpandidas) {
        votacionesOrdenadas
    } else {
        votacionesOrdenadas.take(VOTACIONES_INICIALES)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Surface(
                modifier = Modifier.size(120.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
            ) {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = stringResource(R.string.perfil_photo, nombreVisible),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .padding(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.perfil_identity, nombreVisible, perfil.edad),
                    modifier = Modifier.weight(1f, fill = false),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                FilledTonalIconButton(
                    onClick = onChatClick,
                    modifier = Modifier.size(40.dp),
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = BrandTint,
                        contentColor = MaterialTheme.colorScheme.primary,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ChatBubbleOutline,
                        contentDescription = stringResource(R.string.perfil_chat),
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Badge(diputado.bloquePolitico)
                Badge(diputado.provincia)
                Badge(diputado.profesion)
            }
        }

        SectionTitle(text = stringResource(R.string.perfil_attendance_section))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsistenciaRing(porcentaje = perfil.asistenciaPorcentaje)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.perfil_attendance_label),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Text(
                text = stringResource(R.string.perfil_projects, perfil.proyectosPresentados),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
        }

        SectionTitle(text = stringResource(R.string.perfil_votes_section))
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            votacionesVisibles.forEach { votacion ->
                VotacionCard(votacion = votacion)
            }
            if (perfil.votaciones.size > VOTACIONES_INICIALES) {
                TextButton(
                    onClick = { votacionesExpandidas = !votacionesExpandidas },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        text = stringResource(
                            if (votacionesExpandidas) R.string.perfil_see_less
                            else R.string.perfil_see_more,
                        ),
                    )
                }
            }
        }
    }
}

@Composable
private fun Badge(text: String) {
    Text(
        text = stringResource(R.string.perfil_badge, text),
        modifier = Modifier.padding(horizontal = 6.dp),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun SectionTitle(text: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
    }
}

@Composable
private fun AsistenciaRing(porcentaje: Int) {
    val progreso = porcentaje.coerceIn(0, 100) / 100f
    val pista = MaterialTheme.colorScheme.outlineVariant
    val avance = MaterialTheme.colorScheme.secondary
    val descripcion = stringResource(R.string.perfil_attendance_description, porcentaje)

    Box(
        modifier = Modifier
            .size(112.dp)
            .semantics { contentDescription = descripcion },
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(6.dp)) {
            val stroke = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
            val diametro = size.minDimension
            val topLeft = Offset(
                (size.width - diametro) / 2f,
                (size.height - diametro) / 2f,
            )
            val arco = Size(diametro, diametro)
            drawArc(
                color = pista,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arco,
                style = stroke,
            )
            drawArc(
                color = avance,
                startAngle = -90f,
                sweepAngle = 360f * progreso,
                useCenter = false,
                topLeft = topLeft,
                size = arco,
                style = stroke,
            )
        }
        Text(
            text = stringResource(R.string.perfil_attendance_percent, porcentaje),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun VotacionCard(votacion: VotacionDiputado) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
            Text(
                text = votacion.tituloLey,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                VotoPill(sentido = votacion.sentido)
                Text(
                    text = votacion.fecha,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun VotoPill(sentido: SentidoVoto) {
    val container = when (sentido) {
        SentidoVoto.AFIRMATIVO -> VoteAffirmativeContainer
        SentidoVoto.NEGATIVO -> VoteNegativeContainer
        SentidoVoto.ABSTENCION -> VoteAbstentionContainer
        SentidoVoto.AUSENTE -> VoteAbsentContainer
    }
    val content = when (sentido) {
        SentidoVoto.AFIRMATIVO -> VoteAffirmative
        SentidoVoto.NEGATIVO -> VoteNegative
        SentidoVoto.ABSTENCION -> VoteAbstention
        SentidoVoto.AUSENTE -> VoteAbsent
    }

    Surface(
        shape = RoundedCornerShape(50),
        color = container,
    ) {
        Text(
            text = sentido.etiqueta(),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = content,
        )
    }
}

private fun fechaClave(fecha: String): Int {
    val partes = fecha.split("/")
    if (partes.size != 3) return 0
    val dia = partes[0].toIntOrNull() ?: return 0
    val mes = partes[1].toIntOrNull() ?: return 0
    val anio = partes[2].toIntOrNull() ?: return 0
    return anio * 10_000 + mes * 100 + dia
}

@Composable
private fun SentidoVoto.etiqueta(): String = when (this) {
    SentidoVoto.AFIRMATIVO -> stringResource(R.string.perfil_vote_afirmativo)
    SentidoVoto.NEGATIVO -> stringResource(R.string.perfil_vote_negativo)
    SentidoVoto.ABSTENCION -> stringResource(R.string.perfil_vote_abstencion)
    SentidoVoto.AUSENTE -> stringResource(R.string.perfil_vote_ausente)
}
