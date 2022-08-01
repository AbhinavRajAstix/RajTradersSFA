package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblGetPDAStoreOrderLastVisitHistory {

    @SerializedName("StoreID")
    @Expose
    private int storeID;



    @SerializedName("InvDate")
    @Expose
    private String InvDate;

    @SerializedName("OrderValue")
    @Expose
    private String orderValue;

    @SerializedName("Qty(cases)")
    @Expose
    private double orderQty;


    @SerializedName("PrdName")
    @Expose
    private String PrdName;

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public String getInvDate() {
        return InvDate;
    }

    public void setInvDate(String invDate) {
        InvDate = invDate;
    }

    public String getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(String orderValue) {
        this.orderValue = orderValue;
    }

    public double getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(double orderQty) {
        this.orderQty = orderQty;
    }

    public String getPrdName() {
        return PrdName;
    }

    public void setPrdName(String prdName) {
        PrdName = prdName;
    }
}
