package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.SerializedName;

public class TblPDARouteChangeApproval {
    @SerializedName("flgRequestStatus")
    private int flgRequestSubmit;


    @SerializedName("ApprovarName")
    private String ApprovarName;

    @SerializedName("OldRoute")
    private String OldRoute;

    @SerializedName("NewRoute")
    private String NewRoute;

    public String getOldRoute() {
        return OldRoute;
    }

    public void setOldRoute(String oldRoute) {
        OldRoute = oldRoute;
    }

    public String getNewRoute() {
        return NewRoute;
    }

    public void setNewRoute(String newRoute) {
        NewRoute = newRoute;
    }

    private String PDACode;
   private String OTP;

    public String getApprovarName() {
        return ApprovarName;
    }

    public void setApprovarName(String approvarName) {
        ApprovarName = approvarName;
    }

    public int getFlgRequestSubmit() {
        return flgRequestSubmit;
    }

    public void setFlgRequestSubmit(int flgRequestSubmit) {
        this.flgRequestSubmit = flgRequestSubmit;
    }

    public String getPDACode() {
        return PDACode;
    }

    public void setPDACode(String PDACode) {
        this.PDACode = PDACode;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
}
