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
import com.example.easyfit.clickListeners.ItemClickListener;
import com.example.easyfit.dialogs.QuantityDialog;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MealHolder> {

    Context context;
    String[] meals, calories;
    Activity currentActivity;


    public MealsAdapter(Context ct, String[] meals, String[] calories, Activity activity){
        this.context = ct;
        this.meals = meals;
        this.calories = calories;
        this.currentActivity = activity;
    }

    public MealsAdapter(Context ct, String[] meals, String[] calories) {
        this(ct, meals, calories, null);
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
        mealHolder.name.setText(meals[i]);
        mealHolder.calories.setText(calories[i]);

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
//                    Toast.makeText(context, "clicked in meals products" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void addingProductAction(int position) {
        QuantityDialog dialog = new QuantityDialog();
        dialog.setCurrentActivity(currentActivity);
//        TODO wpisywanie złożonego posiłku
        dialog.setIntention("addEatenMeal");
        dialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "QUANTITY_DIALOG");

    }

    @Override
    public int getItemCount() {
        return meals.length;
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
