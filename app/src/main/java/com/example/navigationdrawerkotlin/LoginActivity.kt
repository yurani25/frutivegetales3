package com.example.navigationdrawerkotlin


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.navigationdrawerkotlin.data.RetrofitUsers
import com.example.navigationdrawerkotlin.reponse.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        etEmail = findViewById(R.id.etLoginEmail)
        etPassword = findViewById(R.id.etLoginPassword)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            handleLogin()
        }
    }

    private fun handleLogin() {
        // Obtener los datos de correo electrónico y contraseña
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        // Validar si los campos están vacíos
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear un HashMap para enviar al servicio web
        val credentials = HashMap<String, String>()
        credentials["email"] = email
        credentials["password"] = password

        // Llamar al método de inicio de sesión en Retrofit
        val call: Call<LoginResponse> = RetrofitUsers.instance.login(credentials)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    // El inicio de sesión fue exitoso
                    val loginResponse: LoginResponse? = response.body()

                    // Aquí obtienes el token del LoginResponse
                    val token: String? = loginResponse?.token

                    // Guardar el token en SharedPreferences (ejemplo)
                    val preferences: SharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = preferences.edit()
                    editor.putString("auth_token", token)
                    editor.apply()


                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Cerrar la actividad actual

                } else {
                    // El inicio de sesión falló
                    Toast.makeText(this@LoginActivity, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Manejar fallos en la llamada
                Toast.makeText(this@LoginActivity, "Error de red: " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}