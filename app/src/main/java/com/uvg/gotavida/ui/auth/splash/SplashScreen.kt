package com.uvg.gotavida.ui.auth.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uvg.gotavida.ui.components.GotaVidaPrimaryButton
import com.uvg.gotavida.ui.components.GotaVidaSecondaryButton
import com.uvg.gotavida.ui.theme.GotaVidaTheme

/**
 * Stateless — solo pinta la UI. No conoce navegación ni lógica: recibe
 * lambdas y las dispara, quien las use decide qué pasa después.
 */
@Composable
fun SplashScreen(
    onDonorClick: () -> Unit,
    onMedicalClick: () -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Tercio superior: marca
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 96.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LogoPlaceholder()
            Spacer(Modifier.padding(top = 16.dp))
            Text(
                text = "GotaVida",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(Modifier.padding(top = 8.dp))
            Text(
                text = "Conectamos donantes con quienes más lo necesitan.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }

        Spacer(Modifier.weight(1f))

        // Tercio inferior: acciones
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            GotaVidaPrimaryButton(
                text = "Soy donante",
                onClick = onDonorClick,
                modifier = Modifier.fillMaxWidth(),
            )
            GotaVidaSecondaryButton(
                text = "Soy personal médico o institución",
                onClick = onMedicalClick,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.padding(top = 4.dp))
            LoginLinkText(onLoginClick)
        }
    }
}

@Composable
private fun LoginLinkText(onLoginClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "¿Ya tienes cuenta? ",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = "Iniciar sesión",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.clickable { onLoginClick() },
        )
    }
}

/**
 * Placeholder del logo mientras no exportas el ícono real desde Figma.
 * Un círculo suave con un ícono adentro, en tono coral.
 */
@Composable
private fun LogoPlaceholder() {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.size(72.dp),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp),
            )
        }
    }
}

// --- Previews: uno por estado relevante. Splash solo tiene un estado. ---

@Preview(showBackground = true, name = "Splash - Normal")
@Composable
private fun SplashScreenPreview() {
    GotaVidaTheme {
        SplashScreen(
            onDonorClick = {},
            onMedicalClick = {},
            onLoginClick = {},
        )
    }
}
