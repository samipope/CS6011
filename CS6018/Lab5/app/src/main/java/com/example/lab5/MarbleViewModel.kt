package com.example.lab5

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MarbleViewModel : ViewModel() {
    // sensor data stored here
    private val _offsets = MutableStateFlow(Pair(0f, 0f))
    val offsets = _offsets.asStateFlow()

    // updates sensor offsets
    fun updateOffsets(newOffsets: Pair<Float, Float>) {
        _offsets.value = newOffsets
    }
}
