package com.uvg.gotavida.vistaadmin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.gotavida.ui.theme.GotaVidaTheme

data class PerfilInstitucional(
    val nombre: String,
    val rolYHospital: String,
    val correo: String,
    val telefono: String,
    val institucion: String,
    val departamento: String,
    val rol: String,
    val miembroDesde: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilInstitucionalScreen(
    perfil: PerfilInstitucional = PerfilInstitucional(
        nombre = "Carlos Mendoza",
        rolYHospital = "Médico - Hospital General",
        correo = "carlos.mendoza@hospital.gt",
        telefono = "+502 5555-1234",
        institucion = "Hospital General de Guatemala",
        departamento = "Guatemala",
        rol = "Médico Solicitante",
        miembroDesde = "Enero 2024"
    ),
    onBackClick: () -> Unit = {},
    onCerrarSesion: () -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Perfil Institucional",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
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
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // Avatar + nombre
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Avatar",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = perfil.nombre,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = perfil.rolYHospital,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Card(
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Datos de la Cuenta",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    PerfilField(
                        label = "Correo electrónico",
                        value = perfil.correo
                    )

                    PerfilField(
                        label = "Teléfono",
                        value = perfil.telefono
                    )

                    PerfilField(
                        label = "Institución",
                        value = perfil.institucion
                    )

                    PerfilField(
                        label = "Departamento",
                        value = perfil.departamento
                    )

                    PerfilField(
                        label = "Rol",
                        value = perfil.rol
                    )

                    PerfilField(
                        label = "Miembro desde",
                        value = perfil.miembroDesde,
                        isLast = true
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            OutlinedButton(
                onClick = onCerrarSesion,
                shape = MaterialTheme.shapes.extraSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = "Cerrar sesión",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }
    }
}

@Composable
private fun PerfilField(
    label: String,
    value: String,
    isLast: Boolean = false
) {

    Column {

        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }

    if (!isLast) {
        Spacer(
            modifier = Modifier.height(14.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilInstitucionalScreenPreview() {
    GotaVidaTheme {
        PerfilInstitucionalScreen()
    }
}