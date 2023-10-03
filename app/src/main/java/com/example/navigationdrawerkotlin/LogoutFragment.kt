package com.example.navigationdrawerkotlin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.navigationdrawerkotlin.MainActivity
import com.example.navigationdrawerkotlin.R


class LogoutFragment : Fragment()

{
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_logout, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            // Aquí configura el clic del ícono de logout en tu diseño
            val logoutIcon = view.findViewById<View>(R.id.logoutIcon)
            logoutIcon.setOnClickListener {
                // Agrega el código para iniciar MainActivity aquí
                val intent = Intent(requireActivity(), PrincipalActivity::class.java)
                startActivity(intent)
                requireActivity().finish() // Cierra la actividad actual (el fragmento de Logout)
            }
        }
    }

