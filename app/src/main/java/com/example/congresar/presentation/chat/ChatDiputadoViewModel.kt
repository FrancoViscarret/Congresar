package com.example.congresar.presentation.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.congresar.domain.model.AutorMensaje
import com.example.congresar.domain.model.MensajeChat
import com.example.congresar.domain.usecase.GetDiputadoPerfilUseCase
import com.example.congresar.navigation.AppDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ChatDiputadoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDiputadoPerfil: GetDiputadoPerfilUseCase,
) : ViewModel() {

    private val idDiputado: Int? = savedStateHandle.get<Int>(AppDestinations.PERFIL_ARG)

    private val _uiState = MutableStateFlow(ChatDiputadoUiState())
    val uiState: StateFlow<ChatDiputadoUiState> = _uiState.asStateFlow()

    private var siguienteId = 1L
    private var bienvenidaLista = false

    init {
        val id = idDiputado
        if (id == null) {
            _uiState.value = ChatDiputadoUiState(isLoading = false, notFound = true)
        } else {
            viewModelScope.launch {
                getDiputadoPerfil(id).collect { perfil ->
                    val diputado = perfil?.diputado
                    _uiState.update { estado ->
                        estado.copy(
                            nombre = diputado?.nombre.orEmpty(),
                            apellido = diputado?.apellido.orEmpty(),
                            bloquePolitico = diputado?.bloquePolitico.orEmpty(),
                            isLoading = false,
                            notFound = perfil == null,
                        )
                    }
                }
            }
        }
    }

    fun asegurarBienvenida(texto: String) {
        if (bienvenidaLista || texto.isBlank() || _uiState.value.notFound) return
        bienvenidaLista = true
        agregar(AutorMensaje.DIPUTADO, texto)
    }

    fun onBorradorChange(valor: String) {
        _uiState.update { it.copy(borrador = valor) }
    }

    fun enviar(respuestaSimulada: String) {
        val texto = _uiState.value.borrador.trim()
        if (texto.isEmpty() || _uiState.value.enviando || _uiState.value.notFound) return
        agregar(AutorMensaje.USUARIO, texto)
        _uiState.update { it.copy(borrador = "", enviando = true) }
        viewModelScope.launch {
            delay(RESPUESTA_SIMULADA_MS)
            if (respuestaSimulada.isNotBlank()) {
                agregar(AutorMensaje.DIPUTADO, respuestaSimulada)
            }
            _uiState.update { it.copy(enviando = false) }
        }
    }

    private fun agregar(autor: AutorMensaje, texto: String) {
        val mensaje = MensajeChat(id = siguienteId++, autor = autor, texto = texto)
        _uiState.update { it.copy(mensajes = it.mensajes + mensaje) }
    }

    private companion object {
        const val RESPUESTA_SIMULADA_MS = 700L
    }
}
