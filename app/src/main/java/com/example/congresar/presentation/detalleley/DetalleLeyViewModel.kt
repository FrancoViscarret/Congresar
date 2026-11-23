package com.example.congresar.presentation.detalleley

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.congresar.domain.model.Diputado
import com.example.congresar.domain.model.Ley
import com.example.congresar.domain.usecase.GetDiputadosUseCase
import com.example.congresar.domain.usecase.GetLeyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DetalleLeyUiState(
    val ley: Ley? = null,
    val diputados: List<Diputado> = emptyList(),
    val isLoading: Boolean = true,
)

@HiltViewModel
class DetalleLeyViewModel @Inject constructor(
    private val getLey: GetLeyUseCase,
    private val getDiputados: GetDiputadosUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalleLeyUiState())
    val uiState: StateFlow<DetalleLeyUiState> = _uiState.asStateFlow()

    private var loadedLeyId: Int? = null
    private var hasLoaded = false

    fun load(idLey: Int?) {
        if (hasLoaded && loadedLeyId == idLey) return
        hasLoaded = true
        loadedLeyId = idLey

        viewModelScope.launch {
            getDiputados().collect { diputados ->
                _uiState.update { it.copy(diputados = diputados) }
            }
        }

        if (idLey == null) {
            _uiState.update { it.copy(isLoading = false) }
            return
        }

        viewModelScope.launch {
            getLey(idLey).collect { ley ->
                _uiState.update { it.copy(ley = ley, isLoading = false) }
            }
        }
    }
}
