package com.example.navigationdrawerkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationdrawerkotlin.Adapter.ProductosAdapter
import com.example.navigationdrawerkotlin.databinding.FragmentListaproductosBinding
import com.example.navigationdrawerkotlin.ViewModel.ProductViewModel



class ListaproductosFragment : Fragment() {

    private lateinit var binding: FragmentListaproductosBinding
    private lateinit var viewModel: ProductViewModel
    private lateinit var adapter: ProductosAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        setupRecyclerView()

        viewModel.obtenerProductos()

        viewModel.listaProductos.observe(viewLifecycleOwner) { nuevaListaProductos ->
            nuevaListaProductos?.let {
                adapter.dataset = it
                adapter.notifyDataSetChanged()
            }
        }
    }
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListaproductosBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ProductosAdapter(requireContext(), ArrayList())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
    }
}