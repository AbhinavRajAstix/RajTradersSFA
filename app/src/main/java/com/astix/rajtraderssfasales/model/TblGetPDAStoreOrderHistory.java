package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblGetPDAStoreOrderHistory {

    @SerializedName("StoreID")
    @Expose
    private int storeID;

    @SerializedName("OrderID")
    @Expose
    private int OrderID;

    @SerializedName("flgOrderSource")
    @Expose
    private int flgOrderSource;

    @SerializedName("InvDate")
    @Expose
    private String InvDate;

    @SerializedName("OrderValue")
    @Expose
    private String orderValue;

    @SerializedName("Qty(cases)")
    @Expose
    private double orderQty;

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getFlgOrderSource() {
        return flgOrderSource;
    }

    public void setFlgOrderSource(int flgOrderSource) {
        this.flgOrderSource = flgOrderSource;
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
}
