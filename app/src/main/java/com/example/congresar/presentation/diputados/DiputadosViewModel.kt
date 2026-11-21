package com.example.congresar.presentation.diputados

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.congresar.domain.model.resumenCamara
import com.example.congresar.domain.usecase.GetDiputadosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.Normalizer
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DiputadosViewModel @Inject constructor(
    private val getDiputados: GetDiputadosUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DiputadosUiState())
    val uiState: StateFlow<DiputadosUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getDiputados().collect { diputados ->
                publish { it.copy(diputados = diputados, isLoading = false) }
            }
        }
    }

    fun onQueryChange(query: String) {
        publish { it.copy(query = query) }
    }

    fun onPartidoSelected(partido: String?) {
        publish { it.copy(partidoSeleccionado = partido) }
    }

    fun onProvinciaSelected(provincia: String?) {
        publish { it.copy(provinciaSeleccionada = provincia) }
    }

    fun onClearFilters() {
        publish {
            it.copy(
                partidoSeleccionado = null,
                provinciaSeleccionada = null,
            )
        }
    }

    private fun publish(transform: (DiputadosUiState) -> DiputadosUiState) {
        _uiState.update { derive(transform(it)) }
    }

    private fun derive(state: DiputadosUiState): DiputadosUiState {
        val query = state.query.trim().foldSearch()
        val visibles = state.diputados.filter { diputado ->
            val nombre = diputado.nombre.foldSearch()
            val apellido = diputado.apellido.foldSearch()
            val matchesQuery = query.isEmpty() ||
                apellido.contains(query) ||
                nombre.contains(query) ||
                "$apellido $nombre".contains(query) ||
                "$nombre $apellido".contains(query)
            val matchesPartido = state.partidoSeleccionado == null ||
                diputado.bloquePolitico == state.partidoSeleccionado
            val matchesProvincia = state.provinciaSeleccionada == null ||
                diputado.provincia == state.provinciaSeleccionada
            matchesQuery && matchesPartido && matchesProvincia
        }
        val resumen = resumenCamara(state.diputados)
        return state.copy(
            visibles = visibles,
            partidos = state.diputados.map { it.bloquePolitico }.distinct().sorted(),
            provincias = state.diputados.map { it.provincia }.distinct().sorted(),
            totalBancas = resumen.totalBancas,
            totalBloques = resumen.totalBloques,
        )
    }

    private fun String.foldSearch(): String =
        Normalizer.normalize(this, Normalizer.Form.NFD)
            .replace("\\p{Mn}+".toRegex(), "")
            .lowercase()
}
