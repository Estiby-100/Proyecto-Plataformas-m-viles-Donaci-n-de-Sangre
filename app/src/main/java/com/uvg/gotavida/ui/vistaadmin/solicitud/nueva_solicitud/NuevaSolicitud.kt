package com.uvg.gotavida.ui.vistaadmin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.uvg.gotavida.ui.theme.GotaVidaTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uvg.gotavida.ui.vistaadmin.solicitud.nueva_solicitud.NuevaSolicitudEvent
import com.uvg.gotavida.ui.vistaadmin.solicitud.nueva_solicitud.NuevaSolicitudState
import com.uvg.gotavida.ui.vistaadmin.solicitud.nueva_solicitud.NuevaSolicitudViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevaSolicitud(
    viewModel: NuevaSolicitudViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onEnviarClick: (NuevaSolicitudState) -> Unit = {}
) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nueva Solicitud",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(
                                NuevaSolicitudEvent.BackClick
                            )
                            onBackClick()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            InfoBanner()

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            FormLabel("Nombre completo")

            FormTextField(
                value = state.nombreCompleto,
                onValueChange = {
                    viewModel.onEvent(
                        NuevaSolicitudEvent.NombreChanged(it)
                    )
                },
                placeholder = "Ej. Juan Pérez"
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    FormLabel("Tipo de sangre")

                    FormDropdown(
                        selected = state.tipoSangre,
                        placeholder = "Selecciona",
                        options = listOf(
                            "O",
                            "A",
                            "B",
                            "AB"
                        ),
                        onSelected = {
                            viewModel.onEvent(
                                NuevaSolicitudEvent.TipoSangreChanged(it)
                            )
                        }
                    )
                }

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    FormLabel("Signo")

                    FormDropdown(
                        selected = state.signo,
                        placeholder = "Selecciona",
                        options = listOf(
                            "+",
                            "-"
                        ),
                        onSelected = {
                            viewModel.onEvent(
                                NuevaSolicitudEvent.SignoChanged(it)
                            )
                        }
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            FormLabel("Departamento")

            FormDropdown(
                selected = state.departamento,
                placeholder = "Selecciona un departamento",
                options = listOf(
                    "Guatemala",
                    "Quetzaltenango",
                    "Escuintla",
                    "Sacatepéquez",
                    "Alta Verapaz",
                    "Petén"
                ),
                onSelected = {
                    viewModel.onEvent(
                        NuevaSolicitudEvent.DepartamentoChanged(it)
                    )
                }
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            FormLabel("Detalles adicionales (Opcional)")

            FormTextArea(
                value = state.detalles,
                onValueChange = {
                    viewModel.onEvent(
                        NuevaSolicitudEvent.DetallesChanged(it)
                    )
                },
                placeholder = "Ej. Hospital o contacto de emergencia."
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Button(
                onClick = {

                    viewModel.onEvent(
                        NuevaSolicitudEvent.EnviarSolicitud
                    )

                    onEnviarClick(state)

                },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {

                Text(
                    text = "Enviar solicitud",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }
    }
}

@Composable
private fun InfoBanner() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "Completa la información para publicar la solicitud en la comunidad de donantes.",
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


@Composable
private fun FormLabel(
    text: String
) {
    Text(
        text = text,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
private fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        ),
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
private fun FormTextArea(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FormDropdown(
    selected: String,
    placeholder: String,
    options: List<String>,
    onSelected: (String) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Box {

        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            placeholder = {
                Text(
                    text = placeholder,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        expanded = true
                    }
                ) {
                    Text("▼")
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .width(300.dp)
        ) {
            options.forEach { option ->

                DropdownMenuItem(
                    text = {
                        Text(option)
                    },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun NuevaSolicitudScreenPreview() {
    GotaVidaTheme {
        NuevaSolicitud(
            onBackClick = {},
            onEnviarClick = { _ -> }
        )
    }
}
