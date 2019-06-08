package com.example.easyfit.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyfit.R;
import com.example.easyfit.apiConnector.Connector;
import com.example.easyfit.apiConnector.EatenMealDetailed;
import com.example.easyfit.apiConnector.User;
import com.example.easyfit.apiConnector.UserId;
import com.example.easyfit.notifications.NotificationManager;

import java.util.List;

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

                    Intent intent = new Intent(thisReference, MainActivity.class);
                    startActivity(intent);
                    setResult(RESULT_OK, null);
                    finish();
                }else{
                    Toast.makeText(thisReference, "Niepoprawny login lub hasło", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserId> call, Throwable t) {
                Toast.makeText(thisReference, "Problem z połączeniem", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
