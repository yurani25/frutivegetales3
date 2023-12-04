package com.example.navigationdrawerkotlin.reponse


data class UserResponse(
    val message: String,
    val user: User // Modelo de datos para el usuario
)

data class LoginResponse(
    val message: String,
    val token: String,
    val user: User // Modelo de datos para el usuario
)
data class UserProfile(
    val nombres: String,
    val apellidos: String,
    val edad: Int,
    val telefono: String,
    val profile_picture: String?
)
data class User(
    val id: Int,
    val nombres: String,
    val apellidos: String,
    val edad: Int,
    val telefono: String,
    val email: String,
    val password: String,
    val profile_picture: String?,
    )

data class Producto(
    val nombres: String,
    val tiempo_reclamo: String,
    val imagen: String,
    val precio: Int,
    val descripcion: String,
    val user_id: Int
)
