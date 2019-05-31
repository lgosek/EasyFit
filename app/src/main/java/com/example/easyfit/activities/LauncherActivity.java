package com.example.easyfit.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.easyfit.R;

public class LauncherActivity extends AppCompatActivity {
    private final String sharedPreferencesFileName = "com.example.easyfit.sharedpreferences";

//    private Button loginButton, createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

//        loginButton = findViewById(R.id.launcherLoginButton);
//        createAccountButton = findViewById(R.id.launcherCreateAccountButton);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sh = getSharedPreferences(this.sharedPreferencesFileName, MODE_PRIVATE);

        boolean loggedIn = sh.getBoolean("loggedIn", false);
        if(loggedIn){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivityForResult(intent, 0);
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                this.finish();
            }
        }
    }
}
