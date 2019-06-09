package com.example.easyfit.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyfit.R;
import com.example.easyfit.adapters.NotificationAdapter;
import com.example.easyfit.apiConnector.Connector;
import com.example.easyfit.apiConnector.EatenMealDetailed;
import com.example.easyfit.apiConnector.Goals;
import com.example.easyfit.apiConnector.Notification;
import com.example.easyfit.apiConnector.User;
import com.example.easyfit.apiConnector.UserId;
import com.example.easyfit.notifications.NotificationManager;

import java.sql.Time;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private final String sharedPreferencesFileName = "com.example.easyfit.sharedpreferences";

    EditText emailEdit, passwordEdit;
    LoginActivity thisReference = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEdit = findViewById(R.id.loginEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
    }

    public void login(View view) {

        Call<UserId> call = Connector.getInstance().logIn(new User(emailEdit.getText().toString(),passwordEdit.getText().toString()));
        call.enqueue(new Callback<UserId>() {
            @Override
            public void onResponse(Call<UserId> call, Response<UserId> response) {
                if(response.isSuccessful()){
                    SharedPreferences sh = getSharedPreferences(thisReference.sharedPreferencesFileName, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.putBoolean("loggedIn", true);
                    editor.putString("loggedInUsername", emailEdit.getText().toString());
                    editor.putInt("loggedInId", response.body().getId());
                    editor.apply();

                    Call<Goals> callGoals =Connector.getInstance().getGoals(response.body().getId());
                    callGoals.enqueue(new Callback<Goals>() {
                        @Override
                        public void onResponse(Call<Goals> call, Response<Goals> response) {
                            if(response.isSuccessful()){
                                SharedPreferences sh = getSharedPreferences(thisReference.sharedPreferencesFileName, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sh.edit();
                                editor.putString("caloriesGoal", Integer.toString((int) response.body().getKcal()));
                                editor.putInt("proteinsGoal", response.body().getProteins());
                                editor.putInt("carbsGoal", response.body().getCarbohydrates());
                                editor.putInt("fatGoal", response.body().getFats());
                                editor.apply();
                            }
                        }

                        @Override
                        public void onFailure(Call<Goals> call, Throwable t) {

                        }
                    });

                    Call<List<String>> callNotifications = Connector.getInstance().getNotifications(response.body().getId());
                    callNotifications.enqueue(new Callback<List<String>>() {
                        @Override
                        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                            if(response.isSuccessful()){
                                for(String time:response.body()){
                                    Pattern pattern = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})");

                                    Matcher matcher = pattern.matcher(time);
                                    if(matcher.matches()){
                                        Log.i("app", time);
                                        int hr = Integer.parseInt(matcher.group(1));
                                        int min = Integer.parseInt(matcher.group(2));
                                        Log.i("app",""+hr+" "+min);

                                        NotificationManager.getInstance().setAlarm(thisReference.getApplicationContext(),time,hr,min);
                                        NotificationManager.getInstance().add(Time.valueOf(time));

                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<String>> call, Throwable t) {

                        }
                    });


                    Intent intent = new Intent(thisReference, MainActivity.class);
                    startActivity(intent);
                    setResult(RESULT_OK, null);
                    finish();
                }else if(response.code()==401){
                    Toast.makeText(thisReference, "Niepoprawny login lub hasło", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(thisReference, "Wystąpił błąd po stronie serwera", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserId> call, Throwable t) {
                Toast.makeText(thisReference, "Problem z połączeniem", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
