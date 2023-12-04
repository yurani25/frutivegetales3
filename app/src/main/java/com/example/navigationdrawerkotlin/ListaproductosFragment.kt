package com.example.navigationdrawerkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.navigationdrawerkotlin.ViewModel.ProductViewModel
import com.example.navigationdrawerkotlin.ViewModel.ProductViewModelFactory
import com.example.navigationdrawerkotlin.data.RetrofitProduc


class ListaproductosFragment : Fragment(R.layout.fragment_listaproductos) {

    private lateinit var productViewModel: ProductViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar ViewModel
        productViewModel = ViewModelProvider(this, ProductViewModelFactory(RetrofitProduc.instance)).get(ProductViewModel::class.java)

        // Observar cambios en la lista de productos
        productViewModel.productList.observe(viewLifecycleOwner, { productList ->
            // Actualizar la interfaz de usuario con la lista de productos
            // productList contiene la lista de productos obtenida desde la API
        })

        // Obtener los productos
        productViewModel.getProducts()
    }
}


