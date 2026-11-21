package com.example.congresar.data.repository

import com.example.congresar.data.source.DiputadoSeed
import com.example.congresar.domain.model.Diputado
import com.example.congresar.domain.model.DiputadoPerfil
import com.example.congresar.domain.repository.DiputadoRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Singleton
class DiputadoRepositoryImpl @Inject constructor() : DiputadoRepository {
    override fun getDiputados(): Flow<List<Diputado>> = flowOf(DiputadoSeed.diputados)

    override fun getPerfil(idDiputado: Int): Flow<DiputadoPerfil?> =
        flowOf(DiputadoSeed.perfiles.find { it.diputado.id == idDiputado })
}
