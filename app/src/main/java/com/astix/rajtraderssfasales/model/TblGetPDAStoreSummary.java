
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblGetPDAStoreSummary {

    @SerializedName("StoreID")
    @Expose
    private String storeID;

    @SerializedName("Visit Target")
    @Expose
    private int visit_target;

    @SerializedName("Monthly Target")
    @Expose
    private int monthly_target;

    @SerializedName("Achieved")
    @Expose
    private int achieved;

    @SerializedName("Balance")
    @Expose
    private int balance;

    public int getVisit_target() {
        return visit_target;
    }

    public void setVisit_target(int visit_target) {
        this.visit_target = visit_target;
    }

    public int getMonthly_target() {
        return monthly_target;
    }

    public void setMonthly_target(int monthly_target) {
        this.monthly_target = monthly_target;
    }

    public int getAchieved() {
        return achieved;
    }

    public void setAchieved(int achieved) {
        this.achieved = achieved;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }
}
