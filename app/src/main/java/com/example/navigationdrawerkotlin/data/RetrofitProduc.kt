package com.example.navigationdrawerkotlin.data

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProduc {

    private const val BASE_URL = "https://frutivegetalesapi-production-245a.up.railway.app/"

    val instance: ProductApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Log para verificar la URL configurada
        Log.d("Retrofit", "https://frutivegetalesapi-production-245a.up.railway.app/: $BASE_URL")

        retrofit.create(ProductApi::class.java)
    }

}