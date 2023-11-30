package com.example.navigationdrawerkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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

                // Verificar si hay productos antes de agregar el botón de edición
                var hayProductos = false

                // Después de obtener datos del producto en el bucle
                for (productSnapshot in dataSnapshot.children) {
                    // Obtener datos del producto
                    val nombre = productSnapshot.child("nombre").getValue(String::class.java)
                    val precio = productSnapshot.child("precio").getValue(Double::class.java)
                    val descripcion =
                        productSnapshot.child("descripcion").getValue(String::class.java)
                    val imageUrl = productSnapshot.child("imageUrl").getValue(String::class.java)

                    // Crear vistas y mostrar datos (esto es un ejemplo, ajusta según tu diseño)
                    val productImageView = ImageView(requireContext())
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

                    // Configurar el botón de eliminación
                    val deleteButton = ImageButton(requireContext())
                    deleteButton.setImageResource(R.drawable.baseline_delete_24)
                    deleteButton.contentDescription = "Eliminar Producto"
                    deleteButton.setOnClickListener {
                        // Llamar al método de eliminación con el ID del producto
                        val productId = productSnapshot.key
                        eliminarProducto(productId)
                    }

                    // Agregar el botón de eliminación al diseño
                    container.addView(deleteButton)

                    // Establecer que hay al menos un producto
                    hayProductos = true
                }

// Verificar si hay productos antes de agregar el botón de edición
                // Verificar si hay productos antes de agregar el botón de edición
                if (hayProductos) {
                    // Configurar el botón de edición fuera del bucle
                    for (productSnapshot in dataSnapshot.children) {
                        val editButton = ImageButton(requireContext())
                        editButton.setImageResource(R.drawable.baseline_edit_24)
                        editButton.contentDescription = "Editar Producto"

                        // Obtener el ID del producto específico
                        val productId = productSnapshot.key

                        editButton.setOnClickListener {
                            // Abrir el fragmento de edición y pasar el ID del producto como argumento
                            val fragmentManager = requireActivity().supportFragmentManager
                            val fragmentTransaction = fragmentManager.beginTransaction()

                            val editFragment = editproductoFragment()
                            val args = Bundle()
                            args.putString("productId", productId)
                            editFragment.arguments = args

                            fragmentTransaction.replace(R.id.fragment_container, editFragment)
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        }

                        // Agregar el botón de edición al diseño
                        container.addView(editButton)
                    }
                }
            }

                private fun eliminarProducto(productId: String?) {
                if (isAdded && productId != null) {
                    // Referencia al producto específico en la base de datos
                    val productoRef = databaseReference.child(productId)

                    // Eliminar el producto de la base de datos
                    productoRef.removeValue()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar el error si es necesario
            }
        })
    }
}