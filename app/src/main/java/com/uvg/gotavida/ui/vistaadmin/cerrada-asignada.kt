package com.uvg.gotavida.vistaadmin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uvg.gotavida.ui.theme.ErrorAccessible
import com.uvg.gotavida.ui.theme.ErrorContainer
import com.uvg.gotavida.ui.theme.GotaVidaTheme
import com.uvg.gotavida.ui.theme.SuccessAccessible
import com.uvg.gotavida.ui.theme.SuccessContainer


@Composable
fun StatusScreen(
    icon: ImageVector,
    badgeColor: Color,
    ringBackground: Color,
    sparkleColor: Color,
    title: String,
    subtitle: String,
    linkText: String = "Volver al dashboard",
    onLinkClick: () -> Unit = {}
) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
        ) {

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            DecorativeBar()

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                StatusBadge(
                    icon = icon,
                    badgeColor = badgeColor,
                    ringBackground = ringBackground,
                    sparkleColor = sparkleColor
                )

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                if (subtitle.isNotBlank()) {

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = subtitle,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(1.dp)
                        .background(
                            MaterialTheme.colorScheme.outline
                        )
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                Text(
                    text = linkText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        onLinkClick()
                    }
                )
            }

            DecorativeBar()

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }
    }
}

@Composable
private fun DecorativeBar() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 100.dp)
            .height(4.dp)
            .clip(MaterialTheme.shapes.extraSmall)
            .background(
                MaterialTheme.colorScheme.outline
            )
    )
}

@Composable
private fun StatusBadge(
    icon: ImageVector,
    badgeColor: Color,
    ringBackground: Color,
    sparkleColor: Color
) {

    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .background(ringBackground),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(badgeColor),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

@Composable
fun SolicitudAsignadaScreen(
    onVolverAlDashboard: () -> Unit = {}
) {

    StatusScreen(
        icon = Icons.Filled.Person,
        badgeColor = SuccessAccessible,
        ringBackground = SuccessContainer,
        sparkleColor = SuccessAccessible,
        title = "Solicitud asignada",
        subtitle = "La solicitud ha sido asignada exitosamente.",
        onLinkClick = onVolverAlDashboard
    )
}

@Composable
fun SolicitudCerradaScreen(
    onVolverAlDashboard: () -> Unit = {}
) {

    StatusScreen(
        icon = Icons.Filled.Lock,
        badgeColor = ErrorAccessible,
        ringBackground = ErrorContainer,
        sparkleColor = ErrorAccessible,
        title = "Solicitud cerrada",
        subtitle = "La solicitud ha sido cerrada correctamente.",
        onLinkClick = onVolverAlDashboard
    )
}

@Preview(
    showBackground = true
)
@Composable
fun SolicitudAsignadaScreenPreview() {
    GotaVidaTheme {
        SolicitudAsignadaScreen()
    }
}

@Preview(
    showBackground = true
)
@Composable
fun SolicitudCerradaScreenPreview() {
    GotaVidaTheme {
        SolicitudCerradaScreen()
    }
}