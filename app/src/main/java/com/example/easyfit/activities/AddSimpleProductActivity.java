package com.example.easyfit.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyfit.R;
import com.example.easyfit.apiConnector.Connector;
import com.example.easyfit.apiConnector.EatenProduct;
import com.example.easyfit.apiConnector.EatenProductsWrapper;
import com.example.easyfit.apiConnector.ID;
import com.example.easyfit.apiConnector.NewSimpleProduct;
import com.example.easyfit.apiConnector.SimpleProduct;
import com.example.easyfit.apiConnector.SimpleProductWrapper;
import com.example.easyfit.dataManager.DataManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSimpleProductActivity extends AppCompatActivity {

    EditText nameEdit, caloriesEdit, carbsEdit, proteinsEdit, fatEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_simple_product);

        nameEdit = findViewById(R.id.newSimpleProductName);
        caloriesEdit = findViewById(R.id.newSimpleProductCalories);
        carbsEdit = findViewById(R.id.newSimpleProductCarbs);
        proteinsEdit = findViewById(R.id.newSimpleProductProteins);
        fatEdit = findViewById(R.id.newSimpleProductFat);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_products_add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.simpleProductsAddAccept:
                storeProduct();
                finish();
                break;
        }
        return true;
    }

    private void storeProduct() {
//        List<SimpleProduct> newProducts = new ArrayList<>();
        NewSimpleProduct newProduct = new NewSimpleProduct(
                nameEdit.getText().toString(),
                Double.parseDouble(caloriesEdit.getText().toString()),
                Double.parseDouble(proteinsEdit.getText().toString()),
                Double.parseDouble(fatEdit.getText().toString()),
                Double.parseDouble(carbsEdit.getText().toString()));

        Call<ID> call = Connector.getInstance().saveSimpleProduct(newProduct);
        call.enqueue(new Callback<ID>(){
            @Override
            public void onResponse(Call<ID> call, Response<ID> response) {
                Log.i("appOk", ""+response.code()+response.body());
                DataManager.getInstance().synchronizeSimpleProducts(true);
                finish();
            }

            @Override
            public void onFailure(Call<ID> call, Throwable t) {
                Log.i("appNotOk", t.getMessage());
                finish();
            }
        });
    }
}
