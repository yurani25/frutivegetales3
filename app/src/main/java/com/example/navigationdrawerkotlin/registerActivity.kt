package com.example.navigationdrawerkotlin

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class registerActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val emailEditText = findViewById<TextInputEditText>(R.id.etEmail)
            val passwordEditText = findViewById<TextInputEditText>(R.id.etPassword)
            val confirmPasswordEditText = findViewById<TextInputEditText>(R.id.etconfirPassword)
            val nameEditText = findViewById<TextInputEditText>(R.id.etName)
            val apellidoEditText = findViewById<TextInputEditText>(R.id.apellido)
            val edadEditText = findViewById<TextInputEditText>(R.id.edad)
            val etPhoneEditText = findViewById<TextInputEditText>(R.id.etPhone)


            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()
            val name = nameEditText.text.toString().trim()
            val apellido = apellidoEditText.text.toString().trim()
            val edad = edadEditText.text.toString().trim()
            val etPhone = etPhoneEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && name.isNotEmpty() && apellido.isNotEmpty() && edad.isNotEmpty()  && etPhone.isNotEmpty()){
                registerUser(email, password, confirmPassword, name, apellido, edad , etPhone )
            } else {
                Toast.makeText(this, "Ingrese todos los campos obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(email: String, password: String, confirmPassword: String, name: String, apellido: String, edad: String , telefono:String) {
        if (password != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Usuario registrado exitosamente
                    val user: FirebaseUser? = auth.currentUser

                    // Obtén la referencia a la imagen predeterminada en el directorio "drawable"
                    val drawableResourceId = resources.getIdentifier("default_profile_image", "drawable", packageName)

                    // Convierte el recurso drawable a URL
                    val defaultImageUrl = Uri.parse("android.resource://$packageName/$drawableResourceId").toString()


                    // Aquí puedes agregar la lógica para almacenar datos en Realtime Database
                    val userId = user?.uid
                    if (userId != null) {
                        val databaseReference = FirebaseDatabase.getInstance().reference.child("users").child(userId)

                        val userData = HashMap<String, Any>()
                        userData["name"] = name
                        userData["apellido"] = apellido
                        userData["edad"] = edad
                        userData["telefono"] = telefono
                        userData["imageUrl"] = defaultImageUrl // Establece la URL de imagen predeterminada

                        // Guarda los datos del usuario en la base de datos
                        databaseReference.setValue(userData)
                            .addOnCompleteListener { databaseTask ->
                                if (databaseTask.isSuccessful) {
                                    Toast.makeText(this, "Registro exitoso: ${user.email}", Toast.LENGTH_SHORT).show()

                                    // Redirige al usuario a la actividad de inicio de sesión
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish() // Esto evita que el usuario pueda volver atrás presionando el botón "Atrás"
                                } else {
                                    Toast.makeText(this, "Error al almacenar datos en la base de datos", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                } else {
                    Toast.makeText(this, "Error en el registro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

