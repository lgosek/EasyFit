package com.example.easyfit.apiConnector;

import com.google.gson.annotations.SerializedName;

public class Goals {
    @SerializedName("KCAL")
    private double kcal;

    @SerializedName("PROTEINS")
    private int proteins;

    @SerializedName("FATS")
    private int fats;

    @SerializedName("CARBOHYDRATES")
    private int carbohydrates;

    public double getKcal() {
        return kcal;
    }

    public int getProteins() {
        return proteins;
    }

    public int getFats() {
        return fats;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public Goals(double kcal, int proteins, int fats, int carbohydrates) {
        this.kcal = kcal;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }
}
