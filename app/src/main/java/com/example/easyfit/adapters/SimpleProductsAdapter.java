package com.example.easyfit.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyfit.R;
import com.example.easyfit.activities.AddEatenProductActivity;
import com.example.easyfit.activities.ChooseIngredientActivity;
import com.example.easyfit.apiConnector.SimpleProduct;
import com.example.easyfit.clickListeners.ItemClickListener;
import com.example.easyfit.dialogs.QuantityDialog;
import com.example.easyfit.dialogs.SimpleProductDialog;

import java.util.List;

public class SimpleProductsAdapter extends RecyclerView.Adapter<SimpleProductsAdapter.SimpleProductsHolder> {

    Context context;
    List<SimpleProduct> products;
    Activity currentActivity;

    public SimpleProductsAdapter(Context ct, List<SimpleProduct> simpleProducts, Activity activity){
        this.context = ct;
        this.products = simpleProducts;
        this.currentActivity = activity;
    }

    public SimpleProductsAdapter(Context ct, List<SimpleProduct> simpleProducts){
        this(ct,simpleProducts,null);
    }

    @NonNull
    @Override
    public SimpleProductsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.simple_product, viewGroup, false);
        return new SimpleProductsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleProductsHolder simpleProductsHolder, int i) {
        simpleProductsHolder.name.setText(products.get(i).getName());
        simpleProductsHolder.calories.setText(Double.toString(products.get(i).getKcal()));


        if (currentActivity != null && (currentActivity.getClass() == AddEatenProductActivity.class || currentActivity.getClass() == ChooseIngredientActivity.class)) {
            simpleProductsHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    //Toast.makeText(context, "clicked in adding" + position, Toast.LENGTH_SHORT).show();

                    boolean ifAddingProduct = true;
                    if(currentActivity.getClass() == ChooseIngredientActivity.class)
                        ifAddingProduct = false;
                    addingProductAction(position, ifAddingProduct);
                }
            });
        }
        else{
            simpleProductsHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    showDetails(position);
//                    Toast.makeText(context, "clicked in products" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showDetails(int position) {
        SimpleProductDialog dialog = new SimpleProductDialog();
        dialog.setProduct(products.get(position));
        dialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "SIMPLE_PRODUCT_DIALOG");
    }

    private void addingProductAction(int position, boolean ifAddingProduct) {
        QuantityDialog dialog = new QuantityDialog();
        dialog.setProduct(products.get(position));
        dialog.setCurrentActivity(currentActivity);
        if(!ifAddingProduct)
            dialog.setIntention("addMealIngredient");
        dialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "QUANTITY_DIALOG");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class SimpleProductsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name, calories;
        private ItemClickListener itemClickListener;

        public SimpleProductsHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.simpleProductName);
            calories = itemView.findViewById(R.id.simpleProductCalories);

            itemView.setOnClickListener(this);

        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }
}
