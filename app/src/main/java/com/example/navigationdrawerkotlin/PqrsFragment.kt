package com.example.navigationdrawerkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PqrsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
            // Crea una instancia del fragmento cancelar_comprasFragment
            val fragmentCancelarCompras = cancelar_comprasFragment()

            // Reemplaza con el nombre correcto de tu contenedor de fragmentos (R.id.fragment_container)
            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, fragmentCancelarCompras)
                ?.addToBackStack(null)
                ?.commit()
        }


        return view
    }
}
