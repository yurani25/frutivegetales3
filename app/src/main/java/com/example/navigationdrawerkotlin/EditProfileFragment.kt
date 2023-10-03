import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.navigationdrawerkotlin.R

class EditProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        // Obtén referencias a los elementos de edición de perfil
        val usernameEditText = rootView.findViewById<EditText>(R.id.usernameEditText)
        val emailEditText = rootView.findViewById<EditText>(R.id.emailEditText)
        val direccionEditText = rootView.findViewById<EditText>(R.id.direccionEditText)
        val edadEditText = rootView.findViewById<EditText>(R.id.edadEditText)
        val saveChangesButton = rootView.findViewById<Button>(R.id.saveChangesButton)

        // Configura el OnClickListener del botón para guardar los cambios
        saveChangesButton.setOnClickListener {
            // Aquí puedes procesar y guardar los cambios en la información de perfil
            // Puedes obtener los valores de los campos de entrada de texto
            val newUsername = usernameEditText.text.toString()
            val newEmail = emailEditText.text.toString()
            val newDireccion = direccionEditText.text.toString()
            val newEdad = edadEditText.text.toString()

            // Puedes realizar la lógica para guardar los cambios en tu base de datos o donde sea necesario

            // Luego, puedes cerrar el fragmento de edición de perfil si es necesario
            fragmentManager?.popBackStack()
        }

        return rootView
    }
}
