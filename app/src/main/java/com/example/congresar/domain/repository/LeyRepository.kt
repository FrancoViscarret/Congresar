package com.example.congresar.domain.repository

import com.example.congresar.domain.model.Ley
import kotlinx.coroutines.flow.Flow

interface LeyRepository {
    fun getLeyes(): Flow<List<Ley>>

    fun getLey(idLey: Int): Flow<Ley?>
}
