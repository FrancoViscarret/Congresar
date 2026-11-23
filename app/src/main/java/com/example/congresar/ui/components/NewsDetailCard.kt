package com.example.congresar.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.congresar.R
import com.example.congresar.domain.model.Noticia

@Composable
fun NewsDetailCard(
    noticia: Noticia,
    relatedLaws: List<String>,
    expanded: Boolean = false,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    var showMore: Boolean by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.then(
            if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        NoticiaCard {

            Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.onBackground)) {

                Row(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = noticia.fuente,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.surface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Card(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                    shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = noticia.contenido,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = if (expanded) 30 else 5,
                        overflow = TextOverflow.Ellipsis
                    )


                    CongresarAnotatedString(
                        titulo = "Fecha de publicación: ",
                        contenido = noticia.fechaPublicacion
                    )
                    CongresarAnotatedString(
                        titulo = "Leé la nota completa en: ",
                        contenido = noticia.urlFuente
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .height(24.dp)
                            .padding(16.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    if (relatedLaws.isNotEmpty()) {
                        Text(
                            text = "Proyectos de ley relacionados",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        relatedLaws.forEach { titulo ->
                            Text(
                                text = "• $titulo",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))



                }

//                IconButton(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(18.dp),
//                    onClick = { showMore = !showMore }
//                ) {
//                    Icon(
//                        painter = painterResource(R.drawable.arrow_down),
//                        contentDescription = "",
//                        tint = MaterialTheme.colorScheme.surface
//                    )
//                }
            }
        }
        NoticiaCard {


        }
    }


}


@Composable
fun CongresarAnotatedString(titulo: String, contenido: String) {
    Text(
        text = buildAnnotatedString {

            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append(titulo) }
            append(contenido)
        },
        modifier = Modifier.padding(8.dp),
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 10,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun NoticiaCard(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        content()

    }
}
//MyCard {
//
//    Row(
//        modifier = Modifier.padding(12.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//
//        Text(
//            text = "Estado: ",
//            style = MaterialTheme.typography.titleMedium,
//            fontWeight = FontWeight.Bold,
//            color = MaterialTheme.colorScheme.onSurface,
//            maxLines = 1,
//            overflow = TextOverflow.Ellipsis
//        )
//        Text(
//            text = when (ley.estado) {
//                EstadoLey.APROBADA -> "Aprobada"
//                EstadoLey.RECHAZADA -> "Rechazada"
//                EstadoLey.EN_TRATAMIENTO -> "En tratamiento"
//            },
//            style = MaterialTheme.typography.bodyMedium,
//            fontWeight = FontWeight.Normal,
//            fontStyle = FontStyle.Italic,
//            color = MaterialTheme.colorScheme.onSurface,
//            maxLines = 10,
//            overflow = TextOverflow.Ellipsis
//        )
//    }
//}

//Noticia(
//id = 1,
//titulo = "Comisiones analizan el Presupuesto 2027",
//contenido = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
//fechaPublicacion = "2026-11-20",
//fuente = "Congresar",
//urlFuente = "https://congresar.example/noticias/1",
//miniatura = R.drawable.miniatura_periodico,
//relatedTo = listOf(1),
//)