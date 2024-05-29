package com.example.parcial2.activities

import android.content.Intent
import android.location.Location
import android.location.LocationRequest
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.parcial2.R
import com.example.parcial2.databinding.ActivityMainBinding
import com.example.parcial2.fragments.MapsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragment: MapsFragment
    lateinit var textoDistancia: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textoDistancia = binding.textoDistancia

        fragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as MapsFragment

        binding.button.setOnClickListener{
            //Borrar los marcadores y las lineas del mapa
            fragment.clearMap()
        }
    }

}