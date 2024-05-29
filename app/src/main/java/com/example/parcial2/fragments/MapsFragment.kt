package com.example.parcial2.fragments

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.parcial2.R
import com.example.parcial2.activities.MainActivity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Dot
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PatternItem

class MapsFragment : Fragment() {
    private lateinit var gMap: GoogleMap
    val inicio = LatLng(4.627835831777713, -74.06409737200865)
    var zoomLevel = 15.0f
    private lateinit var marcador: Marker
    private val marcadores = mutableListOf<LatLng>()


    //Funcion para manipular el mapa cuando este listo
    private val callback = OnMapReadyCallback { googleMap ->
        gMap = googleMap

        // Ponerle los controles de ui al mapa
        gMap.uiSettings.isZoomControlsEnabled = true
        gMap.uiSettings.isMapToolbarEnabled = true
        gMap.uiSettings.isCompassEnabled = true

        //Activar gestos
        gMap.uiSettings.isScrollGesturesEnabled = true

        // Poner un marcador en el inicio
        marcador = gMap.addMarker(MarkerOptions().position(inicio).title("Inicio").icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))!!

        //Mover la camara al inicio con un zoom de 15f guardado en la variable zoomLevel
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inicio, zoomLevel))

        //Agregar el punto de inicio a la lista de marcadores
        marcadores.add(inicio)

        gMap.setOnMapLongClickListener { latLng -> addPoint(latLng) }
    }

    //Funcion para agregar marcador al mapa con setOnMapLongClickListener
    private fun addPoint(latLng: LatLng) {
        gMap.clear()

        gMap.addMarker(MarkerOptions().position(inicio).title("Inicio").icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))

        gMap.addMarker(MarkerOptions().position(latLng).title("Fin").icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)))

        //Agregar marcador a la lista de marcadores
        marcadores.add(latLng)

        //Pintar linea desde el inicio hasta el punto seleccionado
        val pattern: List<PatternItem> = listOf(
            Dot(), Gap(10f), Dash(30f), Gap(10f)
        )

        //Calcular la distancia total entre puntos
        val results = calculateTotalDistance()

        //Mostrar la distancia total en el textview
        val mainActivity = activity as MainActivity
        val distancia = getString(R.string.distancia, results)
        mainActivity.textoDistancia.text = distancia


        gMap.addPolyline(
            com.google.android.gms.maps.model.PolylineOptions()
                .addAll(marcadores) // Utilizar todos los marcadores para dibujar la linea
                .width(20f)
                .color(Color.GRAY)
                .pattern(pattern)
        )
    }

    //Funcion para calcular la distancia total entre los marcadores
    private fun calculateTotalDistance(): Float {
        var totalDistance = 0f
        for (i in 0 until marcadores.size - 1) {
            val results = FloatArray(1)
            val start = marcadores[i]
            val end = marcadores[i + 1]
            Location.distanceBetween(start.latitude, start.longitude, end.latitude, end.longitude, results)
            totalDistance += results[0]
        }
        return totalDistance
    }


    //Funcion para borrar los marcadores y lineas cuando se le da click al boton borrar
    fun clearMap(){
        gMap.clear()
        marcador = gMap.addMarker(MarkerOptions().position(inicio).title("Inicio").icon(
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))!!

        marcadores.clear()
        marcadores.add(inicio)

        val mainActivity = activity as MainActivity
        mainActivity.textoDistancia.text = "Distancia: 0 mts"
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
    }
}