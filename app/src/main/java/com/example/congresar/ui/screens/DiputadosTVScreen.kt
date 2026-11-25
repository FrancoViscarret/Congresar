package com.example.congresar.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.minus
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.congresar.R
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

    val playerView = remember { mutableStateOf<YouTubePlayerView?>(null) }

    LaunchedEffect(isLandscape) {
        onBottomBarVisibilityChange(!isLandscape)
    }

    DisposableEffect(Unit) {
        onDispose {
            onBottomBarVisibilityChange(true)
            playerView.value?.let { view ->
                lifecycleOwner.lifecycle.removeObserver(view)
                view.release()
            }
        }
    }
    Scaffold(
        topBar =
            {
                if (!isLandscape)
                    CenterAlignedTopAppBar(title = {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier,
                                painter = painterResource(R.drawable.diputados_tv_directo),
                                contentDescription = ""
                            )
                            Text(
                                text = "Diputados TV",
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
                        }
                    }, navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = stringResource(R.string.diputados_back),
                            )
                        }
                    })

            }) { paddingValues ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues.minus(PaddingValues(12.dp)))
                .padding(8.dp)
                .clip(RoundedCornerShape(5.dp)),
            factory = { context ->
                YouTubePlayerView(context).apply {
                    playerView.value = this
                    lifecycleOwner.lifecycle.addObserver(this)
                    addYouTubePlayerListener(
                        object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                youTubePlayer.loadVideo(youtubeVideoId, startSeconds = 0f)
                            }
                        }
                    )
                }
            }
        )
    }


}
