package com.example.navigationdrawerkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList
import java.util.List

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
        productList.add(Product("mora", 5.000, "moras frescas", R.drawable.imagen1))
        productList.add(Product("tomates", 4.000, "tomate fresco", R.drawable.imagen2))
        productList.add(Product("maracuya", 8.000, "maracuya", R.drawable.imagen3))

        adapter = ProductAdapter(requireContext(), productList)
        recyclerView.adapter = adapter

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance() = homeFragment()
    }
}
