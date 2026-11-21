package com.example.congresar.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.congresar.ui.theme.SeatPalette
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * Minimal hemicycle. One dot per seat, tinted by bloc with [SeatPalette].
 * [bloques] is one entry per deputy, in seating order.
 */
@Composable
fun HemicicloBancas(
    bloques: List<String>,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    val arcColor = MaterialTheme.colorScheme.outline
    val tonos = bloques.distinct()

    Canvas(
        modifier = modifier.semantics { this.contentDescription = contentDescription },
    ) {
        val center = Offset(size.width / 2f, size.height * 0.96f)
        val radioMax = min(size.height * 0.78f, size.width / 2f - 6.dp.toPx())
        val radioMin = radioMax * 0.62f
        val filas = filasDeBancas(bloques)
        val stroke = Stroke(width = 1.25.dp.toPx())

        filas.forEachIndexed { index, fila ->
            val progreso = if (filas.size == 1) 1f else index / (filas.size - 1f)
            val radio = radioMin + (radioMax - radioMin) * progreso
            drawArc(
                color = arcColor,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(center.x - radio, center.y - radio),
                size = Size(radio * 2f, radio * 2f),
                style = stroke,
            )

            val cantidad = fila.size
            fila.forEachIndexed { asiento, bloque ->
                val angulo = if (cantidad == 1) {
                    Math.PI * 1.5
                } else {
                    val margen = Math.PI * 0.08
                    val inicio = Math.PI + margen
                    val recorrido = Math.PI - margen * 2
                    inicio + recorrido * asiento / (cantidad - 1)
                }
                val tono = SeatPalette[tonos.indexOf(bloque).mod(SeatPalette.size)]
                drawCircle(
                    color = tono,
                    radius = if (index == filas.lastIndex) 5.dp.toPx() else 4.dp.toPx(),
                    center = Offset(
                        x = center.x + (radio * cos(angulo)).toFloat(),
                        y = center.y + (radio * sin(angulo)).toFloat(),
                    ),
                )
            }
        }
    }
}

private fun filasDeBancas(bloques: List<String>): List<List<String>> {
    if (bloques.isEmpty()) return emptyList()
    if (bloques.size <= 7) return listOf(bloques)
    val interior = (bloques.size * 0.4f).toInt().coerceAtLeast(3)
    return listOf(bloques.take(interior), bloques.drop(interior))
}
