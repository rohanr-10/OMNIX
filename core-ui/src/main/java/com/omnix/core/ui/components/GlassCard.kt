package com.omnix.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.omnix.core.ui.theme.OmnixRadii

/**
 * A frosted-glass styled surface: a translucent gradient fill with a subtle
 * light border, used for hero sections, cards, and overlay panels.
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val surfaceColor = MaterialTheme.colorScheme.surface
    val tintColor = MaterialTheme.colorScheme.primary

    Box(
        modifier = modifier
            .clip(OmnixRadii.card)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        surfaceColor.copy(alpha = 0.55f),
                        tintColor.copy(alpha = 0.10f)
                    )
                )
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.12f),
                shape = OmnixRadii.card
            )
    ) {
        content()
    }
}
