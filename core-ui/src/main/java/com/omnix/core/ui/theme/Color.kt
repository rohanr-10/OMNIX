package com.omnix.core.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// OMNIX brand palette — deep indigo/violet core with a luminous accent,
// designed to feel premium in both light and dark contexts.

val OmnixVioletPrimary = Color(0xFF6750E8)
val OmnixVioletPrimaryDark = Color(0xFFC8BBFF)
val OmnixIndigoSecondary = Color(0xFF4A4870)
val OmnixIndigoSecondaryDark = Color(0xFFC9C5DD)
val OmnixTealAccent = Color(0xFF00D4B8)
val OmnixTealAccentDark = Color(0xFF4DF2D9)

val OmnixErrorRed = Color(0xFFBA1A1A)
val OmnixErrorRedDark = Color(0xFFFFB4AB)

val OmnixSurfaceLight = Color(0xFFFCF8FF)
val OmnixSurfaceDark = Color(0xFF131218)
val OmnixSurfaceContainerLight = Color(0xFFF0EBFA)
val OmnixSurfaceContainerDark = Color(0xFF1E1C26)

val OmnixOnSurfaceLight = Color(0xFF1B1B23)
val OmnixOnSurfaceDark = Color(0xFFE6E1E9)

val OmnixLightColorScheme = lightColorScheme(
    primary = OmnixVioletPrimary,
    onPrimary = Color.White,
    secondary = OmnixIndigoSecondary,
    onSecondary = Color.White,
    tertiary = OmnixTealAccent,
    onTertiary = Color.Black,
    error = OmnixErrorRed,
    onError = Color.White,
    background = OmnixSurfaceLight,
    onBackground = OmnixOnSurfaceLight,
    surface = OmnixSurfaceLight,
    onSurface = OmnixOnSurfaceLight,
    surfaceVariant = OmnixSurfaceContainerLight,
    onSurfaceVariant = OmnixOnSurfaceLight
)

val OmnixDarkColorScheme = darkColorScheme(
    primary = OmnixVioletPrimaryDark,
    onPrimary = Color(0xFF2B1F70),
    secondary = OmnixIndigoSecondaryDark,
    onSecondary = Color(0xFF302E4A),
    tertiary = OmnixTealAccentDark,
    onTertiary = Color(0xFF00382F),
    error = OmnixErrorRedDark,
    onError = Color(0xFF690005),
    background = OmnixSurfaceDark,
    onBackground = OmnixOnSurfaceDark,
    surface = OmnixSurfaceDark,
    onSurface = OmnixOnSurfaceDark,
    surfaceVariant = OmnixSurfaceContainerDark,
    onSurfaceVariant = OmnixOnSurfaceDark
)
