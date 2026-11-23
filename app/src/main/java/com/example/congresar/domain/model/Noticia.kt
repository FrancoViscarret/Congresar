package com.example.congresar.domain.model

/**
 * News item. Mirrors the `Noticia` entity of the data model:
 * id_noticia, titulo, contenido, fecha_publicacion, fuente, url_fuente, tipo.
 *
 * [miniatura] is the drawable resource used as the carousel card image.
 * [relatedTo] holds the ids of the [Ley] items this news is related to. A news item
 * can be related to one or more laws.
 */
data class Noticia(
    val id: Int,
    val titulo: String,
    val contenido: String,
    val subtitulo: String,
    val fechaPublicacion: String,
    val fuente: String,
    val urlFuente: String,
    val tipo: String? = null,
    val miniatura: Int,
    val relatedTo: List<Int> = emptyList(),
)
