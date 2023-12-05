package com.example.navigationdrawerkotlin.model

import com.google.gson.annotations.SerializedName

class ProductSend(

    @SerializedName("nombres")
    val nombres: String,
    @SerializedName("tiempo_reclamo")
    val tiempo_reclamo: String,
    @SerializedName("precio")
    val precio: Int,
    @SerializedName("descripcion")
    val descripcion: String,
    @SerializedName("user_id")
    val user_id: Int
)


