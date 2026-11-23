package com.example.congresar.domain.repository

import com.example.congresar.domain.model.Noticia
import kotlinx.coroutines.flow.Flow

interface NoticiaRepository {
    fun getNoticias(): Flow<List<Noticia>>

    fun getNoticia(idNoticia: Int): Flow<Noticia?>
}
