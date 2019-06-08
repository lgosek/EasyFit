package com.example.easyfit.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyfit.R;
import com.example.easyfit.activities.MainActivity;
import com.example.easyfit.apiConnector.Connector;
import com.example.easyfit.apiConnector.EatenProduct;
import com.example.easyfit.apiConnector.EatenProductsWrapper;
import com.example.easyfit.apiConnector.SimpleProduct;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class QuantityDialog extends DialogFragment {

    EditText quantity;

    private SimpleProduct product;
    // TODO jakieś pole na posiłek złożony
    private Activity currentActivity;
    private String intention = "addEatenProduct";

    public void setProduct(SimpleProduct product){
        this.product = product;
    }

    public void setIntention(String intention) {
        this.intention = intention;
    }

    private final String sharedPreferencesFileName = "com.example.easyfit.sharedpreferences";

    Activity activity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.quantity_dialog, null);
        builder.setView(view);

        quantity = view.findViewById(R.id.quantityDialogEditText);


        builder.setTitle(getResources().getString(R.string.quantityDialogName));

        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!quantity.getText().toString().equals("") && Integer.parseInt(quantity.getText().toString())!=0) {
                    //getActivity().finish();
                    switch(intention){
                        case "addEatenProduct":
                            addEatenProduct();
                            break;
                        case "addMealIngredient":
                            returnChosenIngredient();
                            break;
                        case "addEatenMeal":
                            addEatenMeal();
                            break;
                    }


                }
                else{
                    Toast.makeText(getContext(), "Wprowadź poprawną ilość!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    private void addEatenMeal() {
        Toast.makeText(currentActivity, "wybrano ilość " + quantity.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    private void returnChosenIngredient() {
        String quantityValue = quantity.getText().toString();
        Intent intent = new Intent();
        intent.putExtra("product", product);
        intent.putExtra("quantity", quantityValue);

        currentActivity.setResult(Activity.RESULT_OK, intent);
        currentActivity.finish();
    }

    private void addEatenProduct(){
        List<EatenProduct> eatenProducts = new ArrayList<>();
        eatenProducts.add(new EatenProduct(product.getId(),Integer.parseInt(quantity.getText().toString())));
        SharedPreferences sh = activity.getSharedPreferences(this.sharedPreferencesFileName, MODE_PRIVATE);

        Call<String> call = Connector.getInstance().saveEatenMeals(new EatenProductsWrapper(sh.getInt("loggedInId", -1),eatenProducts));
        call.enqueue(new Callback<String>(){
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("app", ""+response.code());
                currentActivity.finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("app", t.getMessage());
                currentActivity.finish();
            }
        });
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

}
