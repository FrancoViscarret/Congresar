package com.example.congresar.navigation

object AppDestinations {
    const val HOME = "Home"
    const val DIPUTADOS_TV = "DiputadosTV"
    const val DIPUTADOS = "Diputados"
    const val DETALLE_LEY = "DetalleLey"
    const val PERFIL_ARG = "idDiputado"
    const val PERFIL_BASE = "PerfilDiputado"
    const val PERFIL = "$PERFIL_BASE/{$PERFIL_ARG}"

    fun perfil(idDiputado: Int) = "$PERFIL_BASE/$idDiputado"
}
