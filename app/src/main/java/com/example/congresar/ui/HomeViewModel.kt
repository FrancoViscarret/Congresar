package com.example.congresar.ui

import androidx.lifecycle.ViewModel
import com.example.congresar.R
import com.example.congresar.data.Ley
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * State rendered by [com.example.congresar.ui.screens.HomeScreen].
 */
data class HomeUiState(
    val leyes: List<Ley> = emptyList(),
    val isLoading: Boolean = false,
)

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadLeyes()
    }

    // TODO: replace with the repository backed by Room / the API.
    private fun loadLeyes() {
        _uiState.value = HomeUiState(
            leyes = listOf(
                Ley(
                    id = 1,
                    titulo = "Presupuesto 2027",
                    descripcion = "Asignación de partidas para el ejercicio financiero 2027.",
                    estado = "En tratamiento",
                    fechaPresentacion = "2026-11-01",
                    miniatura = R.drawable.miniatura_ley,
                ),
                Ley(
                    id = 2,
                    titulo = "Reforma del Poder Judicial",
                    descripcion = "Cambios en la integración y competencias de los tribunales.",
                    estado = "Aprobada",
                    fechaPresentacion = "2026-10-14",
                    miniatura = R.drawable.miniatura_ley,
                ),
                Ley(
                    id = 3,
                    titulo = "Ley de Educación Digital",
                    descripcion = "Incorporación de herramientas digitales en la currícula escolar.",
                    estado = "Rechazada",
                    fechaPresentacion = "2026-09-30",
                    miniatura = R.drawable.miniatura_ley,
                ),
            )
        )
    }
}
