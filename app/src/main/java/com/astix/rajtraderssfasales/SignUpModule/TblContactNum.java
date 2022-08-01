package com.astix.rajtraderssfasales.SignUpModule;

import com.google.gson.annotations.SerializedName;

public class TblContactNum {

    String ContactNo;

    String OTP;

    int flgStep;

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

    public int getFlgStep() {
        return flgStep;
    }

    public void setFlgStep(int flgStep) {
        this.flgStep = flgStep;
    }

    @Override
    public String toString() {
        return "TblContactNum{" +
                "ContactNo='" + ContactNo + '\'' +
                ", OTP='" + OTP + '\'' +
                ", flgStep=" + flgStep +
                '}';
    }
}
