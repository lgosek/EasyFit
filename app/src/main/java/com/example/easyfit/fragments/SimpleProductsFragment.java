package com.example.easyfit.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyfit.R;
import com.example.easyfit.adapters.SimpleProductsAdapter;
import com.example.easyfit.apiConnector.Connector;
import com.example.easyfit.apiConnector.SimpleProduct;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SimpleProductsFragment extends Fragment {

    RecyclerView recyclerView;

    // TODO remove dummy data
//    String[] products, calories;


    SimpleProductsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_products,container,false);

        recyclerView = view.findViewById(R.id.simpleProductsRecyclerView);

//        products = view.getResources().getStringArray(R.array.simpleProducts);
//        calories = view.getResources().getStringArray(R.array.simpleProductsCalories);
        final List<SimpleProduct> spList = new ArrayList<>();

        adapter = new SimpleProductsAdapter(this.getContext(), spList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));





        Call<List<SimpleProduct>> call = Connector.getInstance().getSimpleProducts();
        call.enqueue(new Callback<List<SimpleProduct>>() {
            @Override
            public void onResponse(Call<List<SimpleProduct>> call, Response<List<SimpleProduct>> response) {
                List<SimpleProduct> spList1 = new ArrayList<>();
                if(!response.isSuccessful()){
                    Log.i("App", Integer.toString(response.code()));

                }else {

                    spList.addAll(response.body());
                }
                adapter.notifyItemInserted(0);
                Log.i("App", response.toString());




            }

            @Override
            public void onFailure(Call<List<SimpleProduct>> call, Throwable t) {
                List<SimpleProduct> spList1 = new ArrayList<>();
                spList.add(0, new SimpleProduct(12, t.getMessage(), 21.3, 42.8, 42.3, 16.7));
                adapter.notifyItemInserted(0);
                Log.i("App", t.getMessage());
            }
        });

        return view;
    }
}