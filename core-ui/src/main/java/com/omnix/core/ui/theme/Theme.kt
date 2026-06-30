package com.omnix.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * App-wide theme wrapper. Supports:
 *  - Dark / light mode (system-driven or explicit override)
 *  - Material You dynamic color on API 31+
 *  - Falls back to the bespoke OMNIX violet/teal palette otherwise
 */
@Composable
fun OmnixTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> OmnixDarkColorScheme
        else -> OmnixLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = OmnixTypography,
        shapes = OmnixShapes,
        content = content
    )
}
