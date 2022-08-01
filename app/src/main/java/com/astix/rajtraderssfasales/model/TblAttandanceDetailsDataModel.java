
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TblAttandanceDetailsDataModel {

    @SerializedName("IMEINo")
    @Expose
    private String IMEINO;

    @SerializedName("TblAttandanceDetailsList")
    @Expose
    private List<TblAttandanceDetails> tblAttandanceDetails;

    @SerializedName("TblLocationDetailsList")
    @Expose
    private List<TblLocationDetails> tblLocationDetails;

    public String getIMEINO() {
        return IMEINO;
    }

    public void setIMEINO(String IMEINO) {
        this.IMEINO = IMEINO;
    }

    public List<TblAttandanceDetails> getTblAttandanceDetails() {
        return tblAttandanceDetails;
    }

    public void setTblAttandanceDetails(List<TblAttandanceDetails> tblAttandanceDetails) {
        this.tblAttandanceDetails = tblAttandanceDetails;
    }

    public List<TblLocationDetails> getTblLocationDetails() {
        return tblLocationDetails;
    }

    public void setTblLocationDetails(List<TblLocationDetails> tblLocationDetails) {
        this.tblLocationDetails = tblLocationDetails;
    }

    @Override
    public String toString() {
        return "TblAttandanceDetailsDataModel{" +
                "IMEINO='" + IMEINO + '\'' +
                ", tblAttandanceDetails=" + tblAttandanceDetails +
                ", tblLocationDetails=" + tblLocationDetails +
                '}';
    }
}
