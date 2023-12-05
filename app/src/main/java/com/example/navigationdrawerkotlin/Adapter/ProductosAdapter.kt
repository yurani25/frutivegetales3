package com.example.navigationdrawerkotlin.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navigationdrawerkotlin.ListaproductosFragment
import com.example.navigationdrawerkotlin.R
import com.example.navigationdrawerkotlin.ViewModel.ProductViewModel
import com.example.navigationdrawerkotlin.editproductoFragment
import com.example.navigationdrawerkotlin.model.Productos
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProductosAdapter (val context: Context, var dataset: List<Productos>): RecyclerView.Adapter<ProductosAdapter.ProductViewHolder>() {
    class ProductViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val product = view.findViewById(R.id.cartProductos) as RelativeLayout
        val nombres: TextView = view.findViewById(R.id.textViewNombre)
        val tiemporeclamo: TextView = view.findViewById(R.id.textViewTiemporeclamo)
        val imagen: ImageView = view.findViewById(R.id.imageViewProducto)
        val precio: TextView = view.findViewById(R.id.textViewPrecio)
        val descripcion: TextView = view.findViewById(R.id.textViewDescripcion)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.productos, parent, false)
        return ProductViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = dataset[position]
        holder.nombres.text = item.nombres

        Glide
            .with(context).load(item.imagen)
            .centerInside()
            .into(holder.imagen)

        var imagen = item.imagen


        holder.tiemporeclamo.text = item.tiempo_reclamo
        holder.descripcion.text = item.descripcion
        holder.precio.text = item.precio.toString()

        holder.product.setOnClickListener {
            mostrarProducto(item.nombres,
                item.descripcion,
                item.tiempo_reclamo,
                item.precio.toString(),
                item.user_id.toString(),
               item.id.toString(),

            )
        }

    }

    fun mostrarProducto(nombres: String,
                        descripcion: String,
                        tiempo_reclamo: String,
                        precio: String,
                        user_id: String,
                        id: String) {
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(R.layout.producto_detalle)


        val editar: Button? = bottomSheetDialog.findViewById<Button>(R.id.btnEdit)
        val eliminar: Button? = bottomSheetDialog.findViewById<Button>(R.id.btnDelete)

        val viewModel = ViewModelProvider(context as ViewModelStoreOwner).get(ProductViewModel::class.java)
        eliminar?.setOnClickListener{
            viewModel.eliminarProducto(id){
                Toast.makeText(context, "Error al eliminar el producto", Toast.LENGTH_SHORT).show()
            }
            if (context is FragmentActivity) {
                val fragmentManager = context.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, ListaproductosFragment())
                fragmentTransaction.addToBackStack(null)
                bottomSheetDialog.dismiss()
                fragmentTransaction.commit()
            }

        }
        editar?.setOnClickListener {
            val bundle = Bundle().apply {
                putString("nombres", nombres)
                putString("descripcion", descripcion)
                putString("tiempo_reclamo", tiempo_reclamo)
                putString("precio", precio)
                putString("user_id", user_id)
                putString("id", id)
            }

            val editFragment = editproductoFragment()
            editFragment.arguments = bundle

            if (context is FragmentActivity) {
                val fragmentManager = context.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, editFragment)
                fragmentTransaction.addToBackStack(null)
                bottomSheetDialog.dismiss()
                fragmentTransaction.commit()
            }

        }



   bottomSheetDialog.show()
    }

}