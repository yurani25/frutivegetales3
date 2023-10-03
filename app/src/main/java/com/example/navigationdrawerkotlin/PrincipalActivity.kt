package com.example.navigationdrawerkotlin


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PrincipalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        val registerButton = findViewById<Button>(R.id.registerMainButton)
        val loginButton = findViewById<Button>(R.id.loginMainButton)

        registerButton.setOnClickListener {
            val intent = Intent(this, registerActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }



}