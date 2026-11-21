package com.example.congresar.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreenDetailCard(textToShow: String?) {

    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .heightIn(min = 100.dp, max = 250.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = textToShow ?: "Untitled",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}