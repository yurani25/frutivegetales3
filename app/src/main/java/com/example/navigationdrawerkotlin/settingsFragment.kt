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
 * Use the [settingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class settingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Obtén la referencia al LinearLayout de frutas
        val notificacionesLayout = view.findViewById<LinearLayout>(R.id.notificacionesLayout)

        // Configura el OnClickListener para abrir el fragmento de frutas
        notificacionesLayout.setOnClickListener {
            val notificacionesFragment = NotificacionesFragment() // Reemplaza con el nombre correcto de tu fragmento de frutas
            val fragmentManager: FragmentManager? = fragmentManager
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, notificacionesFragment)
                ?.addToBackStack(null)
                ?.commit()
        }

        return view
    }
}