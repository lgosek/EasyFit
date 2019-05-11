package com.example.easyfit.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyfit.R;
import com.example.easyfit.adapters.EatenMealsAdapter;

public class HomeFragment extends Fragment {

    View view;

    RecyclerView eatenMealsRecyclerView;
    EatenMealsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);

        eatenMealsRecyclerView = view.findViewById(R.id.homeRecyclerView);

        adapter = new EatenMealsAdapter(this.getContext());

        eatenMealsRecyclerView.setAdapter(adapter);
        eatenMealsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;

    }
}
