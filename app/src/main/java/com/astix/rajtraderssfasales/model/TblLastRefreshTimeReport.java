
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblLastRefreshTimeReport {

    @SerializedName("LastProcessTime")
    @Expose
    private String LastRefreshTime;

    public String getLastRefreshTime() {
        return LastRefreshTime;
    }

    public void setLastRefreshTime(String lastRefreshTime) {
        LastRefreshTime = lastRefreshTime;
    }
}
