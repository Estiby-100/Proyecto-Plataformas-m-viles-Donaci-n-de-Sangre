package com.uvg.gotavida.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Radios de "Confianza Cálida" expuestos como MaterialTheme.shapes, para que ninguna
 * pantalla tenga que escribir un RoundedCornerShape a mano.
 *
 *  extraSmall -> chips/badges (pastilla)
 *  small      -> inputs (12dp)
 *  medium     -> botones (16dp)
 *  large      -> tarjetas (20dp)
 *  extraLarge -> hojas inferiores / diálogos (28dp)
 */
val GotaVidaShapes = Shapes(
    extraSmall = RoundedCornerShape(percent = 50),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

private val LightColorScheme = lightColorScheme(
    primary = CoralAction,
    onPrimary = Color.White,
    primaryContainer = CoralContainer,
    onPrimaryContainer = OnCoralContainer,

    secondary = TealAccessible,
    onSecondary = Color.White,
    secondaryContainer = TealContainer,
    onSecondaryContainer = OnTealContainer,

    tertiary = SuccessAccessible,
    onTertiary = Color.White,
    tertiaryContainer = SuccessContainer,
    onTertiaryContainer = SuccessAccessible,

    error = ErrorAccessible,
    onError = Color.White,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,

    background = FondoApp,
    onBackground = TextoPrimario,

    surface = SuperficieApp,
    onSurface = TextoPrimario,
    surfaceVariant = SuperficieAlterna,
    onSurfaceVariant = TextoSecundario,

    outline = Borde,
    outlineVariant = Borde
)

private val DarkColorScheme = darkColorScheme(
    primary = CoralAccent,
    onPrimary = Color.Black,
    primaryContainer = OnCoralContainer,
    onPrimaryContainer = CoralContainer,

    secondary = TealAccent,
    onSecondary = Color.Black,
    secondaryContainer = OnTealContainer,
    onSecondaryContainer = TealContainer,

    tertiary = SuccessAccessible,
    onTertiary = Color.Black,
    tertiaryContainer = SuccessAccessible,
    onTertiaryContainer = SuccessContainer,

    error = ErrorAccessible,
    onError = Color.Black,
    errorContainer = OnErrorContainer,
    onErrorContainer = ErrorContainer,

    background = FondoAppDark,
    onBackground = TextoPrimarioDark,

    surface = SuperficieAppDark,
    onSurface = TextoPrimarioDark,
    surfaceVariant = SuperficieAlternaDark,
    onSurfaceVariant = TextoSecundarioDark,

    outline = BordeDark,
    outlineVariant = BordeDark
)

@Composable
fun GotaVidaTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = GotaVidaShapes,
        content = content
    )
}
