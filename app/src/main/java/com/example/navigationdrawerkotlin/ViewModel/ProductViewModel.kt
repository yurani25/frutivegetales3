package com.example.navigationdrawerkotlin.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.navigationdrawerkotlin.data.RetrofitUsers
import com.example.navigationdrawerkotlin.data.RetrofitUsers.instance
import com.example.navigationdrawerkotlin.model.ProductSend
import com.example.navigationdrawerkotlin.model.Productos
import com.example.navigationdrawerkotlin.reponse.ProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel(application: Application) : AndroidViewModel(application) {


    private var _listaProductos = MutableLiveData<List<Productos>>()

    val listaProductos: LiveData<List<Productos>> get() = _listaProductos

    fun obtenerProductos() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitUsers.instance.obtenerProducto()
            withContext(Dispatchers.Main) {
                _listaProductos.value = response.body()
            }
        }
    }
    fun subirProducto(product: ProductSend, onResponseCallback: (ProductResponse?) -> Unit) {
        viewModelScope.launch {
            val call = instance.crearProducto(product)
            call.enqueue(object : Callback<ProductResponse> {
                override fun onResponse(
                    call: Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    if (response.isSuccessful) {
                        val respuesta = response.body()
                        onResponseCallback(respuesta)
                    } else {
                        onResponseCallback(null)
                    }
                }

                override fun onFailure(call: Call<ProductResponse>, t: Throwable) {

                    onResponseCallback(null)
                }
            })
        }
    }


    fun eliminarProducto (producto: String, onFailureCallback: () -> Unit){
        viewModelScope.launch {
            try {
                val response = instance.eliminarProducto(producto)
                if (!response.isSuccessful) {
                    // Manejar el error si la respuesta no fue exitosa
                    onFailureCallback()
                }
            } catch (e: Exception) {
                // Manejar excepciones (por ejemplo, problemas de red)
                onFailureCallback()
            }
        }
    }

    fun actualizarProducto (id: String, producto: ProductSend, onResponseCallback: (ProductResponse?) -> Unit ){

        instance.updateProducto(id, producto).enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {

                if (response.isSuccessful){
                    onResponseCallback(response.body())
                }else{

                    onResponseCallback(null)
                    try {
                        val errorResponse = response.errorBody()?.string()
                        val json = JSONObject(errorResponse)
                        val errorMessage = json.getString("message")
                        Log.e("editarproducto","mensaje de eror JSON: $errorMessage")
                    }catch (e: JSONException){
                        Log.e("errro", "error en procesar JSON:  ${e.message}")
                    }

                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                onResponseCallback(null)
            }
        }
            )
        }

    }



