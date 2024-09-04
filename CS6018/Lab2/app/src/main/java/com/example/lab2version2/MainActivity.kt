package com.example.lab2version2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab2version2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)


        binding.fragmentContainerView.getFragment<ClickFragment>().setButtonFunction {
            val drawFragment = DrawFragment()
            val transaction = this.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, drawFragment, "draw_tag")
            transaction.addToBackStack(null)
            transaction.commit()
        }
        setContentView(binding.root)
    }
    }
