package com.omnix.core.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val OmnixShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

// Additional bespoke radii used outside the Material shape slots,
// e.g. for chat bubbles and the AI Council node graph.
object OmnixRadii {
    val chatBubbleUser = RoundedCornerShape(
        topStart = 20.dp,
        topEnd = 20.dp,
        bottomStart = 20.dp,
        bottomEnd = 4.dp
    )
    val chatBubbleAssistant = RoundedCornerShape(
        topStart = 20.dp,
        topEnd = 20.dp,
        bottomStart = 4.dp,
        bottomEnd = 20.dp
    )
    val card = RoundedCornerShape(20.dp)
    val pill = RoundedCornerShape(50)
}
