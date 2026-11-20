package com.example.congresar.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HemicicloBancas(
    modifier: Modifier
) {

    Canvas(
        modifier = modifier
    ) {


        val bancasPorFila = listOf<Int>(22,22,25,29,33,37,42,47)
        val radioMax = size.height * 0.7
        val radioMin = radioMax * 0.45f

        repeat(bancasPorFila.size) { fila ->
            val radioFila = radioMin + ((radioMax - radioMin) * fila / (bancasPorFila.size - 1))
            val cantidad = bancasPorFila[fila]



            repeat(cantidad) { i ->
                val angle = Math.PI * i / (cantidad - 1)

                val x = center.x + (radioFila * cos(angle)).toFloat()
                val y = center.y + (radioFila * sin(angle)).toFloat()
                drawCircle(
                    color = Color.Blue,
                    radius = 8f,
                    center = Offset(x, y)
                )
            }
        }
    }
}
