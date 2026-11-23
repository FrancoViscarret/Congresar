package com.example.congresar.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.congresar.R
import com.example.congresar.domain.model.EstadoLey
import com.example.congresar.domain.model.Ley
import com.example.congresar.domain.model.TipoLey
import java.time.format.DateTimeFormatter

@Composable
fun LawDetailsCard(
    ley: Ley,
    onDatePickerClick: () -> Unit,
    onDetailsClick: () -> Unit,
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(7.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {


        MyCard {

            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(painter = painterResource(R.drawable.book_icon), contentDescription = "")
                    Text(
                        text = "Resumen del proyecto",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = ley.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }



        MyCard {

            Row(
                modifier = Modifier
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Presentación: ",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )


                val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
                Text(

                    text = ley.fechaPresentacion.format(formatter),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.calendar_icon),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(45.dp)
                        .clickable {
                            onDatePickerClick()
                        })
            }
        }

        MyCard {

            Column(
                modifier = Modifier.padding(12.dp),

                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.EditNote,
                        contentDescription = "", Modifier.size(32.dp)
                    )
                    Text(
                        text = when (ley.tipo) {
                            TipoLey.ACTUALIZACION_DE_LEY -> when (ley.estado) {
                                EstadoLey.APROBADA -> "Ejes principales: "
                                EstadoLey.RECHAZADA -> "Cambios propuestos: "
                                EstadoLey.EN_TRATAMIENTO -> "Cambios propuestos: "
                            }

                            TipoLey.NUEVA_LEY -> when (ley.estado) {
                                EstadoLey.APROBADA -> "Propuestas aprobadas: "
                                EstadoLey.RECHAZADA -> "Propuestas rechazadas: "
                                EstadoLey.EN_TRATAMIENTO -> "Propuestas: "
                            }
                        },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                ley.cambiosIntroducidos?.let { ci ->

                    ci.forEach {
                        Text(
                            text = "- $it",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 10,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                }
            }

        }

        MyCard() {

            Button(
                onClick = onDetailsClick, modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp), shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.MenuBook,
                        contentDescription = ""
                    )
                    Text(
                        text = "Ver texto completo y dictamen",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                }
            }

        }
        MyCard() {

            OutlinedButton(
                onClick = {}, modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp), shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.notification_icon),
                        contentDescription = ""
                    )
                    Text(
                        text = "Seguir proyecto",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                }
            }

        }

    }
}


@Composable
fun MyCard(modifier: Modifier = Modifier.fillMaxWidth(), content: @Composable () -> Unit) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    ) {

        content()
    }

}