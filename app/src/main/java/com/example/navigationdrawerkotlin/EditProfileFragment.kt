import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.navigationdrawerkotlin.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class EditProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userReference: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        val userId = auth.currentUser?.uid

        if (userId != null) {
            userReference = database.reference.child("users").child(userId)
            loadUserData(view)
        }

        val saveButton = view.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            val nameEditText = view.findViewById<TextInputEditText>(R.id.etName)
            val apellidoEditText = view.findViewById<TextInputEditText>(R.id.apellido)
            val edadEditText = view.findViewById<TextInputEditText>(R.id.edad)
            val telefonoText = view.findViewById<TextInputEditText>(R.id.etPhone)

            val name = nameEditText.text.toString().trim()
            val apellido = apellidoEditText.text.toString().trim()
            val edad = edadEditText.text.toString().trim()
            val telefono = telefonoText.text.toString().trim()

            if (name.isNotEmpty() && apellido.isNotEmpty() && edad.isNotEmpty()) {
                editUserProfile(name, apellido, edad, telefono)
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
            view?.findViewById<ImageView>(R.id.profileImageView)?.visibility = View.GONE
        }
    }

    private fun loadUserData(view: View) {
        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val nameEditText = view.findViewById<TextInputEditText>(R.id.etName)
                    val apellidoEditText = view.findViewById<TextInputEditText>(R.id.apellido)
                    val edadEditText = view.findViewById<TextInputEditText>(R.id.edad)
                    val telefonoText = view.findViewById<TextInputEditText>(R.id.etPhone)
                    val profileImageView = view.findViewById<ImageView>(R.id.profileImageView) // Agrega el ImageView correspondiente

                    val name = snapshot.child("name").getValue(String::class.java)
                    val apellido = snapshot.child("apellido").getValue(String::class.java)
                    val edad = snapshot.child("edad").getValue(String::class.java)
                    val telefono = snapshot.child("telefono").getValue(String::class.java)
                    val imageUrl = snapshot.child("imageUrl").getValue(String::class.java)

                    nameEditText.setText(name)
                    apellidoEditText.setText(apellido)
                    edadEditText.setText(edad)
                    telefonoText.setText(telefono)

                    // Cargar y mostrar la imagen usando Picasso
                    if (!imageUrl.isNullOrEmpty()) {
                        Picasso.get().load(imageUrl).into(profileImageView)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error si es necesario
            }
        })
    }

    private fun editUserProfile(name: String, apellido: String, edad: String, telefono: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val userRef = database.reference.child("users").child(userId)

            val userData = HashMap<String, Any>()
            userData["name"] = name
            userData["apellido"] = apellido
            userData["edad"] = edad
            userData["telefono"] = telefono

            // Agregar la lógica para cargar la imagen solo si se selecciona una nueva imagen
            if (selectedImageUri != null) {
                val imageRef = storageReference.child("images/$userId.jpg")
                imageRef.putFile(selectedImageUri!!)
                    .addOnSuccessListener { taskSnapshot ->
                        taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                            userData["imageUrl"] = uri.toString()
                            updateUserData(userRef, userData)
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
                updateUserData(userRef, userData)
            }
        }
    }

    private fun updateUserData(userRef: DatabaseReference, userData: HashMap<String, Any>) {
        userRef.updateChildren(userData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    requireContext(),
                    "Perfil actualizado exitosamente",
                    Toast.LENGTH_SHORT
                ).show()

                // Enviar un resultado de vuelta a CuentaFragment
                val resultIntent = Intent()
                resultIntent.putExtra("profileUpdated", true)
                requireActivity().setResult(Activity.RESULT_OK, resultIntent)
                requireActivity().supportFragmentManager.popBackStack() // Regresar al fragmento anterior
            } else {
                Toast.makeText(
                    requireContext(),
                    "Error al actualizar perfil: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
