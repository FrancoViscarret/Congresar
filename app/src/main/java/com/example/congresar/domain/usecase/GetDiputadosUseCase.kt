package com.example.congresar.domain.usecase

import com.example.congresar.domain.model.Diputado
import com.example.congresar.domain.repository.DiputadoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDiputadosUseCase @Inject constructor(
    private val repository: DiputadoRepository,
) {
    operator fun invoke(): Flow<List<Diputado>> = repository.getDiputados()
}
