package com.example.easyfit.apiConnector;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EatenProduct {

    @SerializedName("simpleProductId") // <- this will be the JSON key name
    @Expose
    int productId;

    @SerializedName("quantity")
    @Expose
    double quantity;

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public EatenProduct(int productId, double quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
