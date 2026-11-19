package com.uvg.gotavida.ui.auth.registromedica

import com.uvg.gotavida.data.fake.FakeHospitalesDataSource
import com.uvg.gotavida.data.model.Institucion

enum class RegistroMedicaFase {
    FORMULARIO,
    EN_REVISION,
}

data class RegistroMedicaUiState(
    val fase: RegistroMedicaFase = RegistroMedicaFase.FORMULARIO,
    val nombreInstitucion: String = "",
    val correoInstitucional: String = "",
    val numeroColegiado: String = "",
    /** Lista de hospitales. Empieza con la lista de respaldo y se reemplaza con la de Firestore. */
    val instituciones: List<Institucion> = FakeHospitalesDataSource.instituciones,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    /** true después de "Volver al inicio": la sesión ya se cerró y la vista vuelve a Splash. */
    val volverAlInicio: Boolean = false,
) {
    val isContinueEnabled: Boolean
        get() = nombreInstitucion.isNotBlank() && correoInstitucional.isNotBlank() && !isLoading

    /** Hasta 4 hospitales que coinciden con lo escrito; vacío si ya se eligió uno exacto. */
    val sugerencias: List<Institucion>
        get() {
            val texto = nombreInstitucion.trim()
            if (texto.isEmpty()) return emptyList()
            if (instituciones.any { it.nombre.equals(texto, ignoreCase = true) }) return emptyList()
            return instituciones.filter { it.nombre.contains(texto, ignoreCase = true) }.take(4)
        }
}

sealed interface RegistroMedicaEvent {
    data class Institucion(
        val nombre: String
    ) : RegistroMedicaEvent
    data class Correo(
        val correo: String
    ) : RegistroMedicaEvent
    data class NumeroColegiado(
        val numero: String
    ) : RegistroMedicaEvent
    data object Enviar : RegistroMedicaEvent
    data object Inicio : RegistroMedicaEvent
    data object Back : RegistroMedicaEvent
    data object NavegacionAtendida : RegistroMedicaEvent
}
