package com.example.navigationdrawerkotlin.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationdrawerkotlin.data.ProductApi
import com.example.navigationdrawerkotlin.reponse.Producto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel(private val productApi: ProductApi) : ViewModel() {

    val productList = MutableLiveData<List<Producto>>()

    fun getProducts() {
        productApi.getProducts().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    productList.value = response.body()
                } else {
                    Log.e("API Call", "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                // Manejar error de conexión o respuesta no exitosa
                Log.e("API Call", "Error: ${t.message}")
            }
        })
    }
}