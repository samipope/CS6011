package com.example.lab5

import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.material.Text


@Composable
fun MarbleScreen(marbleViewModel: MarbleViewModel) {
    // observe offsets
    val offsets by marbleViewModel.offsets.collectAsState()

    // destructure offset
    val (xOffset, yOffset) = offsets

    val density = LocalDensity.current
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val maxWidth = maxWidth
        val maxHeight = maxHeight
        // have to scale to make moving not weird
        val scalingFactor = 1.5f
        val normalizedXOffset = (-xOffset / 9f) * (maxWidth / 2).value * scalingFactor
        val normalizedYOffset = (yOffset / 9f) * (maxHeight / 2).value * scalingFactor

        val xOffsetDp: Dp = with(density) { normalizedXOffset.toDp() }
        val yOffsetDp: Dp = with(density) { normalizedYOffset.toDp() }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "X: $xOffset, Y: $yOffset", fontSize = 24.sp, modifier = Modifier.padding(16.dp))

            Box(
                modifier = Modifier
                    .offset(
                        x = xOffsetDp.coerceIn(-maxWidth / 2, maxWidth / 2),
                        y = yOffsetDp.coerceIn(-maxHeight / 2, maxHeight / 2)
                    )
                    .size(50.dp)
                    .background(Color.Red, shape = androidx.compose.foundation.shape.CircleShape)
            )
        }
    }
}
