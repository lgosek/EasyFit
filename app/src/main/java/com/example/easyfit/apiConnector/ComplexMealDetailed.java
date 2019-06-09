package com.example.easyfit.apiConnector;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ComplexMealDetailed {
    @SerializedName("ID")
    private int id;

    @SerializedName("INGREDIANS")
    private List<ComplexMealDetailedIngredient> ingredients;

    public int getId() {
        return id;
    }

    public List<ComplexMealDetailedIngredient> getIngredients() {
        return ingredients;
    }
}
