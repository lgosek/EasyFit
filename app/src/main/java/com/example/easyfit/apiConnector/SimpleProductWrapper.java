package com.example.easyfit.apiConnector;

import com.google.gson.annotations.Expose;

import java.util.List;

public class SimpleProductWrapper {

    @Expose
    SimpleProduct simpleProduct;


    public void setSimpleProduct(SimpleProduct simpleProduct) {
        this.simpleProduct = simpleProduct;
    }

    public SimpleProductWrapper(SimpleProduct simpleProduct) {
        this.simpleProduct = simpleProduct;
    }
}
