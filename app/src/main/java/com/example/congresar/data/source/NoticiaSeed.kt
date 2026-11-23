package com.example.congresar.data.source

import com.example.congresar.R
import com.example.congresar.domain.model.Noticia

/**
 * Fictional news used until a database or network source exists.
 * Bill ids in [Noticia.relatedTo] match [LeySeed] ids. [Noticia.miniatura] is kept
 * as a local drawable for now; the image will come from the network (Coil) once
 * there is an API.
 */
object NoticiaSeed {
    val noticias: List<Noticia> = listOf(
        Noticia(
            id = 1,
            titulo = "Diputados abrió el debate del Presupuesto 2027 en comisión",
            subtitulo = "La Comisión de Presupuesto y Hacienda recibió a funcionarios del Ministerio de Economía.",
            contenido = "La Comisión de Presupuesto y Hacienda de la Cámara de Diputados inició el tratamiento del proyecto de presupuesto 2027 con la exposición del secretario de Hacienda. El oficialismo busca emitir dictamen antes de fin de mes para llevar la iniciativa al recinto en la última semana de sesiones ordinarias. La oposición reclamó precisiones sobre la evolución de la inflación y el financiamiento del déficit.",
            fechaPublicacion = "2026-11-20",
            fuente = "Ámbito Financiero",
            urlFuente = "https://congresar.example/noticias/1",
            tipo = "Política",
            miniatura = R.drawable.miniatura_periodico,
            relatedTo = listOf(1),
        ),
        Noticia(
            id = 2,
            titulo = "Presupuesto 2027: expusieron gobernadores y especialistas",
            subtitulo = "Las provincias pidieron mantener las transferencias y revisar la coparticipación.",
            contenido = "En una nueva jornada de audiencias, gobernadores de distintos signos políticos expusieron ante la Comisión de Presupuesto. Los mandatarios provinciales solicitaron que no se reduzcan las transferencias destinadas a obras y programas sociales. Economistas advirtieron por el impacto de la reducción de subsidios energéticos en las tarifas.",
            fechaPublicacion = "2026-11-19",
            fuente = "La Nación",
            urlFuente = "https://congresar.example/noticias/2",
            tipo = "Economía",
            miniatura = R.drawable.miniatura_periodico,
            relatedTo = listOf(1),
        ),
        Noticia(
            id = 3,
            titulo = "El Presupuesto 2027 llega al recinto la próxima semana",
            subtitulo = "El oficialismo confía en reunir los votos necesarios para la aprobación en general.",
            contenido = "Tras obtener dictamen de mayoría en comisión, el proyecto de presupuesto 2027 será tratado en el recinto la próxima semana. El bloque oficialista negocia con gobernadores y con bloques federales para asegurar el quórum y la mayoría. La oposición anticipó que presentará un dictamen de minoría con modificaciones en materia de gasto social.",
            fechaPublicacion = "2026-11-18",
            fuente = "Infobae",
            urlFuente = "https://congresar.example/noticias/3",
            tipo = "Política",
            miniatura = R.drawable.miniatura_periodico,
            relatedTo = listOf(1),
        ),
        Noticia(
            id = 4,
            titulo = "El oficialismo impulsa la reforma del Poder Judicial",
            subtitulo = "El proyecto crea una nueva cámara de casación y modifica el Consejo de la Magistratura.",
            contenido = "El Poder Ejecutivo envió al Congreso un proyecto de reforma integral del Poder Judicial que crea la Cámara Federal de Casación Penal y reduce la cantidad de miembros del Consejo de la Magistratura. La iniciativa también establece plazos máximos para los concursos de jueces y refuerza los mecanismos de control disciplinario.",
            fechaPublicacion = "2026-11-17",
            fuente = "Clarín",
            urlFuente = "https://congresar.example/noticias/4",
            tipo = "Justicia",
            miniatura = R.drawable.miniatura_periodico,
            relatedTo = listOf(2),
        ),
        Noticia(
            id = 5,
            titulo = "Reforma judicial: los cambios que propone el proyecto",
            subtitulo = "Abogados constitucionalistas analizaron el alcance de la iniciativa.",
            contenido = "Especialistas en derecho constitucional coincidieron en que la creación de la nueva cámara de casación busca agilizar causas de delitos complejos. Sin embargo, advirtieron que la reducción del Consejo de la Magistratura requiere un debate profundo sobre la representación de los poderes. El proyecto deberá pasar por la Cámara de Diputados y luego por el Senado.",
            fechaPublicacion = "2026-11-16",
            fuente = "Página/12",
            urlFuente = "https://congresar.example/noticias/5",
            tipo = "Justicia",
            miniatura = R.drawable.miniatura_periodico,
            relatedTo = listOf(2),
        ),
        Noticia(
            id = 6,
            titulo = "Audiencia pública por la reforma del Poder Judicial",
            subtitulo = "Organizaciones de la sociedad civil y colegios de abogados presentaron observaciones.",
            contenido = "La Comisión de Justicia realizó una audiencia pública con más de treinta expositores sobre la reforma del Poder Judicial. Colegios de abogados y organizaciones civiles presentaron observaciones sobre el acceso a la justicia y la independencia de los magistrados. El oficialismo aseguró que incorporará algunos de los aportes al dictamen.",
            fechaPublicacion = "2026-11-15",
            fuente = "Télam",
            urlFuente = "https://congresar.example/noticias/6",
            tipo = "Justicia",
            miniatura = R.drawable.miniatura_periodico,
            relatedTo = listOf(2),
        ),
        Noticia(
            id = 7,
            titulo = "Educación Digital: dictamen de mayoría en la Comisión de Educación",
            subtitulo = "El proyecto que incorpora programación en las escuelas avanza con respaldo del oficialismo.",
            contenido = "La Comisión de Educación emitió dictamen de mayoría sobre el proyecto de Educación Digital, que incorpora programación y pensamiento computacional en el nivel secundario. El texto también prevé la entrega progresiva de dispositivos en escuelas de gestión estatal. La oposición cuestionó la falta de financiamiento para la capacitación docente.",
            fechaPublicacion = "2026-11-14",
            fuente = "El Día",
            urlFuente = "https://congresar.example/noticias/7",
            tipo = "Educación",
            miniatura = R.drawable.miniatura_periodico,
            relatedTo = listOf(3),
        ),
        Noticia(
            id = 8,
            titulo = "Docentes advirtieron sobre la brecha digital en las escuelas",
            subtitulo = "Los gremios docentes pidieron una ley que garantice conectividad y formación.",
            contenido = "Representantes de los gremios docentes expusieron en la Comisión de Educación sobre las dificultades de conectividad en escuelas rurales y periurbanas. Señalaron que la entrega de dispositivos sin formación docente no alcanza para reducir la brecha digital. Reclamaron una partida presupuestaria específica y un plan de capacitación continua.",
            fechaPublicacion = "2026-11-13",
            fuente = "La Capital",
            urlFuente = "https://congresar.example/noticias/8",
            tipo = "Educación",
            miniatura = R.drawable.miniatura_periodico,
            relatedTo = listOf(3),
        ),
        Noticia(
            id = 9,
            titulo = "El rechazo a la Ley de Educación Digital: los argumentos de la oposición",
            subtitulo = "Los bloques que votaron en contra sostienen que el proyecto no contempla el federalismo educativo.",
            contenido = "Tras el rechazo en la Cámara de Diputados, los bloques opositores explicaron sus argumentos. Sostuvieron que la iniciativa avanzaba sobre las autonomías provinciales y que no preveía financiamiento suficiente. Desde el oficialismo anticiparon que presentarán un nuevo proyecto con modificaciones en el próximo período de sesiones.",
            fechaPublicacion = "2026-11-12",
            fuente = "Perfil",
            urlFuente = "https://congresar.example/noticias/9",
            tipo = "Educación",
            miniatura = R.drawable.miniatura_periodico,
            relatedTo = listOf(3),
        ),
    )
}
