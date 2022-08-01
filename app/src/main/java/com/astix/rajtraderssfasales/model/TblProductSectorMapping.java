
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblProductSectorMapping {

    @SerializedName("PrdNodeID")
    @Expose
    private int PrdNodeID;
    @SerializedName("PrdNodeType")
    @Expose
    private int PrdNodeType;
    @SerializedName("SectorID")
    @Expose
    private int SectorID;
    @SerializedName("FromDate")
    @Expose
    private String FromDate;
    @SerializedName("ToDate")
    @Expose
    private String ToDate;

    public int getPrdNodeID() {
        return PrdNodeID;
    }

    public void setPrdNodeID(int prdNodeID) {
        PrdNodeID = prdNodeID;
    }

    public int getPrdNodeType() {
        return PrdNodeType;
    }

    public void setPrdNodeType(int prdNodeType) {
        PrdNodeType = prdNodeType;
    }

    public int getSectorID() {
        return SectorID;
    }

    public void setSectorID(int sectorID) {
        SectorID = sectorID;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }
}
