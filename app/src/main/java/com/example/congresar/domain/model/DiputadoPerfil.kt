package com.example.congresar.domain.model

/**
 * Profile shown on the deputy detail screen.
 * [Diputado] stays the list entity from the v3 model. Age, attendance,
 * bills authored and votes live only here. Each vote points at a [idLey]
 * the same way the Diputado_Ley link does, and adds the vote cast on that bill.
 */
data class DiputadoPerfil(
    val diputado: Diputado,
    val edad: Int,
    val asistenciaPorcentaje: Int,
    val proyectosPresentados: Int,
    val votaciones: List<VotacionDiputado>,
)

data class VotacionDiputado(
    val idLey: Int,
    val tituloLey: String,
    val sentido: SentidoVoto,
    val fecha: String,
)

enum class SentidoVoto {
    AFIRMATIVO,
    NEGATIVO,
    ABSTENCION,
    AUSENTE,
}

/** yyyyMMdd key so votes can be ordered without java.time (minSdk 24). */
fun VotacionDiputado.ordenCronologico(): Int {
    val partes = fecha.split("/")
    if (partes.size != 3) return 0
    val dia = partes[0].toIntOrNull() ?: return 0
    val mes = partes[1].toIntOrNull() ?: return 0
    val anio = partes[2].toIntOrNull() ?: return 0
    return anio * 10_000 + mes * 100 + dia
}
