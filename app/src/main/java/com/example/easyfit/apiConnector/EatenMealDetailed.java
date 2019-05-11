package com.example.easyfit.apiConnector;

import com.google.gson.annotations.SerializedName;

public class EatenMealDetailed {
    @SerializedName("SIMPLEPRODUCT")
    private SimpleProduct simpleProduct;

    @SerializedName("QUANTITY")
    private double quantity;

    public SimpleProduct getSimpleProduct() {
        return simpleProduct;
    }

    public double getQuantity() {
        return quantity;
    }
}
