package com.example.congresar.data.repository

import com.example.congresar.data.source.NoticiaSeed
import com.example.congresar.domain.model.Noticia
import com.example.congresar.domain.repository.NoticiaRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Singleton
class NoticiaRepositoryImpl @Inject constructor() : NoticiaRepository {
    override fun getNoticias(): Flow<List<Noticia>> = flowOf(NoticiaSeed.noticias)

    override fun getNoticia(idNoticia: Int): Flow<Noticia?> =
        flowOf(NoticiaSeed.noticias.find { it.id == idNoticia })
}
