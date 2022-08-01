package com.astix.rajtraderssfasales.model;

import java.util.ArrayList;

public class StoreSummaryModel {
    private String storeID, StoreName, OwnerName, CustomerType, StoreCode, Distributor;
    TblGetPDAStoreSummary storeTargetModel = new TblGetPDAStoreSummary();
    ArrayList<TblGetPDAStoreVisitHistory> storeVisitHistoryArrayList = new ArrayList<>();
    ArrayList<TblGetPDAStoreVisitHistory> tcOrdersArrayList = new ArrayList<>();


    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }

    public String getStoreCode() {
        return StoreCode;
    }

    public void setStoreCode(String storeCode) {
        StoreCode = storeCode;
    }

    public String getDistributor() {
        return Distributor;
    }

    public void setDistributor(String distributor) {
        Distributor = distributor;
    }

    public TblGetPDAStoreSummary getStoreTargetModel() {
        return storeTargetModel;
    }

    public void setStoreTargetModel(TblGetPDAStoreSummary storeTargetModel) {
        this.storeTargetModel = storeTargetModel;
    }

    public ArrayList<TblGetPDAStoreVisitHistory> getStoreVisitHistoryArrayList() {
        return storeVisitHistoryArrayList;
    }

    public void setStoreVisitHistoryArrayList(ArrayList<TblGetPDAStoreVisitHistory> storeVisitHistoryArrayList) {
        this.storeVisitHistoryArrayList = storeVisitHistoryArrayList;
    }

    public ArrayList<TblGetPDAStoreVisitHistory> getTcOrdersArrayList() {
        return tcOrdersArrayList;
    }

    public void setTcOrdersArrayList(ArrayList<TblGetPDAStoreVisitHistory> tcOrdersArrayList) {
        this.tcOrdersArrayList = tcOrdersArrayList;
    }
}
