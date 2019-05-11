package com.example.easyfit.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.easyfit.R;

public class SimpleProductsAdapter extends RecyclerView.Adapter<SimpleProductsAdapter.SimpleProductsHolder> {

    Context context;
    String[] products, calories;

    public SimpleProductsAdapter(Context ct, String[] products, String[] calories){
        this.context = ct;
        this.products = products;
        this.calories = calories;
    }

    @NonNull
    @Override
    public SimpleProductsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.simple_product, viewGroup, false);
        return new SimpleProductsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleProductsHolder simpleProductsHolder, int i) {
        simpleProductsHolder.name.setText(products[i]);
        simpleProductsHolder.calories.setText(calories[i]);
    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    public class SimpleProductsHolder extends RecyclerView.ViewHolder {

        TextView name, calories;

        public SimpleProductsHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.simpleProductName);
            calories = itemView.findViewById(R.id.simpleProductCalories);
        }
    }
}
