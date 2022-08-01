
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusInfo {



    @SerializedName("PDACode")
    @Expose
    private String iMEINo;

    @Override
    public String toString() {
        return "StatusInfo{" +
                "iMEINo='" + iMEINo + '\'' +
                ", AppVersionNo='" + AppVersionNo + '\'' +
                '}';
    }

    @SerializedName("AppVersionNo")
    @Expose
    private String AppVersionNo;

    public String getAppVersionNo() {
        return AppVersionNo;
    }

    public void setAppVersionNo(String appVersionNo) {
        AppVersionNo = appVersionNo;
    }

    public String getIMEINo() {
        return iMEINo;
    }

    public void setIMEINo(String iMEINo) {
        this.iMEINo = iMEINo;
    }

}
