package com.example.congresar.data

import com.example.congresar.R

data class Location(
    val image: Int,
    val title: String?,
    val subtitle: String?,
    val rating: Double,
)

val locations = listOf(
    Location(
        image = R.drawable.ashinajpg,
        title = "Ashina",
        subtitle = "Sekiro, sengoku jidai",
        rating = 4.5
    ),
    Location(
        image = R.drawable.ringedcityjpg,
        title = "Ringed City",
        subtitle = "Ds3, unknow",
        rating = 3.5
    ),
    Location(
        image = R.drawable.lothricjpg,
        title = "Lothric",
        subtitle = "Ds3, unknow",
        rating = 4.5
    ),
)
