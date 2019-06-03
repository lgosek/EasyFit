package com.example.easyfit.apiConnector;

import com.google.gson.annotations.SerializedName;

public class ComplexMealIngredients {
    @SerializedName("SIMPLEPRODUCT_ID")
    private int simpleProductId;

    @SerializedName("QUANTITY")
    private int quantity;

    public ComplexMealIngredients(int kcal, int quantity) {
        this.simpleProductId = kcal;
        this.quantity = quantity;
    }
}
