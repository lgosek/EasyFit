package com.example.easyfit.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.easyfit.R;

public class EatenMealsAdapter extends RecyclerView.Adapter<EatenMealsAdapter.EatenMealsHolder> {

    private Context context;

    //TODO remove dummy data
    String[] meals, grams, calories;

    public EatenMealsAdapter(Context ct){
        this.context = ct;

        meals = context.getResources().getStringArray(R.array.simpleProducts);
        calories = context.getResources().getStringArray(R.array.simpleProductsCalories);
        grams = context.getResources().getStringArray(R.array.grams);
    }

    @NonNull
    @Override
    public EatenMealsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.eaten_meal_item, viewGroup, false);
        return new EatenMealsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EatenMealsHolder eatenMealsHolder, int i) {
        eatenMealsHolder.eatenMealName.setText(meals[i]);
        eatenMealsHolder.eatenMealCalories.setText(calories[i]);
        eatenMealsHolder.eatenMealGrams.setText(grams[i]);
    }

    @Override
    public int getItemCount() {
        return meals.length;
    }

    public class EatenMealsHolder extends RecyclerView.ViewHolder {
        TextView eatenMealName, eatenMealGrams, eatenMealCalories;
        public EatenMealsHolder(@NonNull View itemView) {
            super(itemView);

            eatenMealName = itemView.findViewById(R.id.eatenMealName);
            eatenMealGrams = itemView.findViewById(R.id.eatenMealGrams);
            eatenMealCalories = itemView.findViewById(R.id.eatenMealCalories);
        }
    }
}
