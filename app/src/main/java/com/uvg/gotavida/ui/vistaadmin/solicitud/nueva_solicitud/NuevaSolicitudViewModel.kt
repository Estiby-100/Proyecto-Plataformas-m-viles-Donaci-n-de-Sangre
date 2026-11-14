package com.uvg.gotavida.ui.vistaadmin.solicitud.nueva_solicitud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.gotavida.di.ServiceLocator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NuevaSolicitudViewModel : ViewModel() {

    private val _state = MutableStateFlow(NuevaSolicitudState())
    val state: StateFlow<NuevaSolicitudState> = _state.asStateFlow()

    fun onEvent(event: NuevaSolicitudEvent) {

        when (event) {

            is NuevaSolicitudEvent.NombreChanged -> {
                _state.update { it.copy(nombreCompleto = event.nombre, errorMessage = null) }
            }

            is NuevaSolicitudEvent.TipoSangreChanged -> {
                _state.update { it.copy(tipoSangre = event.tipo, errorMessage = null) }
            }

            is NuevaSolicitudEvent.SignoChanged -> {
                _state.update { it.copy(signo = event.signo, errorMessage = null) }
            }

            is NuevaSolicitudEvent.DepartamentoChanged -> {
                _state.update { it.copy(departamento = event.departamento, errorMessage = null) }
            }

            is NuevaSolicitudEvent.DetallesChanged -> {
                _state.update { it.copy(detalles = event.detalles) }
            }

            NuevaSolicitudEvent.EnviarSolicitud -> enviar()

            NuevaSolicitudEvent.NavegacionAtendida -> {
                _state.update { it.copy(enviada = false) }
            }

            NuevaSolicitudEvent.BackClick -> Unit
        }
    }

    private fun enviar() {
        val actual = _state.value

        if (actual.enviando) return

        val faltaAlgo = actual.nombreCompleto.isBlank() ||
            actual.tipoSangre.isBlank() ||
            actual.signo.isBlank() ||
            actual.departamento.isBlank()

        if (faltaAlgo) {
            _state.update {
                it.copy(errorMessage = "Completa nombre, tipo de sangre, signo y departamento.")
            }
            return
        }

        _state.update { it.copy(enviando = true, errorMessage = null) }

        viewModelScope.launch {
            val resultado = runCatching {
                val usuario = ServiceLocator.cuentaRepository.sesionActual()
                    ?: error("No hay sesión iniciada")
                ServiceLocator.solicitudesRepository.crear(
                    medicaUid = usuario.uid,
                    // La institución sale del perfil de la médica, no se vuelve a escribir.
                    institucion = usuario.institucion ?: usuario.nombre,
                    nombrePaciente = actual.nombreCompleto.trim(),
                    zona = actual.departamento,
                    tipoSangre = actual.tipoSangre + actual.signo,
                    detalles = actual.detalles.trim(),
                )
            }

            if (resultado.isSuccess) {
                _state.update { it.copy(enviando = false, enviada = true) }
            } else {
                _state.update {
                    it.copy(
                        enviando = false,
                        errorMessage = "No se pudo enviar la solicitud. Revisa tu conexión e inténtalo de nuevo."
                    )
                }
            }
        }
    }
}
