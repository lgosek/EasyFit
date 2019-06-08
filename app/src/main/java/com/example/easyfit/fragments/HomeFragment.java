package com.example.easyfit.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.easyfit.R;
import com.example.easyfit.activities.AddEatenProductActivity;
import com.example.easyfit.activities.GoalsEditActivity;
import com.example.easyfit.adapters.EatenMealsAdapter;
import com.example.easyfit.apiConnector.Connector;
import com.example.easyfit.apiConnector.EatenMealDetailed;
import com.example.easyfit.apiConnector.Notification;
import com.example.easyfit.apiConnector.SimpleProduct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    View view;
    private final String sharedPreferencesFileName = "com.example.easyfit.sharedpreferences";
    RecyclerView eatenMealsRecyclerView;
    EatenMealsAdapter adapter;

    ProgressBar carbsProgressBar, proteinsProgressBar, fatProgressBar;

    TextView goal, eaten, left;

    Button addEatenMealButton;

    int FATCALS = 9;
    int CARBSCALS = 4;
    int PROTCALS = 4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        goal = view.findViewById(R.id.homeGoalText);
        eaten = view.findViewById(R.id.homeEatenText);
        left = view.findViewById(R.id.homeLeftText);


        carbsProgressBar = view.findViewById(R.id.homeCarbsProgress);
        proteinsProgressBar = view.findViewById(R.id.homeProteinsProgress);
        fatProgressBar = view.findViewById(R.id.homeFatProgress);

        addEatenMealButton = view.findViewById(R.id.homeAddProductButton);
        setAddMealOnClick();

        eatenMealsRecyclerView = view.findViewById(R.id.homeRecyclerView);

        adapter = new EatenMealsAdapter(this.getContext());

        eatenMealsRecyclerView.setAdapter(adapter);
        eatenMealsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;

    }

    private void setAddMealOnClick() {
        this.addEatenMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddEatenProductActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        final List<EatenMealDetailed> meals = new ArrayList<>();
        SharedPreferences sh = getActivity().getSharedPreferences(this.sharedPreferencesFileName, MODE_PRIVATE);

        final String calGoal = sh.getString("caloriesGoal", "-1");
        int caloriesGoalInt = Integer.parseInt(calGoal);
        int carbsPercent = sh.getInt("carbsGoal", -1);
        int protPercent = sh.getInt("proteinsGoal", -1);
        int fatPercent = sh.getInt("fatGoal", -1);

        int prot = Math.round((float)(protPercent * caloriesGoalInt) / 100 /PROTCALS);
        int carbs = Math.round((float)(carbsPercent * caloriesGoalInt) / 100 / CARBSCALS);
        int fat = Math.round((float)(fatPercent * caloriesGoalInt) / 100 / FATCALS);

        carbsProgressBar.setMax(carbs);
        Log.i("infoCarbs", Integer.toString(carbs));
        proteinsProgressBar.setMax(prot);
        fatProgressBar.setMax(fat);


        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        Log.i("app", Integer.toString(year) + Integer.toString(month) + Integer.toString(day));
        

        Call<List<EatenMealDetailed>> call = Connector.getInstance().getEatenMealsDetailed(sh.getInt("loggedInId", -1),year,month,day);
        call.enqueue(new Callback<List<EatenMealDetailed>>() {
            @Override
            public void onResponse(Call<List<EatenMealDetailed>> call, Response<List<EatenMealDetailed>> response) {
                if(!response.isSuccessful()){
                    Log.i("App", Integer.toString(response.code()));

                    if(Build.VERSION.SDK_INT >= 24) {
                        carbsProgressBar.setProgress(0, true);
                        proteinsProgressBar.setProgress(0,true);
                        fatProgressBar.setProgress(0,true);
                    }
                    else{
                        carbsProgressBar.setProgress(0);
                        proteinsProgressBar.setProgress(0);
                        fatProgressBar.setProgress(0);
                    }

                    goal.setText(calGoal);

                    eaten.setText(Integer.toString(0));

                    left.setText(calGoal);


                }else {

                    meals.addAll(response.body());
                    adapter.setMeals(meals);
                    adapter.notifyDataSetChanged();
                    double eatenCalories = 0;
                    double eatenCarbs, eatenProts, eatenFat;
                    eatenCarbs = 0;
                    eatenProts = 0;
                    eatenFat = 0;
                    for(EatenMealDetailed e:response.body()){
                        eatenCalories += e.getSimpleProduct().getKcal();
                        eatenCarbs += e.getSimpleProduct().getCarbohydrates();
                        eatenProts += e.getSimpleProduct().getProteins();
                        eatenFat += e.getSimpleProduct().getFats();

                    }

                    if(Build.VERSION.SDK_INT >= 24) {
                        carbsProgressBar.setProgress((int) eatenCarbs,true);
                        proteinsProgressBar.setProgress((int) eatenProts, true);
                        fatProgressBar.setProgress((int) eatenFat, true);
                    }
                    else{
                        carbsProgressBar.setProgress((int) eatenCarbs);
                        proteinsProgressBar.setProgress((int) eatenProts);
                        fatProgressBar.setProgress((int) eatenFat);
                    }


                    goal.setText(calGoal);

                    eaten.setText(Integer.toString((int)Math.rint(eatenCalories)));

                    left.setText(Integer.toString(Integer.parseInt(goal.getText().toString()) - Integer.parseInt(eaten.getText().toString())));

                }
                adapter.notifyDataSetChanged();
                Log.i("App", response.toString());




            }

            @Override
            public void onFailure(Call<List<EatenMealDetailed>> call, Throwable t) {

                Log.e("App", t.getMessage());
            }
        });
    }
}
