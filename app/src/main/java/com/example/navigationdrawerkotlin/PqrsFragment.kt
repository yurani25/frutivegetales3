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

        // Obtén la referencia al LinearLayout de cancelar compras
        val cancelarComprasLayout = view.findViewById<LinearLayout>(R.id.cancelarComprasLayout)

        // Configura el OnClickListener para abrir el fragmento de cancelar compras
        cancelarComprasLayout.setOnClickListener {
            val fragmentCancelarCompras = cancelar_comprasFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragmentCancelarCompras)
                .addToBackStack(null)
                .commit()
        }

        // Obtén la referencia al LinearLayout de preguntas sobre las compras
        val preguntasComprasLayout = view.findViewById<LinearLayout>(R.id.preguntasComprasLayout)

        // Configura el OnClickListener para abrir el fragmento de preguntas sobre las compras
        preguntasComprasLayout.setOnClickListener {
            val fragmentPreguntasCompras = PreguntasComprasFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragmentPreguntasCompras)
                .addToBackStack(null)
                .commit()
        }

        // Obtén la referencia al LinearLayout de devoluciones
        val devolucionesLayout = view.findViewById<LinearLayout>(R.id.devolucionesLayout)


        // Configura el OnClickListener para abrir el fragmento de devoluciones
        devolucionesLayout.setOnClickListener {
            val fragmentDevoluciones = DevolucionesFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragmentDevoluciones)
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}
