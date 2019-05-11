package com.example.easyfit.apiConnector;

import com.google.gson.annotations.Expose;

import java.util.List;

public class EatenProductsWrapper {

    @Expose
    int userId;

    @Expose
    List<EatenProduct> eatenProducts;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEatenProducts(List<EatenProduct> eatenProducts) {
        this.eatenProducts = eatenProducts;
    }

    public EatenProductsWrapper(int userId, List<EatenProduct> eatenProducts) {
        this.userId = userId;
        this.eatenProducts = eatenProducts;
    }
}
