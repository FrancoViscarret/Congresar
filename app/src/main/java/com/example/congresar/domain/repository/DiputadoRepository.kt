package com.example.congresar.domain.repository

import com.example.congresar.domain.model.Diputado
import com.example.congresar.domain.model.DiputadoPerfil
import kotlinx.coroutines.flow.Flow

interface DiputadoRepository {
    fun getDiputados(): Flow<List<Diputado>>

    fun getPerfil(idDiputado: Int): Flow<DiputadoPerfil?>
}
