package com.astix.rajtraderssfasales.model;

import java.util.ArrayList;
import java.util.List;

public class ActualVisitProductInfo {

    private String productName;
    private String storeId;
    private String productId;
    private int sStat;
    private String stock;
    private int unitType, isDefaultProduct;
    private String displayUnit;


    public ActualVisitProductInfo(String storeId, String productId, int sStat, String stock, int isDefaultProduct, String displayUnit, int unitType) {
        this.storeId = storeId;
        this.productId = productId;
        this.sStat = sStat;
        this.stock = stock;
        this.isDefaultProduct = isDefaultProduct;
        this.displayUnit = displayUnit;
        this.unitType = unitType;
    }

    public ActualVisitProductInfo(String productName, String productId, String displayUnit,int unitType) {
        this.productName = productName;
        this.productId = productId;
        this.displayUnit = displayUnit;
        this.unitType=unitType;
    }

    List<TblUOMMaster> tblUOMMasterList = new ArrayList<>();

    public List<TblUOMMaster> getTblUOMMasterList() {
        return tblUOMMasterList;
    }

    public void setTblUOMMasterList(List<TblUOMMaster> tblUOMMasterList) {
        this.tblUOMMasterList = tblUOMMasterList;
    }

    public void setIsDefaultProduct(int isDefaultProduct) {
        this.isDefaultProduct = isDefaultProduct;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getProductName() {
        return productName;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getProductId() {
        return productId;
    }

    public int getsStat() {
        return sStat;
    }

    public String getStock() {
        return stock;
    }

    public int getIsDefaultProduct() {
        return isDefaultProduct;
    }

    public String getDisplayUnit() {
        return displayUnit;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public void setDisplayUnit(String displayUnit) {
        this.displayUnit = displayUnit;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    @Override
    public String toString() {
        return "ActualVisitProductInfo{" +
                "productName='" + productName + '\'' +
                ", storeId='" + storeId + '\'' +
                ", productId='" + productId + '\'' +
                ", sStat=" + sStat +
                ", stock='" + stock + '\'' +
                ", unitType=" + unitType +
                ", isDefaultProduct=" + isDefaultProduct +
                ", displayUnit='" + displayUnit + '\'' +
                '}';
    }
}
