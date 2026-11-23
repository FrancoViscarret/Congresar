package com.example.congresar.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.congresar.domain.model.Noticia

@Composable
fun NewsDetailDialog(
    noticia: Noticia,
    relatedLaws: List<String>,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(16.dp),
        ) {
            NewsDetailCard(
                noticia = noticia,
                relatedLaws = relatedLaws,
                expanded = true,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
            )
        }
    }
}
