package com.example.navigationdrawerkotlin.data

import com.example.navigationdrawerkotlin.reponse.LoginResponse
import com.example.navigationdrawerkotlin.reponse.UserProfile
import com.example.navigationdrawerkotlin.reponse.UserResponse
import retrofit2.Call
import retrofit2.http.Body
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


}