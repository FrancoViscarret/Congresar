package com.example.congresar.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PageIndicator(
    pageCount: Int,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val selected = pagerState.currentPage == index
            val dotSize by animateDpAsState(
                targetValue = if (selected) 9.dp else 7.dp,
                label = "dotSize"
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(dotSize)
                    .clip(CircleShape)
                    .background(
                        if (selected) Color.White
                        else Color.White.copy(alpha = 0.45f)
                    )
            )
        }
    }
}
