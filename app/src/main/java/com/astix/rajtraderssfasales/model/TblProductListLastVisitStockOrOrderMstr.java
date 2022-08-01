
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblProductListLastVisitStockOrOrderMstr {

    @SerializedName("StoreID")
    @Expose
    private int storeID;
    @SerializedName("OrderDate")
    @Expose
    private String orderDate;
    @SerializedName("PrdID")
    @Expose
    private int prdID;
    @SerializedName("OrderQty")
    @Expose
    private int orderQty;
    @SerializedName("StockQty")
    @Expose
    private int stockQty;
    @SerializedName("OrderValue")
    @Expose
    private double OrderValue;

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getPrdID() {
        return prdID;
    }

    public void setPrdID(int prdID) {
        this.prdID = prdID;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public int getStockQty() {
        return stockQty;
    }

    public double getOrderValue() {
        return OrderValue;
    }

    public void setOrderValue(double orderValue) {
        OrderValue = orderValue;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

}
