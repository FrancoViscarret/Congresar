package com.example.congresar.domain.usecase

import com.example.congresar.domain.model.Ley
import com.example.congresar.domain.repository.LeyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLeyUseCase @Inject constructor(
    private val repository: LeyRepository,
) {
    operator fun invoke(idLey: Int): Flow<Ley?> = repository.getLey(idLey)
}
