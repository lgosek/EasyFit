package com.example.easyfit.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.easyfit.R;
import com.example.easyfit.apiConnector.SimpleProduct;

public class SimpleProductDialog extends DialogFragment {

    private SimpleProduct product;
    TextView caloriesText, carbsText, protsText, fatText;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_simple_product, null);
        builder.setView(view);

        caloriesText = view.findViewById(R.id.simpleProductDialogCalories);
        carbsText = view.findViewById(R.id.simpleProductDialogCarbs);
        protsText = view.findViewById(R.id.simpleProductDialogProteins);
        fatText = view.findViewById(R.id.simpleProductDialogFat);

        builder.setTitle(product.getName());
        caloriesText.setText(product.getKcal()+"");
        carbsText.setText(product.getCarbohydrates()+"");
        protsText.setText(product.getProteins()+"");
        fatText.setText(product.getFats()+"");

        builder.setNegativeButton(getResources().getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    public void setProduct(SimpleProduct product) {
        this.product = product;
    }
}
