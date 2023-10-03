package com.example.navigationdrawerkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class homeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = rootView.findViewById(R.id.productRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val productList = ArrayList<Product>()
        productList.add(Product("Mora", 5000.0, "Moras frescas", R.drawable.imagen1))
        productList.add(Product("Tomates", 4000.0, "Tomates frescos", R.drawable.imagen2))
        productList.add(Product("Maracuyá", 8000.0, "Maracuyá fresco", R.drawable.imagen3))

        adapter = ProductAdapter(requireContext(), productList)
        recyclerView.adapter = adapter

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance() = homeFragment()
    }
}
