package com.example.congresar.presentation.perfil

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.congresar.domain.usecase.GetDiputadoPerfilUseCase
import com.example.congresar.navigation.AppDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PerfilDiputadoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDiputadoPerfil: GetDiputadoPerfilUseCase,
) : ViewModel() {

    private val idDiputado: Int? = savedStateHandle.get<Int>(AppDestinations.PERFIL_ARG)

    private val _uiState = MutableStateFlow(PerfilDiputadoUiState())
    val uiState: StateFlow<PerfilDiputadoUiState> = _uiState.asStateFlow()

    init {
        val id = idDiputado
        if (id == null) {
            _uiState.value = PerfilDiputadoUiState(isLoading = false, notFound = true)
        } else {
            viewModelScope.launch {
                getDiputadoPerfil(id).collect { perfil ->
                    _uiState.value = PerfilDiputadoUiState(
                        perfil = perfil,
                        isLoading = false,
                        notFound = perfil == null,
                    )
                }
            }
        }
    }
}
