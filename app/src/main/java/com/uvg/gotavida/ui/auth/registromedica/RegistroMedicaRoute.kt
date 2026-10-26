package com.uvg.gotavida.ui.auth.registromedica

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RegistroMedicaRoute(
    onVolverAlInicio: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: RegistroMedicaViewModel = viewModel()
) {
    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(uiState.volverAlInicio) {
        if (!uiState.volverAlInicio) return@LaunchedEffect
        viewModel.onEvent(RegistroMedicaEvent.NavegacionAtendida)
        onVolverAlInicio()
    }

    RegistroMedicaScreen(
        uiState = uiState,
        onNombreInstitucionChange = { viewModel.onEvent(RegistroMedicaEvent.Institucion(it)) },
        onCorreoInstitucionalChange = { viewModel.onEvent(RegistroMedicaEvent.Correo(it)) },
        onNumeroColegiadoChange = { viewModel.onEvent(RegistroMedicaEvent.NumeroColegiado(it)) },
        onEnviarClick = { viewModel.onEvent(RegistroMedicaEvent.Enviar) },
        onVolverAlInicioClick = { viewModel.onEvent(RegistroMedicaEvent.Inicio) },
        onBackClick = {
            viewModel.onEvent(RegistroMedicaEvent.Back)
            onBackClick()
        },
    )
}
