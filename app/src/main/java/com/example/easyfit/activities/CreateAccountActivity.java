package com.example.easyfit.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyfit.R;

public class CreateAccountActivity extends AppCompatActivity {
    private final String sharedPreferencesFileName = "com.example.easyfit.sharedpreferences";

    EditText email, password1, password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        email = findViewById(R.id.createAccountEmailEdit);
        password1 = findViewById(R.id.createAccountPassword1Edit);
        password2 = findViewById(R.id.createAccountPassword2Edit);
    }

    public void createAccount(View view) {
        if(validate()){
            // TODO saving user to database
            SharedPreferences sh = getSharedPreferences(this.sharedPreferencesFileName, MODE_PRIVATE);
            SharedPreferences.Editor editor = sh.edit();
            editor.putBoolean("loggedIn", true);
            editor.putString("loggedInUsername", email.getText().toString());
            editor.apply();

            Intent intent = new Intent(this, GoalsEditActivity.class);
            intent.putExtra("initialEdit", true);
            startActivity(intent);
            setResult(RESULT_OK, null);
            finish();
        }
        else{
            Toast.makeText(this, "Hasła się różnią!", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validate() {
        return password1.getText().toString().equals(password2.getText().toString());
    }
}
