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
import com.example.easyfit.apiConnector.User;
import com.example.easyfit.apiConnector.UserId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity {
    private final String sharedPreferencesFileName = "com.example.easyfit.sharedpreferences";
    CreateAccountActivity thisReference = this;
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
            Call<UserId> call = Connector.getInstance().register(new User(email.getText().toString(),password1.getText().toString()));
            call.enqueue(new Callback<UserId>() {
                @Override
                public void onResponse(Call<UserId> call, Response<UserId> response) {
                    if(response.isSuccessful()){
                        SharedPreferences sh = getSharedPreferences(thisReference.sharedPreferencesFileName, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sh.edit();
                        editor.putBoolean("loggedIn", true);
                        editor.putString("loggedInUsername", email.getText().toString());
                        editor.putInt("loggedInId", response.body().getId());
                        editor.apply();

                        Intent intent = new Intent(thisReference, GoalsEditActivity.class);
                        intent.putExtra("initialEdit", true);
                        startActivity(intent);
                        setResult(RESULT_OK, null);
                        finish();
                    }else if(response.code() == 409){
                        Toast.makeText(thisReference, "Ten email jest już przypisany do innego konta", Toast.LENGTH_SHORT).show();
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
        else{
            Toast.makeText(this, "Hasła się różnią!", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validate() {
        return password1.getText().toString().equals(password2.getText().toString());
    }
}
