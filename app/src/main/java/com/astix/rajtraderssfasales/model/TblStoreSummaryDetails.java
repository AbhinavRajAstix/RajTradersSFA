
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblStoreSummaryDetails {
//(StoreID text null,OrderID text,VisitID integer,InvDate text,ProductID integer,OrderQty int null, NetLiveOrderVal text null,NetOrderValue text null);";
    @SerializedName("StoreID")
    @Expose
    private String StoreID;

    @SerializedName("OrderID")
    @Expose
    private String OrderID;

    @SerializedName("VisitID")
    @Expose
    private int VisitID;

    @SerializedName("ProductID")
    @Expose
    private int ProductID;


    @SerializedName("InvDate")
    @Expose
    private String InvDate;

    @SerializedName("OrderQty")
    @Expose
    private double OrderQty;

    @SerializedName("NetOrderValue")
    @Expose
    private String NetOrderValue;


    @SerializedName("NetLineOrderVal")
    @Expose
    private String NetLineOrderVal;

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public int getVisitID() {
        return VisitID;
    }

    public void setVisitID(int visitID) {
        VisitID = visitID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public double getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(double orderQty) {
        OrderQty = orderQty;
    }

    public String getNetOrderValue() {
        return NetOrderValue;
    }

    public void setNetOrderValue(String netOrderValue) {
        NetOrderValue = netOrderValue;
    }

    public String getNetLineOrderVal() {
        return NetLineOrderVal;
    }

    public void setNetLineOrderVal(String netLineOrderVal) {
        NetLineOrderVal = netLineOrderVal;
    }

    public String getInvDate() {
        return InvDate;
    }

    public void setInvDate(String invDate) {
        InvDate = invDate;
    }
}
