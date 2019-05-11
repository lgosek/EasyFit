package com.example.easyfit.apiConnector;

import com.google.gson.annotations.SerializedName;

public class EatenMeals {

    @SerializedName("CALORIES")
    private double kcal;

    @SerializedName("PROTEINS")
    private double proteins;

    @SerializedName("FATS")
    private double fats;

    @SerializedName("CARBOHYDRATES")
    private double carbohydrates;

    public double getKcal() {
        return kcal;
    }

    public double getProteins() {
        return proteins;
    }

    public double getFats() {
        return fats;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }
}
