package com.example.easyfit.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.easyfit.R;
import com.example.easyfit.adapters.ComplexMealIngredientsAdapter;
import com.example.easyfit.apiConnector.ComplexMeal;
import com.example.easyfit.apiConnector.ComplexMealDetailed;
import com.example.easyfit.apiConnector.ComplexMealDetailedIngredient;
import com.example.easyfit.apiConnector.Connector;
import com.example.easyfit.apiConnector.SimpleProduct;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDialog extends DialogFragment {
    private ComplexMeal meal;
    private RecyclerView recyclerView;
    private List<String> names = new LinkedList<>();
    private List<String> quantities = new LinkedList<>();
    ComplexMealIngredientsAdapter adapter;

    public void setMeal(ComplexMeal meal) {
        this.meal = meal;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_meal, null);
        builder.setView(view);

        recyclerView = view.findViewById(R.id.mealDialogRecyclerView);

        getIngredientsFromDB();

        builder.setTitle(meal.getName());

        adapter = new ComplexMealIngredientsAdapter(this.getContext(), names, quantities);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        builder.setNegativeButton(getResources().getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    private void getIngredientsFromDB() {
        Call<ComplexMealDetailed> call = Connector.getInstance().getComplexMealDetailed(meal.getId());
        call.enqueue(new Callback<ComplexMealDetailed>() {
            @Override
            public void onResponse(Call<ComplexMealDetailed> call, Response<ComplexMealDetailed> response) {
                if(!response.isSuccessful()){
                    Log.i("AppIngredients", Integer.toString(response.code()));

                }else {
                    Log.i("AppIngredients", "OK");
                    ComplexMealDetailed details = response.body();
                     List<ComplexMealDetailedIngredient> ingredients = details.getIngredients();

                    for (ComplexMealDetailedIngredient ingredient: ingredients) {
                        names.add(ingredient.getName());
                        quantities.add(ingredient.getQuantity()+"");

                        adapter.setNames(names);
                        adapter.setQuantites(quantities);
                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onFailure(Call<ComplexMealDetailed> call, Throwable t) {
                Log.i("App", t.getMessage());
            }
        });
    }
}
