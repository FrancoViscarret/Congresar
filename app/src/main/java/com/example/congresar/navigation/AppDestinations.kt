package com.example.congresar.navigation

object AppDestinations {
    const val HOME = "Home"
    const val DIPUTADOS_TV = "DiputadosTV"
    const val DIPUTADOS = "Diputados"
    const val LEY_ARG = "idLey"
    const val DETALLE_LEY_BASE = "DetalleLey"
    const val DETALLE_LEY = "$DETALLE_LEY_BASE/{$LEY_ARG}"
    const val PERFIL_ARG = "idDiputado"
    const val PERFIL_BASE = "PerfilDiputado"
    const val PERFIL = "$PERFIL_BASE/{$PERFIL_ARG}"
    const val CHAT_BASE = "ChatDiputado"
    const val CHAT = "$CHAT_BASE/{$PERFIL_ARG}"

    fun detalleLey(idLey: Int) = "$DETALLE_LEY_BASE/$idLey"

    fun perfil(idDiputado: Int) = "$PERFIL_BASE/$idDiputado"

    fun chat(idDiputado: Int) = "$CHAT_BASE/$idDiputado"
}
