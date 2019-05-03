package com.example.easyfit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealHolder> {

    Context context;
    String[] meals, calories;

    public MealsAdapter(Context ct, String[] meals, String[] calories){
        this.context = ct;
        this.meals = meals;
        this.calories = calories;
    }

    @NonNull
    @Override
    public MealHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal, viewGroup, false);
        return new MealHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealHolder simpleProductsHolder, int i) {
        simpleProductsHolder.name.setText(meals[i]);
        simpleProductsHolder.calories.setText(calories[i]);
    }

    @Override
    public int getItemCount() {
        return meals.length;
    }

    public class MealHolder extends RecyclerView.ViewHolder {

        TextView name, calories;

        public MealHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.mealName);
            calories = itemView.findViewById(R.id.mealCalories);
        }
    }
}
