package com.example.easyfit.apiConnector;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("EMAIL")
    private String email;

    @SerializedName("PASSWORD")
    private String password;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }


}
