package com.example.easyfit.apiConnector;

import com.google.gson.annotations.SerializedName;

public class ComplexMeal {
    @SerializedName("ID")
    private int id;

    @SerializedName("NAME")
    private String name;

    @SerializedName("KCAL")
    private double kcal;

    @SerializedName("PROTEINS")
    private double proteins;

    @SerializedName("FATS")
    private double fats;

    @SerializedName("CARBOHYDRATES")
    private double carbohydrates;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

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
