package com.example.navigationdrawerkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager

class settingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Obtén las referencias a los LinearLayouts
        val notificacionesLayout = view.findViewById<LinearLayout>(R.id.notificacionesLayout)
        val seguridadLayout = view.findViewById<LinearLayout>(R.id.seguridadLayout)

        // Configura el OnClickListener para abrir el fragmento de notificaciones
        notificacionesLayout.setOnClickListener {
            val notificacionesFragment = NotificacionesFragment() // Reemplaza con el nombre correcto de tu fragmento de notificaciones
            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, notificacionesFragment)
                ?.addToBackStack(null)
                ?.commit()
        }

        // Configura el OnClickListener para abrir el fragmento de seguridad
        seguridadLayout.setOnClickListener {
            val seguridadFragment = SeguridadFragment() // Reemplaza con el nombre correcto de tu fragmento de seguridad
            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, seguridadFragment)
                ?.addToBackStack(null)
                ?.commit()
        }

        return view
    }
}
