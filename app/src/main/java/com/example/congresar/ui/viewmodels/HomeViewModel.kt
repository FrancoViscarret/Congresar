package com.example.congresar.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.congresar.domain.model.Ley
import com.example.congresar.domain.model.Noticia
import com.example.congresar.domain.usecase.GetLeyesUseCase
import com.example.congresar.domain.usecase.GetNoticiasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * State rendered by [com.example.congresar.ui.screens.HomeScreen].
 */
data class HomeUiState(
    val leyes: List<Ley> = emptyList(),
    val noticias: List<Noticia> = emptyList(),
    val isLoading: Boolean = true,
    val currentLawId: Int? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLeyes: GetLeyesUseCase,
    private val getNoticias: GetNoticiasUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadLeyes()
        loadNoticias()
    }

    fun onItemLawChange(lawId: Int) {

        _uiState.update { currentState ->
            currentState.copy(currentLawId = lawId)
        }

    }

    private fun loadLeyes() {
        viewModelScope.launch {
            getLeyes().collect { leyes ->
                _uiState.update {
                    it.copy(leyes = leyes, isLoading = false)
                }
            }
        }
    }

    private fun loadNoticias() {
        viewModelScope.launch {
            getNoticias().collect { noticias ->
                _uiState.update {
                    it.copy(noticias = noticias, isLoading = false)
                }
            }
        }
    }
}
