package com.example.navigationdrawerkotlin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText("Precio: $" + product.getPrice());
        holder.productDescriptionTextView.setText(product.getDescription());
        holder.productImageView.setImageResource(product.getImageResource());

        holder.addToCartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementa la lógica para agregar el producto al carrito
                // Puedes utilizar la posición 'position' para identificar el producto
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView productNameTextView;
        public TextView productPriceTextView;
        public TextView productDescriptionTextView;
        public ImageView productImageView;
        public ImageView addToCartImageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            productDescriptionTextView = itemView.findViewById(R.id.productDescriptionTextView);
            productImageView = itemView.findViewById(R.id.productImage);
            addToCartImageView = itemView.findViewById(R.id.addToCartImageView);
        }
    }
}
