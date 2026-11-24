package com.example.congresar.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.congresar.domain.model.Noticia
import com.example.congresar.ui.theme.BrandBorder
import com.example.congresar.ui.theme.BrandTint
import java.time.format.DateTimeFormatter

private val VoteDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
@Composable
fun NewsDetailCard(
    noticia: Noticia,
    relatedLaws: List<String>,
    expanded: Boolean = false,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val animatedMaxLines by animateIntAsState(
        targetValue = if (expanded) 30 else 5,
        animationSpec = tween(durationMillis = 300),
        label = "newsMaxLines"
    )
    Column(
        modifier = modifier.then(
            if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        NoticiaCard {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(color = MaterialTheme.colorScheme.primary)
            ) {

                Row(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = noticia.fuente,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Card(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxHeight(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                    shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                ) {
                    Text(
                        modifier = Modifier
                            .padding(12.dp)
                            .animateContentSize(animationSpec = tween(durationMillis = 300)),
                        text = noticia.contenido,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = animatedMaxLines,
                        overflow = TextOverflow.Ellipsis
                    )
                    noticia.quote?.let { quote ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = CardDefaults.cardColors(containerColor = BrandTint),
                            border = BorderStroke(1.dp, BrandBorder),
                        ) {
                            Box(contentAlignment = Alignment.CenterStart) {
                                Text(
                                    text = quote,
                                    modifier = Modifier.padding(8.dp),
                                    style = MaterialTheme.typography.bodyMediumEmphasized,
                                    fontStyle = FontStyle.Italic
                                )
                            }

                        }

                    }

                    CongresarAnnotatedString(
                        titulo = "Fecha de publicación: ",
                        contenido = noticia.fechaPublicacion.format(VoteDateFormatter)
                    )
                    CongresarAnnotatedString(
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
                            color = MaterialTheme.colorScheme.primary,
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
            }
        }
    }


}


@Composable
fun CongresarAnnotatedString(titulo: String, contenido: String) {
    Text(
        text = buildAnnotatedString {

            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                ),
            ) { append(titulo) }
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
            color = BrandBorder,
        )
    ) {
        content()

    }
}
