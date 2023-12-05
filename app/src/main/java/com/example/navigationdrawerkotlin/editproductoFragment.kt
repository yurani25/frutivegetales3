package com.example.navigationdrawerkotlin


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.navigationdrawerkotlin.ViewModel.ProductViewModel
import com.example.navigationdrawerkotlin.model.ProductSend
import com.google.android.material.textfield.TextInputEditText

private lateinit var viewModel: ProductViewModel

class editproductoFragment : Fragment() {

    private lateinit var nombresProductNew: TextInputEditText
    private lateinit var descripcionProductNew: TextInputEditText
    private lateinit var tiempoProductNew: TextInputEditText
    private lateinit var precioProductNew: TextInputEditText
    private lateinit var useridProductNew: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editproducto, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        val nombres = arguments?.getString("nombres", "")
        val descripcion = arguments?.getString("descripcion", "")
        val tiempo_reclamo = arguments?.getString("tiempo_reclamo", "")
        val precio = arguments?.getString("precio", "")
        val user_id = arguments?.getString("user_id", "")
        val id = arguments?.getString("id", "")

        nombresProductNew = view.findViewById<TextInputEditText>(R.id.etProductName)
        descripcionProductNew = view.findViewById<TextInputEditText>(R.id.etProductDescription)
        tiempoProductNew = view.findViewById<TextInputEditText>(R.id.etProductreclamo)
        precioProductNew = view.findViewById<TextInputEditText>(R.id.etProductPrice)
        useridProductNew = view.findViewById<TextInputEditText>(R.id.etProductUser)

        // Verificar si los valores no son nulos
        if (nombres != null) {
            nombresProductNew.setText(nombres)
        }

        if (descripcion != null) {
            descripcionProductNew.setText(descripcion)
        }
        if (tiempo_reclamo != null) {
            tiempoProductNew.setText(tiempo_reclamo)
        }

        if (precio != null) {
            precioProductNew.setText(precio)
        }

        if (user_id != null) {
            useridProductNew.setText(user_id)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveButton = view.findViewById<Button>(R.id.guardarButton)
        saveButton.setOnClickListener {
            val id = arguments?.getString("id", "")
            if(id !=null){

                val nuevoProducto = ProductSend(
                    nombres = nombresProductNew.text.toString(),
                    descripcion = descripcionProductNew.text.toString(),
                    tiempo_reclamo = tiempoProductNew.text.toString(),
                    precio = precioProductNew.text.toString().toInt(),
                    user_id = useridProductNew.text.toString().toInt()
                )
                viewModel.actualizarProducto(id,nuevoProducto){response ->
                    if(response?.nombres != null){
                        Toast.makeText(requireContext(), "se actualizo correctamente", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), "Error al actualizar producto", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Log.e("editproducto", "ID es nulo")
            }
        }

            }
        }




