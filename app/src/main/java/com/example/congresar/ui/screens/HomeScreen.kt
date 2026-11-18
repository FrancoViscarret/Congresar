package com.example.congresar.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            IconButton(onClick = { navController.navigate(route = "DiputadosTV") }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "go to")
            }
        }) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text("Pantalla de home")

        }
    }


}