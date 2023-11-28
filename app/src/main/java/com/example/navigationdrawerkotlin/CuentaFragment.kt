import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.navigationdrawerkotlin.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class CuentaFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var userReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_cuenta, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val userId = auth.currentUser?.uid
        if (userId != null) {
            userReference = database.reference.child("users").child(userId)

            loadUserData(rootView)
        }

        // Agregar OnClickListener al botón "Editar Perfil"
        val editProfileButton = rootView.findViewById<Button>(R.id.editProfileButton)
        editProfileButton.setOnClickListener {
            // Crear y reemplazar el fragmento de edición de perfil cuando se haga clic
            val editProfileFragment = EditProfileFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragment_container, editProfileFragment)
            transaction.addToBackStack(null) // Opcional, para agregar a la pila de retroceso
            transaction.commit()
        }

        return rootView
    }

    private fun loadUserData(rootView: View) {
        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val nameTextView = rootView.findViewById<TextView>(R.id.nameTextView)
                    val apellidoTextView = rootView.findViewById<TextView>(R.id.apellidoTextView)
                    val edadTextView = rootView.findViewById<TextView>(R.id.edadTextView)
                    val emailTextView = rootView.findViewById<TextView>(R.id.emailTextView)
                    val telefonoTextView = rootView.findViewById<TextView>(R.id.telefonoTextView)

                    val name = snapshot.child("name").getValue(String::class.java)
                    val apellido = snapshot.child("apellido").getValue(String::class.java)
                    val edad = snapshot.child("edad").getValue(String::class.java)
                    val email = snapshot.child("email").getValue(String::class.java)
                    val telefono = snapshot.child("telefono").getValue(String::class.java)

                    nameTextView.text = "Nombre: $name"
                    apellidoTextView.text = "Apellido: $apellido"
                    edadTextView.text = "Edad: $edad"
                    emailTextView.text = "email: $email" // Asegúrate de que el valor de email no sea null
                    telefonoTextView.text = "Telefono: $telefono"

                    val imageUrl = snapshot.child("imageUrl").getValue(String::class.java)

                    // Cargar la imagen utilizando Picasso (asegúrate de agregar la dependencia en el archivo build.gradle)
                    val profileImageView = rootView.findViewById<ImageView>(R.id.profileImageView)

                    // Si imageUrl no es nulo o vacío, carga la imagen
                    if (!imageUrl.isNullOrEmpty()) {
                        Picasso.get().load(imageUrl).into(profileImageView)
                    } else {
                        // Si la URL de la imagen está vacía, puedes establecer una imagen predeterminada
                        profileImageView.setImageResource(R.drawable.usuarioperfil)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error si es necesario
            }
        })
    }
}
