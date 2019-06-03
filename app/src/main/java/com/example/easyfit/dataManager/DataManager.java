package com.example.easyfit.dataManager;

import android.os.Build;
import android.text.format.Time;
import android.util.Log;

import com.example.easyfit.adapters.EatenMealsAdapter;
import com.example.easyfit.adapters.SimpleProductsAdapter;
import com.example.easyfit.apiConnector.Connector;
import com.example.easyfit.apiConnector.EatenMealDetailed;
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
    private SimpleProductsAdapter simpleProductsAdapter;
    private boolean firstDownloadedFlag;


    private DataManager() {
        simpleProducts = new ArrayList<>();
        getDataFromAPI(simpleProducts);
        firstDownloadedFlag = false;
        time = new Time();
        time.setToNow();
    }

    public void setAdapter(SimpleProductsAdapter adapter) {
        this.simpleProductsAdapter = adapter;
    }

    public SimpleProductsAdapter getSimpleProductsAdapter() {
        return simpleProductsAdapter;
    }

    public List<SimpleProduct> getSimpleProducts(){
        return simpleProducts;
    }

    public static synchronized DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    void getDataFromAPI(final List<SimpleProduct> spList){
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
//
                if(!(simpleProductsAdapter ==null)){
                    simpleProductsAdapter.notifyDataSetChanged();
                }
                Log.i("App", response.toString());
                firstDownloadedFlag = true;
            }

            @Override
            public void onFailure(Call<List<SimpleProduct>> call, Throwable t) {
                Log.i("App", t.getMessage());
            }
        });

    }





    public void synchronizeSimpleProducts(boolean forceSynchronisation){
        Time currentTime = new Time(); currentTime.setToNow();
        Log.i("App","synchronize request"+ Long.toString(time.toMillis(false) + 10*1000) + " " +  Long.toString(currentTime.toMillis(false)));

        if(forceSynchronisation || (!firstDownloadedFlag) || (time.toMillis(false) + 60*1000) < currentTime.toMillis(false)){
            Log.i("App","update products");
            time.setToNow();
            getDataFromAPI(getSimpleProducts());
        }
    }




}
