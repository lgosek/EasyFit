package com.example.easyfit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easyfit.R;
import com.example.easyfit.activities.AddMealActivity;
import com.example.easyfit.activities.MainActivity;
import com.example.easyfit.adapters.MealsAdapter;

public class MealsFragment extends Fragment {

    RecyclerView recyclerView;

    FloatingActionButton addMealButton;


    // TODO remove dummy data
    String[] meals, calories;

    MealsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meals,container,false);

        recyclerView = view.findViewById(R.id.mealsRecyclerView);
        addMealButton = view.findViewById(R.id.mealsAddButton);

        if(getActivity().getClass()== MainActivity.class) {
            addButtonOnClickHandler();
        }
        else{
            addMealButton.hide();
        }

        meals = view.getResources().getStringArray(R.array.meals);
        calories = view.getResources().getStringArray(R.array.simpleProductsCalories);

        adapter = new MealsAdapter(this.getContext(), meals, calories);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;    }

    private void addButtonOnClickHandler() {
        this.addMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "Add meal", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), AddMealActivity.class);
                startActivity(intent);
            }
        });
    }
}
