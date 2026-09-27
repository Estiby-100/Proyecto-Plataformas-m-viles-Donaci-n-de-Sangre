package com.uvg.gotavida.ui.vistaadmin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.gotavida.ui.theme.GotaVidaTheme
import com.uvg.gotavida.ui.theme.WarningAccessible
import com.uvg.gotavida.ui.theme.TealAccent
import com.uvg.gotavida.ui.theme.OnTealContainer

data class BloodType(val type: String, val percentage: Int, val colorKey: BloodColorKey)

enum class BloodColorKey { PRIMARY, ORANGE, GREEN, PURPLE, BLUE, GRAY }

@Composable
fun bloodColorFor(key: BloodColorKey): Color {
    return when (key) {
        BloodColorKey.PRIMARY -> MaterialTheme.colorScheme.primary
        BloodColorKey.ORANGE -> WarningAccessible
        BloodColorKey.GREEN -> MaterialTheme.colorScheme.tertiary
        // Tu paleta (Color.kt) no define un morado ni un azul propios;
        // uso estos dos tonos de teal como sustituto más cercano.
        // Dime si quieres que agregue colores dedicados para estos casos.
        BloodColorKey.PURPLE -> TealAccent
        BloodColorKey.BLUE -> OnTealContainer
        BloodColorKey.GRAY -> MaterialTheme.colorScheme.outline
    }
}

@Composable
fun BloodDonationScreen() {
    val bloodTypes = listOf(
        BloodType("O+", 0, BloodColorKey.PRIMARY),
        BloodType("A+", 0, BloodColorKey.ORANGE),
        BloodType("B+", 0, BloodColorKey.GREEN),
        BloodType("AB+", 0, BloodColorKey.PURPLE),
        BloodType("O-", 0, BloodColorKey.BLUE),
        BloodType("A-", 0, BloodColorKey.GRAY),
        BloodType("B-", 0, BloodColorKey.GRAY),
        BloodType("AB-", 0, BloodColorKey.GRAY)
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomNavBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            HeaderSection(userName = "Carlos", location = "Guatemala, Zona 10")
            Spacer(modifier = Modifier.height(20.dp))
            RequestCard()
            Spacer(modifier = Modifier.height(16.dp))
            CurrentRequestsCard(requestCount = 5)
            Spacer(modifier = Modifier.height(16.dp))
            BloodDistributionCard(bloodTypes)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun HeaderSection(userName: String, location: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.Person,
                contentDescription = "Perfil",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "¡Hola, $userName!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = location,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun RequestCard() {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "¿Desea realizar una solicitud?",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = { /* TODO: acción crear solicitud */ },
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text("🩸 Crear solicitud", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun CurrentRequestsCard(requestCount: Int) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Solicitudes Actuales: $requestCount",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { /* TODO: acción ver solicitudes */ },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Text("Ver solicitudes  →", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun BloodDistributionCard(bloodTypes: List<BloodType>) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Distribución de Tipos de Sangre",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            val maxPercentage = bloodTypes.maxOf { it.percentage }.coerceAtLeast(1)
            bloodTypes.forEach { bt ->
                BloodTypeRow(bt, maxPercentage)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun BloodTypeRow(bloodType: BloodType, maxPercentage: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = bloodType.type,
            fontSize = 13.sp,
            modifier = Modifier.width(36.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(bloodType.percentage / maxPercentage.toFloat())
                    .clip(RoundedCornerShape(50))
                    .background(bloodColorFor(bloodType.colorKey))
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "${bloodType.percentage}%",
            fontSize = 13.sp,
            color = Color.White,
            modifier = Modifier.width(38.dp)
        )
    }
}

@Composable
fun BottomNavBar() {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Filled.Notifications, contentDescription = "Alertas") },
            label = { Text("Alertas") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Ajustes") },
            label = { Text("Ajustes") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BloodDonationScreenPreview() {
    GotaVidaTheme {
        BloodDonationScreen()
    }
}