package com.example.easyfit.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyfit.R;
import com.example.easyfit.adapters.MealIngredientsAdapter;
import com.example.easyfit.apiConnector.ComplexMealIngredients;
import com.example.easyfit.apiConnector.Connector;
import com.example.easyfit.apiConnector.ID;
import com.example.easyfit.apiConnector.NewComplexMeal;
import com.example.easyfit.apiConnector.SimpleProduct;
import com.example.easyfit.dataManager.DataManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMealActivity extends AppCompatActivity {

    static final int CHOOSE_INGREDIENT_REQUEST_CODE = 1;  // The request code

    EditText mealNameEdit;

    Button addIngredientButton;

    RecyclerView ingredientsRecycler;

    MealIngredientsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        mealNameEdit = findViewById(R.id.newMealName);
        ingredientsRecycler = findViewById(R.id.addNewMealRecyclerView);
        addIngredientButton = findViewById(R.id.addMealIngredientButton);

        adapter = new MealIngredientsAdapter(this);

        ingredientsRecycler.setAdapter(adapter);
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meal_add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mealAddAccept:
                storeMeal();
                finish();
                break;
        }
        return true;
    }
    public void storeMeal(){
        if(adapter.getItemCount()>0){
            List<SimpleProduct> products = adapter.getProducts();
            List<String> quantities = adapter.getQuantites();

            NewComplexMeal complexMeal = new NewComplexMeal(mealNameEdit.getText().toString());

            while (!products.isEmpty()){
                complexMeal.addIngredients(new ComplexMealIngredients(products.remove(0).getId(),Integer.parseInt(quantities.remove(0))));
            }



            Call<ID> call = Connector.getInstance().saveComplexMeal(complexMeal);
            call.enqueue(new Callback<ID>(){
                @Override
                public void onResponse(Call<ID> call, Response<ID> response) {
                    if(response.isSuccessful()){
                        DataManager.getInstance().synchronizeComplexMeals(true);
                    }
                    Log.i("app", ""+response.code()+response.body());
                    finish();
                }

                @Override
                public void onFailure(Call<ID> call, Throwable t) {
                    Log.i("app", t.getMessage());
                    finish();
                }
            });

        }
    }

    public void addIngredient(View view) {
        Intent intent = new Intent(this, ChooseIngredientActivity.class);
        startActivityForResult(intent, CHOOSE_INGREDIENT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CHOOSE_INGREDIENT_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                SimpleProduct product = (SimpleProduct) data.getSerializableExtra("product");
                String quantity = data.getStringExtra("quantity");
//                Toast.makeText(this, product.getName() + " " + quantity, Toast.LENGTH_SHORT).show();
                adapter.addProduct(product);
                adapter.addQuantity(quantity);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
