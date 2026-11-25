package com.example.congresar.domain.model

/**
 * One turn in the direct citizen channel with a deputy.
 * The thread stays in memory until a remote channel exists.
 */
data class MensajeChat(
    val id: Long,
    val autor: AutorMensaje,
    val texto: String,
)

enum class AutorMensaje {
    USUARIO,
    DIPUTADO,
}
