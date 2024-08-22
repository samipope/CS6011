package com.example.helloworld3

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.constraintlayout.widget.ConstraintLayout

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)

        // Apply insets to the root ConstraintLayout instead
        val rootLayout = findViewById<ConstraintLayout>(R.id.rootLayout)
        ViewCompat.setOnApplyWindowInsetsListener(rootLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get the text from the intent
        val buttonText = intent.getStringExtra("button_text")

        // Find the TextView and set the text
        val textView = findViewById<TextView>(R.id.textView3)
        textView.text = buttonText
    }
}
