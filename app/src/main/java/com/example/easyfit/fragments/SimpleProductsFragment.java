package com.example.easyfit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyfit.R;
import com.example.easyfit.activities.AddEatenProductActivity;
import com.example.easyfit.activities.AddSimpleProductActivity;
import com.example.easyfit.activities.MainActivity;
import com.example.easyfit.adapters.SimpleProductsAdapter;
import com.example.easyfit.apiConnector.Connector;
import com.example.easyfit.apiConnector.SimpleProduct;
import com.example.easyfit.dataManager.DataManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SimpleProductsFragment extends Fragment {

    RecyclerView recyclerView;

    FloatingActionButton addSimpleProductButton;

    SimpleProductsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_products,container,false);

        if(getActivity().getClass() == AddEatenProductActivity.class)
            Log.i("ACTIVITY", "true");

        recyclerView = view.findViewById(R.id.simpleProductsRecyclerView);
        addSimpleProductButton = view.findViewById(R.id.simpleProductsAddButton);
        if(getActivity().getClass()== MainActivity.class) {
            addButtonOnClickHandler();
        }
        else{
            addSimpleProductButton.hide();
        }

//        products = view.getResources().getStringArray(R.array.simpleProducts);
//        calories = view.getResources().getStringArray(R.array.simpleProductsCalories);
        final List<SimpleProduct> spList = DataManager.getInstance().getSimpleProducts();

        adapter = new SimpleProductsAdapter(this.getContext(), spList, getActivity());


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        DataManager.getInstance().setAdapter(adapter);
        DataManager.getInstance().synchronizeSimpleProducts(false);




//        Call<List<SimpleProduct>> call = Connector.getInstance().getSimpleProducts();
//        call.enqueue(new Callback<List<SimpleProduct>>() {
//            @Override
//            public void onResponse(Call<List<SimpleProduct>> call, Response<List<SimpleProduct>> response) {
//                List<SimpleProduct> spList1 = new ArrayList<>();
//                if(!response.isSuccessful()){
//                    Log.i("App", Integer.toString(response.code()));
//
//                }else {
//
//                    spList.addAll(response.body());
//                }
//                adapter.notifyItemInserted(0);
//                Log.i("App", response.toString());
//
//
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<SimpleProduct>> call, Throwable t) {
//                List<SimpleProduct> spList1 = new ArrayList<>();
//                spList.add(0, new SimpleProduct(12, t.getMessage(), 21.3, 42.8, 42.3, 16.7));
//                adapter.notifyItemInserted(0);
//                Log.i("App", t.getMessage());
//            }
//        });

        return view;
    }

    private void addButtonOnClickHandler() {
        this.addSimpleProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddSimpleProductActivity.class);
                startActivity(intent);
            }
        });
    }
}
