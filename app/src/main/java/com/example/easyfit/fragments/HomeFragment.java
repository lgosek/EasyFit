package com.example.easyfit.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyfit.R;
import com.example.easyfit.adapters.EatenMealsAdapter;
import com.example.easyfit.apiConnector.Connector;
import com.example.easyfit.apiConnector.EatenMealDetailed;
import com.example.easyfit.apiConnector.SimpleProduct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    View view;

    RecyclerView eatenMealsRecyclerView;
    EatenMealsAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        final List<EatenMealDetailed> meals = new ArrayList<>();



        eatenMealsRecyclerView = view.findViewById(R.id.homeRecyclerView);

        adapter = new EatenMealsAdapter(this.getContext(), meals);

        eatenMealsRecyclerView.setAdapter(adapter);
        eatenMealsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));



        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        Log.i("app", Integer.toString(year) + Integer.toString(month) + Integer.toString(day));

        Call<List<EatenMealDetailed>> call = Connector.getInstance().getEatenMealsDetailed(1,year,month,day);
        call.enqueue(new Callback<List<EatenMealDetailed>>() {
            @Override
            public void onResponse(Call<List<EatenMealDetailed>> call, Response<List<EatenMealDetailed>> response) {
                if(!response.isSuccessful()){
                    Log.i("App", Integer.toString(response.code()));

                }else {

                    meals.addAll(response.body());
                }
                adapter.notifyItemInserted(0);
                Log.i("App", response.toString());




            }

            @Override
            public void onFailure(Call<List<EatenMealDetailed>> call, Throwable t) {

                Log.e("App", t.getMessage());
            }
        });



        return view;

    }
}
