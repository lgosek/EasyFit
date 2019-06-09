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

import java.util.LinkedList;
import java.util.List;

public class ComplexMealIngredientsAdapter extends RecyclerView.Adapter<ComplexMealIngredientsAdapter.ComplexMealIngredientsHolder> {
    private Context context;
    private List<String> names = new LinkedList<>();
    private List<String> quantites = new LinkedList<>();

    public void setNames(List<String> names) {
        this.names = names;
    }

    public void setQuantites(List<String> quantites) {
        this.quantites = quantites;
    }

    public ComplexMealIngredientsAdapter(Context context, List<String> names, List<String> quantites) {
        this.context = context;
        this.names = names;
        this.quantites = quantites;
    }

    @NonNull
    @Override
    public ComplexMealIngredientsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal_ingredient, viewGroup, false);
        return new ComplexMealIngredientsHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ComplexMealIngredientsHolder complexMealIngredientsHolder, int i) {
        complexMealIngredientsHolder.name.setText(names.get(i));
        complexMealIngredientsHolder.grams.setText(quantites.get(i));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ComplexMealIngredientsHolder extends RecyclerView.ViewHolder {
        TextView name, grams;

        public ComplexMealIngredientsHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ingredientName);
            grams = itemView.findViewById(R.id.ingredientQuantity);
        }
    }
}
