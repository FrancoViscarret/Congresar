package com.example.congresar.presentation.perfil

import com.example.congresar.domain.model.DiputadoPerfil

data class PerfilDiputadoUiState(
    val perfil: DiputadoPerfil? = null,
    val isLoading: Boolean = true,
    val notFound: Boolean = false,
)
