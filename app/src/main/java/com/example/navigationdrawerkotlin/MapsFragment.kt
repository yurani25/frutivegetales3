package com.example.navigationdrawerkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener
import com.google.android.gms.maps.SupportMapFragment

class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMapClickListener, OnMapLongClickListener {
    private lateinit var txtLatitud: EditText
    private lateinit var txtLongitud: EditText
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps2, container, false)
        txtLatitud = view.findViewById(R.id.txtLatitud)
        txtLongitud = view.findViewById(R.id.txtLongitud)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener(this)
        mMap.setOnMapLongClickListener(this)

        // Coordenadas de Popayán, Cauca
        val popayan = LatLng(2.4442, -76.6141)

        mMap.addMarker(MarkerOptions().position(popayan).title("Popayán, Cauca"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(popayan))
    }

    override fun onMapClick(latLng: LatLng) {
        txtLatitud.setText(latLng.latitude.toString())
        txtLongitud.setText(latLng.longitude.toString())
        mMap.clear()
        val popayan = LatLng(latLng.latitude, latLng.longitude)
        mMap.addMarker(MarkerOptions().position(popayan).title(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(popayan))
    }

    override fun onMapLongClick(latLng: LatLng) {
        txtLatitud.setText(latLng.latitude.toString())
        txtLongitud.setText(latLng.longitude.toString())
        mMap.clear()
        val popayan = LatLng(latLng.latitude, latLng.longitude)
        mMap.addMarker(MarkerOptions().position(popayan).title(""))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(popayan))
    }
}
