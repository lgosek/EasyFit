package com.example.easyfit.apiConnector;

import com.google.gson.annotations.SerializedName;

public class ComplexMealDetailedIngredient {
    @SerializedName("NAME")
    private String name;

    @SerializedName("QUANTITY")
    private int quantity;

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
