package com.example.easyfit.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyfit.R;
import com.example.easyfit.notifications.NotificationManager;

public class GoalsEditActivity extends AppCompatActivity {

    NumberPicker carbsPicker, fatPicker, proteinsPicker;
    EditText caloriesGoalEdit;
    TextView warningText, carbsGrams, protGrams, fatGrams;

    int FATCALS = 9;
    int CARBSCALS = 4;
    int PROTCALS = 4;
    int caloriesGoalInt;

    boolean error = false;

    private final String sharedPreferencesFileName = "com.example.easyfit.sharedpreferences";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals_edit);

//        Toolbar toolbar = findViewById(R.id.toolbar2);
//        setSupportActionBar(toolbar);


        warningText = findViewById(R.id.goalsEditWarningText);
        warningText.setText("");

        caloriesGoalEdit = findViewById(R.id.caloriesEdit);

        carbsPicker = findViewById(R.id.carbsPicker);
        fatPicker = findViewById(R.id.fatPicker);
        proteinsPicker = findViewById(R.id.proteinsPicker);

        carbsGrams = findViewById(R.id.goalsCarbsAmount);
        protGrams = findViewById(R.id.goalsProteinsAmount);
        fatGrams = findViewById(R.id.goalsFatAmount);


        SharedPreferences sh = getSharedPreferences(this.sharedPreferencesFileName, MODE_PRIVATE);

        String calGoal = sh.getString("caloriesGoal", "2000");
        caloriesGoalEdit.setText(calGoal);
        caloriesGoalInt = Integer.parseInt(calGoal);
        int carbsPercent = sh.getInt("carbsGoal", 50);
        int protPercent = sh.getInt("proteinsGoal", 20);
        int fatPercent = sh.getInt("fatGoal", 30);

        updateGrams(protPercent,carbsPercent,fatPercent);


        //String[] values = {"0","5","10","15","20","25","30","35","40","45","50","55","60","65","70","75","80","85","90","95","100"};

//        carbsPicker.setDisplayedValues(values);
//        fatPicker.setDisplayedValues(values);
//        proteinsPicker.setDisplayedValues(values);
        carbsPicker.setMinValue(0);
        carbsPicker.setMaxValue(100);
        carbsPicker.setValue(carbsPercent);

        fatPicker.setMinValue(0);
        fatPicker.setMaxValue(100);
        fatPicker.setValue(fatPercent);

        proteinsPicker.setMinValue(0);
        proteinsPicker.setMaxValue(100);
        proteinsPicker.setValue(protPercent);

        caloriesGoalEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = caloriesGoalEdit.getText().toString();
                if(!text.equals("")) {
                    caloriesGoalInt = Integer.parseInt(text);
                    error = false;
                }
                else {
                    caloriesGoalInt = 0;
                    error = true;
                }
                updateGrams(proteinsPicker.getValue(), carbsPicker.getValue(), fatPicker.getValue());
            }
        });

        carbsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal + fatPicker.getValue() + proteinsPicker.getValue() != 100) {
                    warningText.setText(getResources().getText(R.string.warningText));
                    error = true;
                }
                else {
                    warningText.setText("");
                    error = false;
                }

                int carbs = Math.round((float)(newVal * caloriesGoalInt) / 100 / CARBSCALS);
                carbsGrams.setText(carbs+"g");
            }
        });

        fatPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal + carbsPicker.getValue() + proteinsPicker.getValue() != 100) {
                    warningText.setText(getResources().getText(R.string.warningText));
                    error = true;
                }
                else {
                    warningText.setText("");
                    error = false;
                }

                int fat = Math.round((float)(newVal * caloriesGoalInt) / 100 / FATCALS);
                fatGrams.setText(fat+"g");
            }
        });

        proteinsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(newVal + fatPicker.getValue() + carbsPicker.getValue() != 100) {
                    warningText.setText(getResources().getText(R.string.warningText));
                    error = true;
                }
                else {
                    warningText.setText("");
                    error = false;
                }

                int prot = Math.round((float)(newVal * caloriesGoalInt) / 100 / PROTCALS);
                protGrams.setText(prot+"g");
            }
        });

    }

    private void updateGrams(int protPercent, int carbsPercent, int fatPercent) {
        if(caloriesGoalInt==0){
            carbsGrams.setText("-");
            protGrams.setText("-");
            fatGrams.setText("-");
            return;
        }

        int prot = Math.round((float)(protPercent * caloriesGoalInt) / 100 /PROTCALS);
        int carbs = Math.round((float)(carbsPercent * caloriesGoalInt) / 100 / CARBSCALS);
        int fat = Math.round((float)(fatPercent * caloriesGoalInt) / 100 / FATCALS);

        carbsGrams.setText(carbs+"g");
        protGrams.setText(prot+"g");
        fatGrams.setText(fat+"g");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.goals_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.goalsEditAccept:

                if(error){
                    Toast.makeText(this, "Wprowadź poprawne dane!", Toast.LENGTH_LONG).show();
                    break;
                }

                //saving data to shared preferences file
                SharedPreferences sh = getSharedPreferences(this.sharedPreferencesFileName, MODE_PRIVATE);
                SharedPreferences.Editor editor = sh.edit();
                editor.putString("caloriesGoal", caloriesGoalEdit.getText().toString());
                editor.putInt("proteinsGoal", proteinsPicker.getValue());
                editor.putInt("carbsGoal", carbsPicker.getValue());
                editor.putInt("fatGoal", fatPicker.getValue());
                editor.apply();

                finish();
                break;
        }
        return true;
    }
}
