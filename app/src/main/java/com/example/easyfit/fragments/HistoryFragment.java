package com.example.easyfit.fragments;

import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.easyfit.R;
import com.example.easyfit.adapters.HistoryAdapter;
import com.example.easyfit.apiConnector.Connector;
import com.example.easyfit.apiConnector.EatenMealDetailed;
import com.example.easyfit.apiConnector.EatenProduct;
import com.example.easyfit.apiConnector.EatenProductsWrapper;
import com.example.easyfit.apiConnector.HistoryItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class HistoryFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    HistoryFragment thisReference = this;

    HistoryAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history,container,false);
        recyclerView = view.findViewById(R.id.historyRecyclerView);

        adapter = new HistoryAdapter(this.getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        SharedPreferences sh = getActivity().getSharedPreferences("com.example.easyfit.sharedpreferences", MODE_PRIVATE);
        int userID = sh.getInt("loggedInId", -1);

        Call<List<HistoryItem>> call = Connector.getInstance().getHistory(userID);
        call.enqueue(new Callback<List<HistoryItem>>() {
            @Override
            public void onResponse(Call<List<HistoryItem>> call, Response<List<HistoryItem>> response) {
                if(response.isSuccessful() && response.body() != null){
                    adapter.getHistoryItems().clear();
                    adapter.getHistoryItems().addAll(response.body());
                    adapter.notifyDataSetChanged();
                }else {
                    if(response.code() == 404){
                        Toast.makeText(thisReference.getContext(), "Brak historii, sprawdź jutro", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(thisReference.getContext(), "Problem z pobraniem historii", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<List<HistoryItem>> call, Throwable t) {
                Toast.makeText(thisReference.getContext(), "Problem z połączeniem",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
