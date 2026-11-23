package com.example.congresar.domain.usecase

import com.example.congresar.domain.model.Noticia
import com.example.congresar.domain.repository.NoticiaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoticiasUseCase @Inject constructor(
    private val repository: NoticiaRepository,
) {
    operator fun invoke(): Flow<List<Noticia>> = repository.getNoticias()
}
