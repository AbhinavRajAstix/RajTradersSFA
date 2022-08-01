package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblProductLevelData {

    @SerializedName("StoreID")
    @Expose
    private int StoreID;
    @SerializedName("OrderID")
    @Expose
    private int OrderID;
    @SerializedName("PrdNodeId")
    @Expose
    private int PrdNodeId;
    @SerializedName("InvDate")
    @Expose
    private String InvDate;
    @SerializedName("OrderValue")
    @Expose
    private double OrderValue;
    @SerializedName("Qty(cases)")
    @Expose
    private double Qty;

    private String PrdName;

    public String getPrdName() {
        return PrdName;
    }

    public void setPrdName(String prdName) {
        PrdName = prdName;
    }

    public int getStoreID() {
        return StoreID;
    }

    public void setStoreID(int storeID) {
        StoreID = storeID;
    }

    public int getPrdNodeId() {
        return PrdNodeId;
    }

    public void setPrdNodeId(int prdNodeId) {
        PrdNodeId = prdNodeId;
    }

    public String getInvDate() {
        return InvDate;
    }

    public void setInvDate(String invDate) {
        InvDate = invDate;
    }

    public double getOrderValue() {
        return OrderValue;
    }

    public void setOrderValue(double orderValue) {
        OrderValue = orderValue;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public double getQty() {
        return Qty;
    }

    public void setQty(double qty) {
        Qty = qty;
    }
}
