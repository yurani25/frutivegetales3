package com.example.navigationdrawerkotlin.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navigationdrawerkotlin.data.ProductApi

class ProductViewModelFactory (private val productApi: ProductApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(productApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}