package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblSchemePerProduct {

    @SerializedName("StoreID")
    @Expose
    private String storeID;

    @SerializedName("ProductId")
    @Expose
    private String productId;

    @SerializedName("SlabId")
    @Expose
    private String SlabId;

    @SerializedName("AmtToAchieve")
    @Expose
    private String AmtToAchieve;

    @SerializedName("Discount")
    @Expose
    private String Discount;


    @SerializedName("Gap")
    @Expose
    private int Gap;

    @SerializedName("flgColor")
    @Expose
    private int flgColor;

    public int getGap() {
        return Gap;
    }

    public void setGap(int gap) {
        Gap = gap;
    }

    public int getFlgColor() {
        return flgColor;
    }

    public void setFlgColor(int flgColor) {
        this.flgColor = flgColor;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSlabId() {
        return SlabId;
    }

    public void setSlabId(String slabId) {
        SlabId = slabId;
    }

    public String getAmtToAchieve() {
        return AmtToAchieve;
    }

    public void setAmtToAchieve(String amtToAchieve) {
        AmtToAchieve = amtToAchieve;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
