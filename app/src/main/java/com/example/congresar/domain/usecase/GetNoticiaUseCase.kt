package com.example.congresar.domain.usecase

import com.example.congresar.domain.model.Noticia
import com.example.congresar.domain.repository.NoticiaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoticiaUseCase @Inject constructor(
    private val repository: NoticiaRepository,
) {
    operator fun invoke(idNoticia: Int): Flow<Noticia?> = repository.getNoticia(idNoticia)
}
