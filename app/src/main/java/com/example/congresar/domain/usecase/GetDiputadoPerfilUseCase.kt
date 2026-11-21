package com.example.congresar.domain.usecase

import com.example.congresar.domain.model.DiputadoPerfil
import com.example.congresar.domain.model.ordenCronologico
import com.example.congresar.domain.repository.DiputadoRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetDiputadoPerfilUseCase @Inject constructor(
    private val repository: DiputadoRepository,
) {
    operator fun invoke(idDiputado: Int): Flow<DiputadoPerfil?> =
        repository.getPerfil(idDiputado).map { perfil ->
            perfil?.copy(
                votaciones = perfil.votaciones.sortedByDescending { it.ordenCronologico() },
            )
        }
}
