package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.SerializedName;

public class TblRequestForOrderOnMainData {

    @SerializedName("PDACode")
    String PDACode;

    @SerializedName("EMailID")
    String EMailID;

    @SerializedName("OrderDate")
    String OrderDate;

    public String getPDACode() {
        return PDACode;
    }

    public void setPDACode(String PDACode) {
        this.PDACode = PDACode;
    }

    public String getEMailID() {
        return EMailID;
    }

    public void setEMailID(String EMailID) {
        this.EMailID = EMailID;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    @Override
    public String toString() {
        return "TblRequestForOrderOnMainData{" +
                "PDACode='" + PDACode + '\'' +
                ", EMailID='" + EMailID + '\'' +
                ", OrderDate='" + OrderDate + '\'' +
                '}';
    }
}
