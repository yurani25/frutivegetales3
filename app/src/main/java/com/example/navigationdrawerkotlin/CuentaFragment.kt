import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import com.example.navigationdrawerkotlin.R

class CuentaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_cuenta, container, false)

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
}
