package com.example.lab5

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the SensorManager and gravity sensor
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

        // Get the flow of sensor data for offsets
        val offsetFlow = getOffsets(gravitySensor!!, sensorManager)

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MarbleScreen(offsetFlow)
            }
        }
    }
}

fun getOffsets(gravitySensor: Sensor, sensorManager: SensorManager): Flow<Pair<Float, Float>> {
    return channelFlow {
        var lastX = 0f
        var lastY = 0f
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event != null) {
                    val sensorX = event.values[0]
                    val sensorY = event.values[1]

                    // Update only if the movement is significant (e.g., threshold of 0.05f)
                    if (abs(sensorX - lastX) > 0.05f || abs(sensorY - lastY) > 0.05f) {
                        lastX = sensorX
                        lastY = sensorY
                        channel.trySend(Pair(sensorX, sensorY))
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // Not used in this example
            }
        }

        sensorManager.registerListener(listener, gravitySensor, SensorManager.SENSOR_DELAY_GAME)

        awaitClose {
            sensorManager.unregisterListener(listener)
        }
    }
}


@Composable
fun MarbleScreen(offsetFlow: Flow<Pair<Float, Float>>) {
    // Collect the offset data from the Flow
    val offsets by offsetFlow.collectAsStateWithLifecycle(initialValue = Pair(0f, 0f))

    // Destructure the offsets
    val (xOffset, yOffset) = offsets

    val density = LocalDensity.current
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val maxWidth = maxWidth
        val maxHeight = maxHeight

        // Define the range for sensor values (-9 to 9 typically)
        val sensorRange = 9f

        // Increase scaling factor to amplify movement
        val scalingFactor = 1.5f  // You can adjust this for more/less movement

        // Invert the xOffset to fix the inverted direction
        val normalizedXOffset = (-xOffset / sensorRange) * (maxWidth / 2).value * scalingFactor
        val normalizedYOffset = (yOffset / sensorRange) * (maxHeight / 2).value * scalingFactor

        // Convert normalized offsets (Float) from pixels to Dp
        val xOffsetDp: Dp = with(density) { normalizedXOffset.toDp() }
        val yOffsetDp: Dp = with(density) { normalizedYOffset.toDp() }

        // Display the marble and the coordinates
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Text to display the current xOffset and yOffset values
            Text(
                text = "X: $xOffset, Y: $yOffset",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )

            // Create the rolling marble
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
