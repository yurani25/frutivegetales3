package com.example.navigationdrawerkotlin
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.navigationdrawerkotlin.data.RetrofitUsers
import com.example.navigationdrawerkotlin.reponse.UserResponse
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class registerActivity : AppCompatActivity() {

    private lateinit var etName: TextInputEditText
    private lateinit var etApellido: TextInputEditText
    private lateinit var etEdad: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPhone: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etName = findViewById(R.id.etName)
        etApellido = findViewById(R.id.apellido)
        etEdad = findViewById(R.id.edad)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etconfirPassword)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            val name = etName.text.toString()
            val apellido = etApellido.text.toString()
            val edadText = etEdad.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            // Verificar que los campos no estén vacíos
            if (name.isEmpty() || apellido.isEmpty() || edadText.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Todos los campos deben ser completados", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val edad = edadText.toInt()

            // Verificar que las contraseñas coincidan
            if (password != confirmPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userData = HashMap<String, String>()
            userData["nombres"] = name
            userData["apellidos"] = apellido
            userData["edad"] = edad.toString()
            userData["email"] = email
            userData["telefono"] = phone
            userData["password"] = password

            // Llamar a la API de registro
            val call = RetrofitUsers.instance.register(userData)
            call.enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        val userResponse = response.body()
                        Toast.makeText(this@registerActivity, userResponse?.message, Toast.LENGTH_SHORT).show()
                        // Redirigir a la LoginActivity
                        val intent = Intent(this@registerActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()  // Esto ayuda a cerrar la actividad actual para que el usuario no pueda volver atrás.
                    } else {
                        Toast.makeText(this@registerActivity, "Error en el registro", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(this@registerActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}

