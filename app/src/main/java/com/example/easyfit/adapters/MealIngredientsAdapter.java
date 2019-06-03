package com.example.easyfit.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.easyfit.R;
import com.example.easyfit.apiConnector.SimpleProduct;

import java.util.LinkedList;
import java.util.List;

public class MealIngredientsAdapter extends RecyclerView.Adapter<MealIngredientsAdapter.MealIngredientsHolder> {

    Context context;
    List<SimpleProduct> products;
    List<String> quantites;

    public MealIngredientsAdapter(Context ct){
        this.context = ct;
        this.products = new LinkedList<>();
        this.quantites = new LinkedList<>();
    }

    public void addProduct(SimpleProduct product){
        this.products.add(product);
    }

    public void addQuantity(String quantity){
        this.quantites.add(quantity);
    }

    @NonNull
    @Override
    public MealIngredientsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal_ingredient, viewGroup, false);
        return new MealIngredientsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealIngredientsHolder mealIngredientsHolder, int i) {
        mealIngredientsHolder.name.setText(products.get(i).getName());
        mealIngredientsHolder.grams.setText(quantites.get(i));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MealIngredientsHolder extends RecyclerView.ViewHolder {

        TextView name, grams;

        public MealIngredientsHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ingredientName);
            grams = itemView.findViewById(R.id.ingredientQuantity);

        }
    }
}
