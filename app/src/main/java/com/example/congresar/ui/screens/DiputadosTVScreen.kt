package com.example.congresar.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Composable
fun DiputadosTVScreen(
    navController: NavController,
    onBottomBarVisibilityChange: (Boolean) -> Unit = {},
    youtubeVideoId: String = "6Rx2CMf6QiU",
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {

    val isLandscape =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Hide the app bottom bar while this screen is horizontal.
    LaunchedEffect(isLandscape) {
        onBottomBarVisibilityChange(!isLandscape)
    }

    // Restore it when leaving the screen (or if it is disposed while hidden).
    DisposableEffect(Unit) {
        onDispose { onBottomBarVisibilityChange(true) }
    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(5.dp)),
        factory = { factory ->
            YouTubePlayerView(context = factory).apply {
                lifecycleOwner.lifecycle.addObserver(this)
                addYouTubePlayerListener(
                    youTubePlayerListener = object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {


                            youTubePlayer.loadVideo(youtubeVideoId, startSeconds = 0f)

                        }
                    }
                )
            }
        }
    )

}