package com.example.congresar.domain.model

data class Noticia(
    val id: Int,
    val titulo: String,
    val contenido: String,
    val quote: String? = null,
    val subtitulo: String,
    val fechaPublicacion: String,
    val fuente: String,
    val urlFuente: String,
    val tipo: String? = null,
    val miniatura: Int,
    val relatedTo: List<Int> = emptyList(),
)
