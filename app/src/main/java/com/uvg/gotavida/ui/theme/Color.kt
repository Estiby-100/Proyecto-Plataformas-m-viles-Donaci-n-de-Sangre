package com.uvg.gotavida.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Paleta "Confianza Cálida" definida en el diseño de Figma del flujo del donante.
 * Estos son los únicos valores hexadecimales del proyecto: todo lo demás en las
 * pantallas debe leer sus colores de MaterialTheme.colorScheme, nunca repetir un hex.
 */

// Coral - solo para elementos decorativos grandes (marcadores, ilustraciones) o botones con texto blanco.
val CoralAccent = Color(0xFFE85D4A)
val CoralAction = Color(0xFFC94332)
val CoralContainer = Color(0xFFF7DAD3)
val OnCoralContainer = Color(0xFFA93627)

// Teal - ubicación, jornadas, información secundaria.
val TealAccent = Color(0xFF2A9D8F)
val TealAccessible = Color(0xFF147A70)
val TealContainer = Color(0xFFD9EFEC)
val OnTealContainer = Color(0xFF0F5850)

// Semánticos: éxito y advertencia (sin rol propio en Material 3, se usan como
// constantes locales igual que en los ejercicios del curso, no como parte del scheme).
val SuccessAccessible = Color(0xFF237A54)
val SuccessContainer = Color(0xFFE6F4EC)
val WarningAccessible = Color(0xFF8A5A00)
val WarningContainer = Color(0xFFFBEFD9)
val ErrorAccessible = Color(0xFFC93535)
val ErrorContainer = Color(0xFFF8D7D5)
val OnErrorContainer = Color(0xFF8C2A26)

// Fondo y superficies.
val FondoApp = Color(0xFFFAFAF8)
val SuperficieApp = Color(0xFFFFFFFF)
val SuperficieAlterna = Color(0xFFF1EFEC)

// Texto y bordes.
val TextoPrimario = Color(0xFF1F2933)
val TextoSecundario = Color(0xFF5A6672)
val Borde = Color(0xFFE3E1DD)

// Variante oscura (mismos acentos, superficies oscurecidas).
val FondoAppDark = Color(0xFF15181B)
val SuperficieAppDark = Color(0xFF1E2226)
val SuperficieAlternaDark = Color(0xFF262B2F)
val TextoPrimarioDark = Color(0xFFECEDEE)
val TextoSecundarioDark = Color(0xFFB4BAC0)
val BordeDark = Color(0xFF3A4045)
