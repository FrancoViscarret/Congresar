package com.example.congresar.domain.model

/**
 * Deputy. Mirrors the v3 `Diputado` entity (id, nombre, apellido, provincia,
 * bloque_politico) plus the fields the list card needs: profesión and an
 * optional photo. Partido on screen is [bloquePolitico].
 */
data class Diputado(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val provincia: String,
    val bloquePolitico: String,
    val profesion: String,
    val fotoUrl: String? = null,
) {
    val nombreCompleto: String get() = "$apellido, $nombre"
}

data class ResumenCamara(
    val totalBancas: Int,
    val totalBloques: Int,
)

fun resumenCamara(diputados: List<Diputado>): ResumenCamara = ResumenCamara(
    totalBancas = diputados.size,
    totalBloques = diputados.map { it.bloquePolitico }.distinct().size,
)
