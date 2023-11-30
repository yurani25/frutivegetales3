package com.example.navigationdrawerkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ListaproductosFragment : Fragment() {
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_listaproductos, container, false)

        // Referencia a la base de datos y al nodo de productos (ajusta según tu estructura)
        databaseReference = FirebaseDatabase.getInstance().reference.child("productos")

        // Obtener la lista de productos y mostrarla en el fragmento
        obtenerListaDeProductos(rootView)

        return rootView
    }

    private fun obtenerListaDeProductos(rootView: View) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Limpiar cualquier vista previa de productos en tu diseño
                val container = rootView.findViewById<ViewGroup>(R.id.productContainer)
                container.removeAllViews()

                // Iterar sobre los nodos de productos
                for (productSnapshot in dataSnapshot.children) {
                    // Obtener datos del producto
                    val nombre = productSnapshot.child("nombre").getValue(String::class.java)
                    val precio = productSnapshot.child("precio").getValue(Double::class.java)
                    val descripcion =
                        productSnapshot.child("descripcion").getValue(String::class.java)
                    val imageUrl = productSnapshot.child("imageUrl").getValue(String::class.java)

                    // Crear vistas y mostrar datos (esto es un ejemplo, ajusta según tu diseño)
                    val productImageView = ImageView(requireContext())
                    // Cargar la imagen utilizando Picasso o Glide (asegúrate de agregar las dependencias)
                    Picasso.get().load(imageUrl).into(productImageView)

                    val productNameTextView = TextView(requireContext())
                    productNameTextView.text = "Nombre: $nombre"

                    val productPriceTextView = TextView(requireContext())
                    productPriceTextView.text = "Precio: $precio"

                    val productDescriptionTextView = TextView(requireContext())
                    productDescriptionTextView.text = "Descripción: $descripcion"

                    // Agregar las vistas al diseño
                    container.addView(productImageView)
                    container.addView(productNameTextView)
                    container.addView(productPriceTextView)
                    container.addView(productDescriptionTextView)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar el error si es necesario
            }
        })
    }
}