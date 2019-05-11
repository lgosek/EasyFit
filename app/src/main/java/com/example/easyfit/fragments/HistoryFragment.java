package com.example.easyfit.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.easyfit.R;
import com.example.easyfit.apiConnector.Connector;
import com.example.easyfit.apiConnector.EatenMealDetailed;
import com.example.easyfit.apiConnector.EatenProduct;
import com.example.easyfit.apiConnector.EatenProductsWrapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        List<EatenProduct> eatenProducts = new ArrayList<>();
        eatenProducts.add(new EatenProduct(1,200));

        Call<String> call = Connector.getInstance().saveEatenMeals(new EatenProductsWrapper(1,eatenProducts));
        call.enqueue(new Callback<String>(){
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("app", ""+response.code());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("app", t.getMessage());
            }
        });




        return inflater.inflate(R.layout.fragment_history,container,false);
    }
}
