package com.astix.rajtraderssfasales.model;

public class TblStoreContactNum {

    String StoreID;

    String OTP;

    String ContactNo;

    int flgStep;

    public int getFlgStep() {
        return flgStep;
    }

    public void setFlgStep(int flgStep) {
        this.flgStep = flgStep;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    @Override
    public String toString() {
        return "TblStoreContactNum{" +
                "StoreID='" + StoreID + '\'' +
                ", OTP='" + OTP + '\'' +
                ", ContactNo='" + ContactNo + '\'' +
                ", flgStep=" + flgStep +
                '}';
    }
}
