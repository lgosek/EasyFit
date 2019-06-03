package com.example.easyfit.apiConnector;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NewComplexMeal {
    @SerializedName("NAME")
    private String name;

    @SerializedName("INGREDIENTS")
    private List<ComplexMealIngredients> ingredients;


    public NewComplexMeal(String name) {
        this.name = name;
        this.ingredients = new ArrayList<>();
    }

    public void addIngredients(ComplexMealIngredients ingredients){
        this.ingredients.add(ingredients);
    }


}
