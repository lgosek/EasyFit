package com.example.easyfit.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyfit.R;
import com.example.easyfit.adapters.MealIngredientsAdapter;
import com.example.easyfit.apiConnector.SimpleProduct;

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
//                TODO check if name was entered and at least one ingredient exists, save meal in database
                finish();
                break;
        }
        return true;
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
