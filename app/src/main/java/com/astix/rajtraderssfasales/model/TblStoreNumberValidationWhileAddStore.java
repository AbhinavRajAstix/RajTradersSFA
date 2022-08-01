package com.astix.rajtraderssfasales.model;

public class TblStoreNumberValidationWhileAddStore {

    String PDACode;
    String StoreID;
    String ContactNo;
    String GSTNumber;

    public String getPDACode() {
        return PDACode;
    }

    public void setPDACode(String PDACode) {
        this.PDACode = PDACode;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getGSTNumber() {
        return GSTNumber;
    }

    public void setGSTNumber(String GSTNumber) {
        this.GSTNumber = GSTNumber;
    }

    @Override
    public String toString() {
        return "TblStoreNumberValidationWhileAddStore{" +
                "PDACode='" + PDACode + '\'' +
                ", StoreID=" + StoreID +
                ", ContactNo=" + ContactNo +
                ", GSTNumber='" + GSTNumber + '\'' +
                '}';
    }
}
