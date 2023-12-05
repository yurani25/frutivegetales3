package com.example.navigationdrawerkotlin.data

import com.example.navigationdrawerkotlin.model.ProductSend
import com.example.navigationdrawerkotlin.model.Productos
import com.example.navigationdrawerkotlin.reponse.LoginResponse
import com.example.navigationdrawerkotlin.reponse.ProductResponse
import com.example.navigationdrawerkotlin.reponse.UserProfile
import com.example.navigationdrawerkotlin.reponse.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("api/register")
    fun register(
        @Body userData: HashMap<String, String>
    ): Call<UserResponse>
@POST("api/logins")
fun login(
    @Body credentials: HashMap<String, String>
): Call<LoginResponse>

    @POST("api/users_update/{id}")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Path("id") userId: Int,
        @Body userProfile: UserProfile
    ): Call<UserResponse>


    @GET("api/getUser/{id}")
    fun getUserProfile(
        @Header("Authorization") token: String,
        @Path("id") userId: Int
    ): Call<UserResponse>

    @GET("api/productos")
    suspend fun obtenerProducto(): Response<List<Productos>>


    @POST("api/productos_store")
    fun crearProducto(@Body producto: ProductSend): Call<ProductResponse>

    @DELETE("api/productos_destroy/{producto}")
    suspend fun eliminarProducto(@Path("producto") producto: String): Response<Unit>

    @POST("api/productos_update/{producto}")
    fun updateProducto(@Path("producto") id: String, @Body producto: ProductSend): Call<ProductResponse>


}