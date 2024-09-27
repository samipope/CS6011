package com.example.lab5

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.math.abs
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope


class MainActivity : ComponentActivity() {
    private val marbleViewModel: MarbleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // gravity sensor
        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

        // sensor flow
        val offsetFlow = getOffsets(gravitySensor!!, sensorManager)

        // updates live model
        lifecycleScope.launch {
            offsetFlow.collect { offsets ->
                marbleViewModel.updateOffsets(offsets)
            }
        }

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MarbleScreen(marbleViewModel)
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

                    // Update only if the movement is significant
                    if (abs(sensorX - lastX) > 0.05f || abs(sensorY - lastY) > 0.05f) {
                        lastX = sensorX
                        lastY = sensorY
                        channel.trySend(Pair(sensorX, sensorY))
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // have to have it --> nabil kept empty
            }
        }

        sensorManager.registerListener(listener, gravitySensor, SensorManager.SENSOR_DELAY_GAME)

        awaitClose {
            sensorManager.unregisterListener(listener)
        }
    }
}
