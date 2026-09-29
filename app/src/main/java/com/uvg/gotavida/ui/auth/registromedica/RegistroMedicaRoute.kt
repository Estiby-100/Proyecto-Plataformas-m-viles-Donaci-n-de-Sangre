package com.uvg.gotavida.ui.auth.registromedica

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun RegistroMedicaRoute() {
    var uiState by remember { mutableStateOf(RegistroMedicaUiState()) }

    RegistroMedicaScreen(
        uiState = uiState,
        onNombreInstitucionChange = { uiState = uiState.copy(nombreInstitucion = it) },
        onCorreoInstitucionalChange = { uiState = uiState.copy(correoInstitucional = it) },
        onNumeroColegiadoChange = { uiState = uiState.copy(numeroColegiado = it) },
        onEnviarClick = {
            // TODO: enviar a revisión real. Por ahora, solo cambia de fase para
            // poder ver el estado "en revisión" al correr la app.
            uiState = uiState.copy(fase = RegistroMedicaFase.EN_REVISION)
        },
        onVolverAlInicioClick = { /* TODO: navegar a Splash */ },
        onBackClick = { /* TODO: navegar a Registro básico */ },
    )
}
