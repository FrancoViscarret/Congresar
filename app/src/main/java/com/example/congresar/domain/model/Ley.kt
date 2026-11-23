package com.example.congresar.domain.model

import java.time.LocalDate

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
    val nroDeLey: Int? = null,
    val estado: EstadoLey,
    val fechaPresentacion: LocalDate,
    val rubro: List<String>? = emptyList(),
    val miniatura: Int,
    val tipo: TipoLey,
    val cambiosIntroducidos: List<String>? = null,
    val expediente: String = "",
    val periodo: String = "",
    val votacion: ResultadoVotacion? = null,
)

/**
 * Vote outcome for a law. Laws still in [EstadoLey.EN_TRATAMIENTO] have a null
 * [Ley.votacion].
 */
data class ResultadoVotacion(
    val fecha: LocalDate,
    val afirmativos: Int,
    val negativos: Int,
    val abstenciones: Int,
)

enum class TipoLey {
    NUEVA_LEY, ACTUALIZACION_DE_LEY
}

enum class EstadoLey {
    APROBADA, RECHAZADA, EN_TRATAMIENTO
}
