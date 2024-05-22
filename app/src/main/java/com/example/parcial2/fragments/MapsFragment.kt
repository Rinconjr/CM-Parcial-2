package com.example.parcial2.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.parcial2.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    private lateinit var gMap: GoogleMap
    val javeriana = LatLng(4.628859, -74.064919)
    val sydney = LatLng(-34.0, 151.0)
    var zoomLevel = 15.0f
    var moveCamera = true
    private lateinit var marcador: Marker


    //Funcion para manipular el mapa cuando este listo
    private val callback = OnMapReadyCallback { googleMap ->
        gMap = googleMap

        // Ponerle los controles de ui al mapa
        gMap.uiSettings.isZoomControlsEnabled = true
        gMap.uiSettings.isMapToolbarEnabled = true
        gMap.uiSettings.isCompassEnabled = true

        // Estilo del mapa
//        gMap.setMapStyle(context?.let { MapStyleOptions.loadRawResourceStyle(it, R.raw.map_day) })

        // Poner un marcador en la universidad y mover la camara ahi
        marcador = gMap.addMarker(MarkerOptions().position(sydney).title("Marcador").icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)))!!
        gMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        gMap.setOnMapLongClickListener { latLng -> addPoint(latLng) }
    }

    //Funcion para agregar marcador al mapa con setOnMapLongClickListener
    private fun addPoint(latLng: LatLng) {
        gMap.addMarker(MarkerOptions().position(latLng).title("Nuevo marcador").icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        /* Sensor de luz
        sensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)!!
         */
    }

    fun movePosition(location: Location){
        val latLng = LatLng(location.latitude, location.longitude)
        marcador.position = latLng
        marcador.zIndex = 10.0f
        if(moveCamera){
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))
        }
    }

    override fun onResume() {
        super.onResume()
//        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
//        sensorManager.unregisterListener(this)
    }
}