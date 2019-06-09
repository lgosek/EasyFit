package com.example.easyfit.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyfit.R;
import com.example.easyfit.activities.AddEatenProductActivity;
import com.example.easyfit.activities.ChooseIngredientActivity;
import com.example.easyfit.apiConnector.ComplexMeal;
import com.example.easyfit.clickListeners.ItemClickListener;
import com.example.easyfit.dialogs.MealDialog;
import com.example.easyfit.dialogs.QuantityDialog;
import com.example.easyfit.dialogs.SimpleProductDialog;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealHolder> {

    Context context;
    Activity currentActivity;
    List<ComplexMeal> meals;


    public MealsAdapter(Context ct,List<ComplexMeal> meals, Activity activity){
        this.context = ct;
        this.meals = meals;
        this.currentActivity = activity;
    }

    public MealsAdapter(Context ct,List<ComplexMeal> meals) {
        this(ct, meals, null);
    }

    @NonNull
    @Override
    public MealHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal, viewGroup, false);
        return new MealHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealHolder mealHolder, int i) {
        mealHolder.name.setText(meals.get(i).getName());
        mealHolder.calories.setText(Double.toString(meals.get(i).getKcal()));

        if (currentActivity != null && currentActivity.getClass() == AddEatenProductActivity.class) {
            mealHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
//                    Toast.makeText(context, "clicked in meals adding" + position, Toast.LENGTH_SHORT).show();

                    addingProductAction(position);

                }
            });
        }
        else{
            mealHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    mealDetails(position);
//                    Toast.makeText(context, "clicked in meals products" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void mealDetails(int position) {
        MealDialog dialog = new MealDialog();
        dialog.setMeal(meals.get(position));
        dialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "MEAL_DIALOG");
    }

    private void addingProductAction(int position) {
        QuantityDialog dialog = new QuantityDialog();
        dialog.setCurrentActivity(currentActivity);
        dialog.setMeal(meals.get(position));
        dialog.setIntention("addEatenMeal");
        dialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "QUANTITY_DIALOG");

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class MealHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, calories;
        private ItemClickListener itemClickListener;


        public MealHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.mealName);
            calories = itemView.findViewById(R.id.mealCalories);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }
}
