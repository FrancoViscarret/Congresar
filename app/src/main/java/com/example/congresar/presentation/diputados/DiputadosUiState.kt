package com.example.congresar.presentation.diputados

import com.example.congresar.domain.model.Diputado

data class DiputadosUiState(
    val diputados: List<Diputado> = emptyList(),
    val visibles: List<Diputado> = emptyList(),
    val query: String = "",
    val partidos: List<String> = emptyList(),
    val provincias: List<String> = emptyList(),
    val partidoSeleccionado: String? = null,
    val provinciaSeleccionada: String? = null,
    val totalBancas: Int = 0,
    val totalBloques: Int = 0,
    val isLoading: Boolean = true,
)
