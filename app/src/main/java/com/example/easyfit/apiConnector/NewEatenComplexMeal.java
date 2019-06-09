package com.example.easyfit.apiConnector;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewEatenComplexMeal {
    @SerializedName("MEAL_ID") // <- this will be the JSON key name
    int mealId;

    @SerializedName("QUANTITY")
    double quantity;

    @SerializedName("USER_ID")
    int userId;

    public NewEatenComplexMeal(int userId, int mealId, double quantity) {
        this.userId = userId;
        this.mealId = mealId;
        this.quantity = quantity;
    }
}
