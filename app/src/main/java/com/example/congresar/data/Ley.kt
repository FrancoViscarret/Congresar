package com.example.congresar.data

/**
 * Legislative bill. Mirrors the `Ley` entity of the data model:
 * id_ley, titulo, descripcion, estado, fecha_presentacion.
 *
 * [miniatura] is the drawable resource used as the carousel card image.
 */
data class Ley(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val estado: String,
    // TODO: switch to LocalDate once core library desugaring is enabled (minSdk 24).
    val fechaPresentacion: String,
    val miniatura: Int,
)
