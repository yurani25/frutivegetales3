import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.navigationdrawerkotlin.R
import com.example.navigationdrawerkotlin.data.RetrofitUsers
import com.example.navigationdrawerkotlin.reponse.User
import com.example.navigationdrawerkotlin.reponse.UserProfile
import com.example.navigationdrawerkotlin.reponse.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CuentaFragment : Fragment() {

    private lateinit var etName: EditText
    private lateinit var etApellido: EditText
    private lateinit var etEdad: EditText
    private lateinit var etPhone: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cuenta, container, false)

        // Inicializar vistas
        etName = view.findViewById(R.id.etName)
        etApellido = view.findViewById(R.id.apellido)
        etEdad = view.findViewById(R.id.edad)
        etPhone = view.findViewById(R.id.etPhone)
        saveButton = view.findViewById(R.id.saveButton)

        // Configurar clic en el botón de guardar cambios
        saveButton.setOnClickListener {
            updateProfile()
        }

        // Cargar información del usuario actual
        loadUserData()

        return view
    }

    private fun loadUserData() {
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val userId: Int = sharedPreferences.getInt("user_id", -1)
        val token: String? = sharedPreferences.getString("auth_token", null)

        if (userId != -1 && token != null) {
            RetrofitUsers.instance.getUserProfile("Bearer $token", userId)
                .enqueue(object : Callback<UserResponse> {
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        if (response.isSuccessful) {
                            val userResponse: UserResponse? = response.body()

                            // Verificar si userResponse o user son nulos
                            val user: User? = userResponse?.user
                            if (user != null) {
                                // Actualizar los campos de la interfaz de usuario con la información del usuario
                                etName.setText(user.nombres)
                                etApellido.setText(user.apellidos)
                                etEdad.setText(user.edad.toString())
                                etPhone.setText(user.telefono)
                            } else {
                                // Manejar el caso en que la respuesta no contiene la información del usuario
                                // Puedes mostrar un mensaje de error o tomar alguna acción específica
                            }
                        } else {
                            // Manejar la respuesta fallida, por ejemplo, mostrar un mensaje de error
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        // Manejar errores de la llamada, por ejemplo, mostrar un mensaje de error
                    }
                })
        }
    }

    private fun updateProfile() {
        // Obtén el ID del usuario y el token almacenados en SharedPreferences
        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val userId: Int = sharedPreferences.getInt("user_id", -1)
        val token: String? = sharedPreferences.getString("auth_token", null)

        if (userId == -1 || token == null) {
            // Manejar el caso en que el ID del usuario o el token no estén disponibles
            return
        }

        // Obtén instancias de los campos de entrada
        val nombres = etName.text.toString()
        val apellidos = etApellido.text.toString()
        val edad = etEdad.text.toString().toInt()
        val telefono = etPhone.text.toString()

        // Crear objeto UserProfile con la información actualizada
        val userProfile = UserProfile(nombres, apellidos, edad, telefono, null)
        RetrofitUsers.instance.updateProfile("Bearer $token", userId, userProfile)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        // Manejar la respuesta exitosa, por ejemplo, mostrar un mensaje al usuario
                        // También puedes llamar a loadUserData() para actualizar la interfaz con la información actualizada
                        loadUserData()
                    } else {
                        // Manejar la respuesta fallida, por ejemplo, mostrar un mensaje de error
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    // Manejar errores de la llamada, por ejemplo, mostrar un mensaje de error
                }
            })
    }
}

