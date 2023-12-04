package com.example.navigationdrawerkotlin.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navigationdrawerkotlin.R
import com.example.navigationdrawerkotlin.reponse.Producto

class ProductosAdapter : ListAdapter<Producto, ProductosAdapter.ProductoViewHolder>(ProductosDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_listaproductos, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = getItem(position)
        holder.bind(producto)
    }

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       private val imageViewProducto: ImageView = itemView.findViewById(R.id.imageViewProducto)
        private val textViewNombre: TextView = itemView.findViewById(R.id.textViewNombre)
        private val textViewPrecio: TextView = itemView.findViewById(R.id.textViewPrecio)
        private val textViewDescripcion: TextView = itemView.findViewById(R.id.textViewDescripcion)
        private val textViewTiemporeclamo: TextView = itemView.findViewById(R.id.textViewTiemporeclamo)

        fun bind(producto: Producto) {
            // Puedes personalizar esto según las vistas en tu fragment_listaproductos.xml
            textViewNombre.text = producto.nombres
            textViewPrecio.text = "Precio: $${producto.precio}"
            textViewDescripcion.text = "Descripción: ${producto.descripcion}"
            textViewTiemporeclamo.text = "Tiempo de Reclamo: ${producto.tiempo_reclamo}"

            // Utiliza Glide u otra biblioteca para cargar imágenes desde la URL
            Glide.with(itemView.context)
               .load(producto.imagen)
                .placeholder(R.drawable.cargando) // Imagen de carga si es necesario
                .into(imageViewProducto)
        }
    }

    private class ProductosDiffCallback : DiffUtil.ItemCallback<Producto>() {
        override fun areItemsTheSame(oldItem: Producto, newItem: Producto): Boolean {
            return oldItem === newItem // Cambia esto si tu Producto tiene una clave única
        }

        override fun areContentsTheSame(oldItem: Producto, newItem: Producto): Boolean {
            return oldItem == newItem
        }
    }
}