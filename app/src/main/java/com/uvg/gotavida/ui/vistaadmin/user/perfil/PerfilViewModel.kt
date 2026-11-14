package com.uvg.gotavida.ui.vistaadmin.user.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uvg.gotavida.di.ServiceLocator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        // Empieza vacío para no mostrar datos de ejemplo mientras carga el perfil real.
        PerfilState(perfil = Perfil("", "", "", "", "", "", "", ""))
    )
    val state: StateFlow<PerfilState> = _state.asStateFlow()

    init {
        cargarPerfil()
    }

    /** Llena el perfil con los datos reales guardados en Room al registrarse. */
    private fun cargarPerfil() {
        viewModelScope.launch {
            val usuario = runCatching { ServiceLocator.cuentaRepository.sesionActual() }.getOrNull()
            if (usuario != null) {
                val institucion = usuario.institucion ?: "No registrada"
                _state.update {
                    it.copy(
                        perfil = Perfil(
                            nombre = usuario.nombre,
                            hospital = institucion,
                            correo = usuario.correo,
                            telefono = "No registrado",
                            institucion = institucion,
                            departamento = "Guatemala",
                            rol = "Médica",
                            miembro = "—"
                        )
                    )
                }
            }
        }
    }

    fun onEvent(
        event: PerfilEvent
    ) {

        when (event) {

            // El repositorio se pide aquí y no en el constructor para que el
            // @Preview del perfil no necesite Firebase ni Room.
            PerfilEvent.CerrarSesion -> ServiceLocator.cuentaRepository.cerrarSesion()

            // Solo navegan; no cambian el estado.
            PerfilEvent.BackClick -> Unit
            PerfilEvent.Ajustes -> Unit
        }
    }
}
