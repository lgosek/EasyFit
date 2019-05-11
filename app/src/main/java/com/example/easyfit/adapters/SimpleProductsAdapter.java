package com.example.easyfit.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.easyfit.R;
import com.example.easyfit.apiConnector.SimpleProduct;

import java.util.List;

public class SimpleProductsAdapter extends RecyclerView.Adapter<SimpleProductsAdapter.SimpleProductsHolder> {

    Context context;
    List<SimpleProduct> products;

    public SimpleProductsAdapter(Context ct, List<SimpleProduct> simpleProducts){
        this.context = ct;
        this.products = simpleProducts;
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
        simpleProductsHolder.name.setText(products.get(i).getName());
        simpleProductsHolder.calories.setText(Double.toString(products.get(i).getKcal()));
    }

    @Override
    public int getItemCount() {
        return products.size();
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
