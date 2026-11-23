package com.example.congresar.data.repository

import com.example.congresar.data.source.LeySeed
import com.example.congresar.domain.model.Ley
import com.example.congresar.domain.repository.LeyRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Singleton
class LeyRepositoryImpl @Inject constructor() : LeyRepository {
    override fun getLeyes(): Flow<List<Ley>> = flowOf(LeySeed.leyes)

    override fun getLey(idLey: Int): Flow<Ley?> =
        flowOf(LeySeed.leyes.find { it.id == idLey })
}
