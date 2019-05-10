package com.example.easyfit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

public class GoalsFragment extends Fragment {

    View view;
    Button editButton;

    int FATCALS = 9;
    int CARBSCALS = 4;
    int PROTCALS = 4;

    TextView goalsCaloriesValueText, goalsCarbsValueText, goalsProteinsValueText, goalsFatValueText;

    private final String sharedPreferencesFileName = "com.example.easyfit.sharedpreferences";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_goals,container,false);
        editButton = view.findViewById(R.id.goalsEditButton);
        setEditOnClick();

        goalsCaloriesValueText = view.findViewById(R.id.goalsCaloriesValue);
        goalsCarbsValueText = view.findViewById(R.id.goalsCarbsValue);
        goalsProteinsValueText = view.findViewById(R.id.goalsProteinsValue);
        goalsFatValueText = view.findViewById(R.id.goalsFatValue);

        //reading values from shared preferences file and setting up values
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sh = getActivity().getSharedPreferences(this.sharedPreferencesFileName, MODE_PRIVATE);

        String calGoal = sh.getString("caloriesGoal", "-");
        int carbsPercent = sh.getInt("carbsGoal", -1);
        int protPercent = sh.getInt("proteinsGoal", -1);
        int fatPercent = sh.getInt("fatGoal", -1);

        setValues(calGoal, carbsPercent, protPercent, fatPercent);
    }

    private void setValues(String calGoal, int carbsPercent, int protPercent, int fatPercent) {
        goalsCaloriesValueText.setText(calGoal);
        int caloriesGoalInt = 0;
        if(!calGoal.equals("-"))
            caloriesGoalInt = Integer.parseInt(calGoal);
        if(carbsPercent != -1){
            if(caloriesGoalInt != 0){
                int carbs = Math.round((float)(carbsPercent * caloriesGoalInt) / 100 / CARBSCALS);
                String text = new StringBuilder().append("(").append(carbs).append("g) ").append(carbsPercent).append("%").toString();
                goalsCarbsValueText.setText(text);
            }
        }
        else
            goalsCarbsValueText.setText("-");
        if(protPercent != -1){
            if(caloriesGoalInt != 0){
                int prots = Math.round((float)(protPercent * caloriesGoalInt) / 100 / PROTCALS);
                String text = new StringBuilder().append("(").append(prots).append("g) ").append(protPercent).append("%").toString();
                goalsProteinsValueText.setText(text);
            }
        }
        else
            goalsProteinsValueText.setText("-");
        if(fatPercent != -1){
            if(caloriesGoalInt != 0){
                int fat = Math.round((float)(fatPercent * caloriesGoalInt) / 100 / FATCALS);
                String text = new StringBuilder().append("(").append(fat).append("g) ").append(fatPercent).append("%").toString();
                goalsFatValueText.setText(text);
            }
        }
        else
            goalsFatValueText.setText("-");
    }

    private void setEditOnClick(){
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GoalsEditActivity.class);
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();
                startActivity(intent);
            }
        });
    }
}
