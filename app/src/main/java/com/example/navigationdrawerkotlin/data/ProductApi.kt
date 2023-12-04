package com.example.navigationdrawerkotlin.data

import com.example.navigationdrawerkotlin.reponse.Producto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProductApi {
    @GET("productos")
    fun getProducts(): Call<List<Producto>>
    @Multipart
    @POST("productos_store")
    fun createProduct(
        @Part("nombres") nombres: MultipartBody.Part,
        @Part("tiempo_reclamo") tiempoReclamo: MultipartBody.Part,
        @Part("precio") precio: MultipartBody.Part,
        @Part("descripcion") descripcion: MultipartBody.Part,
        @Part("user_id") userId: MultipartBody.Part,
        @Part imagen: MultipartBody.Part
    ): Call<Producto>

}