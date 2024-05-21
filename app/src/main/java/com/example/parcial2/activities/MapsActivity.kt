package com.example.parcial2.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.parcial2.databinding.ActivityMapsBinding

class MapsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMapsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}