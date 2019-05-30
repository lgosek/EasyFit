package com.example.easyfit.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyfit.R;
import com.example.easyfit.notifications.NotificationManager;

public class LoginActivity extends AppCompatActivity {
    private final String sharedPreferencesFileName = "com.example.easyfit.sharedpreferences";

    EditText emailEdit, passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEdit = findViewById(R.id.loginEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
    }

    public void login(View view) {
        if(emailEdit.getText().toString().equals("admin@gmail.com") && passwordEdit.getText().toString().equals("password")){
            SharedPreferences sh = getSharedPreferences(this.sharedPreferencesFileName, MODE_PRIVATE);
            SharedPreferences.Editor editor = sh.edit();
            editor.putBoolean("loggedIn", true);
            editor.putString("loggedInUsername", emailEdit.getText().toString());
            editor.apply();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            setResult(RESULT_OK, null);
            finish();
        }
        else
        Toast.makeText(this, "Niepoprawny login lub hasło", Toast.LENGTH_SHORT).show();
    }
}
