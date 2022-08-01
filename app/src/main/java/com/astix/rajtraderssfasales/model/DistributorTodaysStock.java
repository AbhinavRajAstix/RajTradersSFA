package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistributorTodaysStock {

    @SerializedName("PDACode")
    @Expose
    private String iMEINo;

    @Override
    public String toString() {
        return "DistributorTodaysStock{" +
                "iMEINo='" + iMEINo + '\'' +
                ", customerNodeId=" + customerNodeId +
                ", customerNodeType=" + customerNodeType +
                ", AppVersionNo='" + AppVersionNo + '\'' +
                '}';
    }

    @SerializedName("CustomerNodeId")
    @Expose
    private int customerNodeId;

    @SerializedName("CustomerNodeType ")
    @Expose
    private int customerNodeType ;
    @SerializedName("AppVersionNo")
    @Expose
    private String AppVersionNo;

    public String getAppVersionNo() {
        return AppVersionNo;
    }

    public void setAppVersionNo(String appVersionNo) {
        AppVersionNo = appVersionNo;
    }

    public String getiMEINo() {
        return iMEINo;
    }

    public void setiMEINo(String iMEINo) {
        this.iMEINo = iMEINo;
    }

    public int getCustomerNodeId() {
        return customerNodeId;
    }

    public void setCustomerNodeId(int customerNodeId) {
        this.customerNodeId = customerNodeId;
    }

    public int getCustomerNodeType() {
        return customerNodeType;
    }

    public void setCustomerNodeType(int customerNodeType) {
        this.customerNodeType = customerNodeType;
    }


}
