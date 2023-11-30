package com.example.navigationdrawerkotlin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class editproductoFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var productReference: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editproducto, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        val productId = arguments?.getString("productId")

        if (productId != null) {
            productReference = database.reference.child("productos").child(productId)
            loadProductData(view)
        }

        val saveButton = view.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            val nameEditText = view.findViewById<TextInputEditText>(R.id.etProductName)
            val priceEditText = view.findViewById<TextInputEditText>(R.id.etProductPrice)
            val descriptionEditText = view.findViewById<TextInputEditText>(R.id.etProductDescription)

            val productName = nameEditText.text.toString().trim()
            val productPrice = priceEditText.text.toString().trim()
            val productDescription = descriptionEditText.text.toString().trim()

            if (productName.isNotEmpty() && productPrice.isNotEmpty() && productDescription.isNotEmpty()) {
                editProduct(productName, productPrice, productDescription)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Ingrese todos los campos obligatorios",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Agregar lógica para seleccionar una imagen
        val selectImageButton = view.findViewById<Button>(R.id.selectImageButton)
        selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        return view
    }

    // Método para manejar el resultado de seleccionar una imagen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            Log.d("ImageUri", "Selected Image Uri: $selectedImageUri")

            // Mostrar la imagen seleccionada en el ImageView
            val selectedImageView = view?.findViewById<ImageView>(R.id.selectedImageView)
            selectedImageView?.visibility = View.VISIBLE
            Picasso.get().load(selectedImageUri).into(selectedImageView)

            // También puedes ocultar el ImageView principal
            view?.findViewById<ImageView>(R.id.productImageView)?.visibility = View.GONE
        }
    }

    private fun loadProductData(view: View) {
        productReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val nameEditText = view.findViewById<TextInputEditText>(R.id.etProductName)
                    val priceEditText = view.findViewById<TextInputEditText>(R.id.etProductPrice)
                    val descriptionEditText = view.findViewById<TextInputEditText>(R.id.etProductDescription)
                    val productImageView = view.findViewById<ImageView>(R.id.productImageView) // Agrega el ImageView correspondiente

                    val productName = snapshot.child("nombre").getValue(String::class.java)
                    val productPrice = snapshot.child("precio").getValue(Double::class.java)
                    val productDescription = snapshot.child("descripcion").getValue(String::class.java)
                    val imageUrl = snapshot.child("imageUrl").getValue(String::class.java)

                    nameEditText.setText(productName)
                    priceEditText.setText(productPrice?.toString())
                    descriptionEditText.setText(productDescription)

                    // Cargar y mostrar la imagen usando Picasso
                    if (!imageUrl.isNullOrEmpty()) {
                        Picasso.get().load(imageUrl).into(productImageView)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error si es necesario
            }
        })
    }

    private fun editProduct(productName: String, productPrice: String, productDescription: String) {
        val productId = arguments?.getString("productId")
        if (productId != null) {
            val productRef = database.reference.child("productos").child(productId)

            val productData = HashMap<String, Any>()
            productData["nombre"] = productName
            productData["precio"] = productPrice.toDouble()
            productData["descripcion"] = productDescription

            // Agregar la lógica para cargar la imagen solo si se selecciona una nueva imagen
            if (selectedImageUri != null) {
                val imageRef = storageReference.child("images/$productId.jpg")
                imageRef.putFile(selectedImageUri!!)
                    .addOnSuccessListener { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                            productData["imageUrl"] = uri.toString()
                            updateProductData(productRef, productData)
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(
                            requireContext(),
                            "Error al cargar la imagen: ${exception.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                // Si no se selecciona una nueva imagen, solo actualizar los datos existentes
                updateProductData(productRef, productData)
            }
        }
    }

    private fun updateProductData(productRef: DatabaseReference, productData: HashMap<String, Any>) {
        productRef.updateChildren(productData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    requireContext(),
                    "Producto actualizado exitosamente",
                    Toast.LENGTH_SHORT
                ).show()

                // Enviar un resultado de vuelta a tu fragmento anterior (si es necesario)
                // Puedes utilizar un intent para pasar datos de vuelta, similar a como lo haces con setResult para Activities

                // También puedes cerrar el fragmento actual
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Error al actualizar producto: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}