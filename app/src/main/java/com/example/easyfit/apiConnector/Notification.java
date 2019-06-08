package com.example.easyfit.apiConnector;

import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("TIME")
    String time;

    public String getTime() {
        return time;
    }

    public Notification(String time) {
        this.time = time;
    }
}
