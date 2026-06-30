package com.omnix.core.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.omnix.core.ui.theme.OmnixRadii

/**
 * A shimmering placeholder block used while content (recent chats, model
 * status, council nodes) is loading. Animates a soft highlight sweep
 * left-to-right on an infinite loop.
 */
@Composable
fun ShimmerSkeleton(
    modifier: Modifier = Modifier,
    height: Dp = 64.dp
) {
    val baseColor = MaterialTheme.colorScheme.surfaceVariant
    val highlightColor = MaterialTheme.colorScheme.surface

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = -1000f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslate"
    )

    val brush = Brush.linearGradient(
        colors = listOf(baseColor, highlightColor, baseColor),
        start = Offset(translateAnim, 0f),
        end = Offset(translateAnim + 400f, 400f)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(OmnixRadii.card)
            .background(brush)
    )
}