package com.example.congresar.presentation.chat

import com.example.congresar.domain.model.MensajeChat

data class ChatDiputadoUiState(
    val nombre: String = "",
    val apellido: String = "",
    val bloquePolitico: String = "",
    val mensajes: List<MensajeChat> = emptyList(),
    val borrador: String = "",
    val isLoading: Boolean = true,
    val notFound: Boolean = false,
    val enviando: Boolean = false,
)
