package com.example.easyfit.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.easyfit.R;
import com.example.easyfit.apiConnector.EatenMealDetailed;

import java.util.List;

public class EatenMealsAdapter extends RecyclerView.Adapter<EatenMealsAdapter.EatenMealsHolder> {

    private Context context;

    private List<EatenMealDetailed> meals;

    public EatenMealsAdapter(Context ct, List<EatenMealDetailed> meals){
        this.context = ct;
        this.meals = meals;

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
        eatenMealsHolder.eatenMealName.setText(meals.get(i).getSimpleProduct().getName());
        eatenMealsHolder.eatenMealCalories.setText(Integer.toString((int) Math.rint(meals.get(i).getSimpleProduct().getKcal()))+" kcal");
        eatenMealsHolder.eatenMealGrams.setText(Integer.toString((int) Math.rint(meals.get(i).getQuantity()))+" g");
    }

    @Override
    public int getItemCount() {
        return meals.size();
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
