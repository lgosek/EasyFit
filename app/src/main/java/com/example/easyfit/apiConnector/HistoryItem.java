package com.example.easyfit.apiConnector;

import com.google.gson.annotations.SerializedName;

public class HistoryItem {
    @SerializedName("DAY")
    int day;

    @SerializedName("MONTH")
    int month;

    @SerializedName("YEAR")
    int year;

    @SerializedName("KCAL")
    double kcal;

    @SerializedName("PROTEINS")
    double proteins;

    @SerializedName("CARBOHYDRATES")
    double carbohydrates;

    @SerializedName("FATS")
    double fats;

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public double getKcal() {
        return kcal;
    }

    public double getProteins() {
        return proteins;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getFats() {
        return fats;
    }

    public String getDate(){
        return ""+this.day+"."+this.month+"."+this.year;
    }

}
