package com.example.easyfit.dataManager;

import android.text.format.Time;
import android.util.Log;

import com.example.easyfit.adapters.MealsAdapter;
import com.example.easyfit.adapters.SimpleProductsAdapter;
import com.example.easyfit.apiConnector.ComplexMeal;
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
    private static List<ComplexMeal> complexMeals;
    private Time timeSimpleProducts;
    private Time timeComplexMeals;
    private SimpleProductsAdapter simpleProductsAdapter;
    private MealsAdapter complexMealsAdapter;
    private boolean firstDownloadedSimpleProductsFlag;
    private boolean firstDownloadedComplexMealsFlag;


    private DataManager() {
        simpleProducts = new ArrayList<>();
        this.getSpDataFromAPI(simpleProducts);
        complexMeals = new ArrayList<>();
        this.getCmDataFromAPI(complexMeals);
        firstDownloadedSimpleProductsFlag = false;
        firstDownloadedComplexMealsFlag = false;
        timeSimpleProducts = new Time();
        timeSimpleProducts.setToNow();
        timeComplexMeals = new Time();
        timeComplexMeals.setToNow();
    }

    public void setAdapter(SimpleProductsAdapter adapter) {
        this.simpleProductsAdapter = adapter;
    }

    public void setAdapter(MealsAdapter adapter) {
        this.complexMealsAdapter = adapter;
    }
    public SimpleProductsAdapter getSimpleProductsAdapter() {
        return simpleProductsAdapter;
    }

    public List<SimpleProduct> getSimpleProducts(){
        return simpleProducts;
    }

    public List<ComplexMeal> getComplexMeals(){
        return complexMeals;
    }

    public static synchronized DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }

    void getSpDataFromAPI(final List<SimpleProduct> spList){
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
                firstDownloadedSimpleProductsFlag = true;
            }

            @Override
            public void onFailure(Call<List<SimpleProduct>> call, Throwable t) {
                Log.i("App", t.getMessage());
            }
        });

    }

    void getCmDataFromAPI(final List<ComplexMeal> spList){
        Call<List<ComplexMeal>> call = Connector.getInstance().getComplexMeals();
        call.enqueue(new Callback<List<ComplexMeal>>() {
            @Override
            public void onResponse(Call<List<ComplexMeal>> call, Response<List<ComplexMeal>> response) {
                if(!response.isSuccessful()){
                    Log.i("App", Integer.toString(response.code()));

                }else {
                    spList.clear();
                    spList.addAll(response.body());
                }
//
                if(!(complexMealsAdapter ==null)){
                    complexMealsAdapter.notifyDataSetChanged();
                }
                Log.i("App", response.toString());
                firstDownloadedComplexMealsFlag = true;
            }

            @Override
            public void onFailure(Call<List<ComplexMeal>> call, Throwable t) {
                Log.i("App", t.getMessage());
            }
        });

    }





    public void synchronizeSimpleProducts(boolean forceSynchronisation){
        Time currentTime = new Time(); currentTime.setToNow();
        Log.i("App","synchronize request"+ Long.toString(timeSimpleProducts.toMillis(false) + 10*1000) + " " +  Long.toString(currentTime.toMillis(false)));

        if(forceSynchronisation || (!firstDownloadedSimpleProductsFlag) || (timeSimpleProducts.toMillis(false) + 60*1000) < currentTime.toMillis(false)){
            Log.i("App","update products");
            timeSimpleProducts.setToNow();
            this.getSpDataFromAPI(getSimpleProducts());
        }
    }

    public void synchronizeComplexMeals(boolean forceSynchronisation){
        Time currentTime = new Time(); currentTime.setToNow();
        Log.i("App","synchronize request"+ Long.toString(timeComplexMeals.toMillis(false) + 10*1000) + " " +  Long.toString(currentTime.toMillis(false)));

        if(forceSynchronisation || (!firstDownloadedComplexMealsFlag) || (timeComplexMeals.toMillis(false) + 60*1000) < currentTime.toMillis(false)){
            Log.i("App","update meals");
            timeComplexMeals.setToNow();
            getCmDataFromAPI(getComplexMeals());
        }
    }




}
