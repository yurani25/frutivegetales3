package com.example.navigationdrawerkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager

class PqrsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pqrs, container, false)

        val cancelarComprasLayout = view.findViewById<LinearLayout>(R.id.cancelarComprasLayout)
        val preguntasComprasLayout = view.findViewById<LinearLayout>(R.id.preguntasComprasLayout)
        val devolucionesLayout = view.findViewById<LinearLayout>(R.id.devolucionesLayout)
        val reembolsosLayout = view.findViewById<LinearLayout>(R.id.reembolsosLayout)
        val gestionarVentasLayout = view.findViewById<LinearLayout>(R.id.gestionarVentasLayout)

        cancelarComprasLayout.setOnClickListener {
            val fragmentCancelarCompras = cancelar_comprasFragment()
            replaceFragment(fragmentCancelarCompras)
        }

        preguntasComprasLayout.setOnClickListener {
            val fragmentpreguntasCompras = preguntasCompras()
            replaceFragment(fragmentpreguntasCompras)
        }

        devolucionesLayout.setOnClickListener {
            val fragmentdevoluciones = DevolucionesFragment()
            replaceFragment(fragmentdevoluciones)
        }

        reembolsosLayout.setOnClickListener {
            val fragmentreembolsos = ReembolsosFragment()
            replaceFragment(fragmentreembolsos)
        }

        gestionarVentasLayout.setOnClickListener {
            val fragmentGestionarVentas = GestionarVentasFragment()
            replaceFragment(fragmentGestionarVentas)
        }

        return view
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager? = fragmentManager
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }
}
