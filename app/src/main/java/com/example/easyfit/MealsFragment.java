package com.example.easyfit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MealsFragment extends Fragment {

    RecyclerView recyclerView;

    // TODO remove dummy data
    String[] meals, calories;

    MealsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meals,container,false);

        recyclerView = view.findViewById(R.id.mealsRecyclerView);

        meals = view.getResources().getStringArray(R.array.meals);
        calories = view.getResources().getStringArray(R.array.simpleProductsCalories);

        adapter = new MealsAdapter(this.getContext(), meals, calories);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;    }
}
