package com.example.easyfit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.easyfit.R;
import com.example.easyfit.adapters.SimpleProductsAdapter;
import com.example.easyfit.apiConnector.SimpleProduct;
import com.example.easyfit.dataManager.DataManager;

import java.util.List;

public class ChooseIngredientActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    SimpleProductsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ingredient);

        recyclerView = findViewById(R.id.chooseIngredientRecyclerView);

        final List<SimpleProduct> spList = DataManager.getInstance().getSimpleProducts();

        adapter = new SimpleProductsAdapter(this, spList, this);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
