package com.example.navigationdrawerkotlin



import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView

class PreguntasComprasFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_preguntas_compras, container, false)

        // Simulación de datos de preguntas frecuentes
        val preguntas = arrayOf(
            "¿Cómo realizo una compra?",
            "¿Puedo cancelar una compra ya realizada?",
            "¿Cómo puedo realizar un seguimiento de mi pedido?",
            // Agrega más preguntas según sea necesario
        )

        // Configuración del adaptador para la lista de preguntas
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, preguntas)
        val listaPreguntas = view.findViewById<ListView>(R.id.listaPreguntas)
        listaPreguntas.adapter = adapter

        // Agrega un listener para manejar clics en las preguntas (opcional)
        listaPreguntas.setOnItemClickListener { _, _, position, _ ->
            // Aquí puedes implementar lógica para manejar el clic en una pregunta específica
        }

        return view
    }
}
