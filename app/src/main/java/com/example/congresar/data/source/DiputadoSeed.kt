package com.example.congresar.data.source

import com.example.congresar.domain.model.Diputado
import com.example.congresar.domain.model.DiputadoPerfil
import com.example.congresar.domain.model.SentidoVoto
import com.example.congresar.domain.model.VotacionDiputado

/**
 * Fictional deputies used until a database or network source exists.
 * Bill ids 1–3 match the sample leyes already used on Home. Vote dates are dd/MM/yyyy.
 */
object DiputadoSeed {
    private val leyes = mapOf(
        1 to "Ley Glaciares",
        2 to "Ley Financiamiento universitario",
        3 to "Presupuesto 2027",
        4 to "Reforma del Poder Judicial",
        5 to "Ley de Educación Digital",
        6 to "Ley de Humedales",
    )

    val perfiles: List<DiputadoPerfil> = listOf(
        perfil(
            diputado(1, "Martina", "Álvarez", "Buenos Aires", "Unión por el Trabajo", "Abogada"),
            edad = 46,
            asistencia = 85,
            proyectos = 3,
            voto(1, SentidoVoto.NEGATIVO, "24/04/2008"),
            voto(2, SentidoVoto.AFIRMATIVO, "12/09/2024"),
            voto(3, SentidoVoto.AFIRMATIVO, "03/11/2026"),
            voto(4, SentidoVoto.NEGATIVO, "18/06/2025"),
            voto(5, SentidoVoto.ABSTENCION, "02/03/2026"),
        ),
        perfil(
            diputado(2, "Carlos", "Benítez", "Córdoba", "Frente del Sur", "Ingeniero agrónomo"),
            edad = 54,
            asistencia = 78,
            proyectos = 5,
            voto(1, SentidoVoto.AFIRMATIVO, "24/04/2008"),
            voto(6, SentidoVoto.AFIRMATIVO, "15/08/2023"),
            voto(3, SentidoVoto.NEGATIVO, "03/11/2026"),
            voto(5, SentidoVoto.AFIRMATIVO, "02/03/2026"),
            voto(2, SentidoVoto.ABSTENCION, "12/09/2024"),
        ),
        perfil(
            diputado(3, "Lucía", "Cabrera", "Santa Fe", "Partido Federal", "Economista"),
            edad = 41,
            asistencia = 92,
            proyectos = 4,
            voto(3, SentidoVoto.AFIRMATIVO, "03/11/2026"),
            voto(4, SentidoVoto.AFIRMATIVO, "18/06/2025"),
            voto(2, SentidoVoto.NEGATIVO, "12/09/2024"),
            voto(5, SentidoVoto.AUSENTE, "02/03/2026"),
            voto(6, SentidoVoto.AFIRMATIVO, "15/08/2023"),
        ),
        perfil(
            diputado(4, "Pedro", "Domínguez", "Mendoza", "Unión por el Trabajo", "Docente"),
            edad = 61,
            asistencia = 70,
            proyectos = 2,
            voto(5, SentidoVoto.AFIRMATIVO, "02/03/2026"),
            voto(2, SentidoVoto.AFIRMATIVO, "12/09/2024"),
            voto(1, SentidoVoto.NEGATIVO, "24/04/2008"),
            voto(4, SentidoVoto.ABSTENCION, "18/06/2025"),
        ),
        perfil(
            diputado(5, "Valentina", "Espinosa", "Tucumán", "Movimiento del Norte", "Médica"),
            edad = 38,
            asistencia = 88,
            proyectos = 6,
            voto(6, SentidoVoto.AFIRMATIVO, "15/08/2023"),
            voto(5, SentidoVoto.AFIRMATIVO, "02/03/2026"),
            voto(3, SentidoVoto.ABSTENCION, "03/11/2026"),
            voto(2, SentidoVoto.AFIRMATIVO, "12/09/2024"),
            voto(4, SentidoVoto.NEGATIVO, "18/06/2025"),
        ),
        perfil(
            diputado(6, "Joaquín", "Fernández", "Buenos Aires", "Partido Federal", "Periodista"),
            edad = 49,
            asistencia = 64,
            proyectos = 1,
            voto(4, SentidoVoto.NEGATIVO, "18/06/2025"),
            voto(3, SentidoVoto.AUSENTE, "03/11/2026"),
            voto(2, SentidoVoto.NEGATIVO, "12/09/2024"),
            voto(1, SentidoVoto.ABSTENCION, "24/04/2008"),
            voto(5, SentidoVoto.NEGATIVO, "02/03/2026"),
        ),
        perfil(
            diputado(7, "Ana", "Gómez", "Neuquén", "Alianza Patagónica", "Geóloga"),
            edad = 44,
            asistencia = 96,
            proyectos = 7,
            voto(1, SentidoVoto.AFIRMATIVO, "24/04/2008"),
            voto(6, SentidoVoto.AFIRMATIVO, "15/08/2023"),
            voto(5, SentidoVoto.AFIRMATIVO, "02/03/2026"),
            voto(3, SentidoVoto.AFIRMATIVO, "03/11/2026"),
            voto(2, SentidoVoto.ABSTENCION, "12/09/2024"),
        ),
        perfil(
            diputado(8, "Mateo", "Herrera", "Salta", "Movimiento del Norte", "Abogado"),
            edad = 36,
            asistencia = 81,
            proyectos = 3,
            voto(4, SentidoVoto.AFIRMATIVO, "18/06/2025"),
            voto(3, SentidoVoto.NEGATIVO, "03/11/2026"),
            voto(6, SentidoVoto.AFIRMATIVO, "15/08/2023"),
            voto(2, SentidoVoto.AUSENTE, "12/09/2024"),
        ),
        perfil(
            diputado(9, "Sofía", "Ibarra", "Entre Ríos", "Frente del Sur", "Contadora"),
            edad = 52,
            asistencia = 73,
            proyectos = 4,
            voto(3, SentidoVoto.AFIRMATIVO, "03/11/2026"),
            voto(2, SentidoVoto.AFIRMATIVO, "12/09/2024"),
            voto(5, SentidoVoto.NEGATIVO, "02/03/2026"),
            voto(4, SentidoVoto.ABSTENCION, "18/06/2025"),
            voto(1, SentidoVoto.AFIRMATIVO, "24/04/2008"),
        ),
        perfil(
            diputado(10, "Diego", "Juárez", "Chubut", "Alianza Patagónica", "Arquitecto"),
            edad = 47,
            asistencia = 69,
            proyectos = 2,
            voto(6, SentidoVoto.NEGATIVO, "15/08/2023"),
            voto(1, SentidoVoto.AFIRMATIVO, "24/04/2008"),
            voto(3, SentidoVoto.ABSTENCION, "03/11/2026"),
            voto(5, SentidoVoto.AUSENTE, "02/03/2026"),
            voto(4, SentidoVoto.NEGATIVO, "18/06/2025"),
        ),
        perfil(
            diputado(11, "Camila", "López", "Ciudad Autónoma de Buenos Aires", "Unión por el Trabajo", "Socióloga"),
            edad = 33,
            asistencia = 90,
            proyectos = 8,
            voto(5, SentidoVoto.AFIRMATIVO, "02/03/2026"),
            voto(2, SentidoVoto.AFIRMATIVO, "12/09/2024"),
            voto(4, SentidoVoto.AFIRMATIVO, "18/06/2025"),
            voto(3, SentidoVoto.NEGATIVO, "03/11/2026"),
            voto(6, SentidoVoto.ABSTENCION, "15/08/2023"),
        ),
        perfil(
            diputado(12, "Andrés", "Morales", "Misiones", "Frente del Sur", "Productor agropecuario"),
            edad = 58,
            asistencia = 76,
            proyectos = 3,
            voto(6, SentidoVoto.AFIRMATIVO, "15/08/2023"),
            voto(1, SentidoVoto.AFIRMATIVO, "24/04/2008"),
            voto(2, SentidoVoto.NEGATIVO, "12/09/2024"),
            voto(3, SentidoVoto.AFIRMATIVO, "03/11/2026"),
        ),
    )

    val diputados: List<Diputado> = perfiles.map { it.diputado }

    private fun diputado(
        id: Int,
        nombre: String,
        apellido: String,
        provincia: String,
        bloque: String,
        profesion: String,
    ) = Diputado(
        id = id,
        nombre = nombre,
        apellido = apellido,
        provincia = provincia,
        bloquePolitico = bloque,
        profesion = profesion,
    )

    private fun perfil(
        diputado: Diputado,
        edad: Int,
        asistencia: Int,
        proyectos: Int,
        vararg votaciones: VotacionDiputado,
    ) = DiputadoPerfil(
        diputado = diputado,
        edad = edad,
        asistenciaPorcentaje = asistencia,
        proyectosPresentados = proyectos,
        votaciones = votaciones.toList(),
    )

    private fun voto(idLey: Int, sentido: SentidoVoto, fecha: String) = VotacionDiputado(
        idLey = idLey,
        tituloLey = leyes.getValue(idLey),
        sentido = sentido,
        fecha = fecha,
    )
}
