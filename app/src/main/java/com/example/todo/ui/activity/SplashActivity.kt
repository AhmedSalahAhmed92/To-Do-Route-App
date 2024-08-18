package com.example.todo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigateToHomeActivity()
    }

    private fun navigateToHomeActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}