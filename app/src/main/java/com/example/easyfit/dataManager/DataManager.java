package com.example.easyfit.dataManager;

import android.text.format.Time;
import android.util.Log;

import com.example.easyfit.adapters.SimpleProductsAdapter;
import com.example.easyfit.apiConnector.Connector;
import com.example.easyfit.apiConnector.SimpleProduct;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataManager {
    private static DataManager instance;
    private static List<SimpleProduct> simpleProducts;
    private Time time;

    private DataManager() {
        simpleProducts = new ArrayList<>();
        time = new Time();   time.setToNow();
    }

    public static synchronized DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }
    void synchronize(final List<SimpleProduct> spList, final SimpleProductsAdapter adapter){
        Call<List<SimpleProduct>> call = Connector.getInstance().getSimpleProducts();
        call.enqueue(new Callback<List<SimpleProduct>>() {
            @Override
            public void onResponse(Call<List<SimpleProduct>> call, Response<List<SimpleProduct>> response) {
                if(!response.isSuccessful()){
                    Log.i("App", Integer.toString(response.code()));

                }else {
                    spList.clear();
                    spList.addAll(response.body());
                }
                adapter.notifyDataSetChanged();
                Log.i("App", response.toString());




            }

            @Override
            public void onFailure(Call<List<SimpleProduct>> call, Throwable t) {
                Log.i("App", t.getMessage());
            }
        });

    }

    public List<SimpleProduct> getSimpleProducts(){
        return simpleProducts;
    }

    public void synchronizeSimpleProducts(SimpleProductsAdapter adapter){
        Time currentTime = new Time(); currentTime.setToNow();
        Log.i("App","synchronize request"+ Long.toString(time.toMillis(false) + 10*1000) + " " +  Long.toString(currentTime.toMillis(false)));

        if(getSimpleProducts().isEmpty() || (time.toMillis(false) + 10*1000) < currentTime.toMillis(false)){
            Log.i("App","update products");
            time = currentTime;
            synchronize(getSimpleProducts(),adapter);
        }
    }
}
