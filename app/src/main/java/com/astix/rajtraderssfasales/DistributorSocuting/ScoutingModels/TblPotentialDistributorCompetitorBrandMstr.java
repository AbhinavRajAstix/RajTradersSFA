
package com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPotentialDistributorCompetitorBrandMstr {


    @SerializedName("CompetitorBrandID")
    @Expose
    private int CompetitorBrandID;

    @SerializedName("CompetitorBrand")
    @Expose
    private String CompetitorBrand;

    @SerializedName("OtherBrand")
    @Expose
    private String OtherBrand;

    @SerializedName("NodeID")
    @Expose
    private String NodeID;

    @SerializedName("NodeType")
    @Expose
    private int NodeType=140;


    @SerializedName("flgOther")
    @Expose
    private transient int flgOther;

    @SerializedName("SalesValue_Lacs")
    @Expose
    private transient String SalesQty;

    @SerializedName("OtherBrandCode")
    @Expose
    private int OtherBrandCode;

    public String getSalesQty() {
        return SalesQty;
    }

    public void setSalesQty(String salesQty) {
        SalesQty = salesQty;
    }

    public int getOtherBrandCode() {
        return OtherBrandCode;
    }

    public void setOtherBrandCode(int otherBrandCode) {
        OtherBrandCode = otherBrandCode;
    }

    public int getFlgOther() {
        return flgOther;
    }

    public void setFlgOther(int flgOther) {
        this.flgOther = flgOther;
    }

    private int flgCheck;

    public int getFlgCheck() {
        return flgCheck;
    }

    public void setFlgCheck(int flgCheck) {
        this.flgCheck = flgCheck;
    }

    public int getCompetitorBrandID() {
        return CompetitorBrandID;
    }

    public void setCompetitorBrandID(int competitorBrandID) {
        CompetitorBrandID = competitorBrandID;
    }

    public String getCompetitorBrand() {
        return CompetitorBrand;
    }

    public void setCompetitorBrand(String competitorBrand) {
        CompetitorBrand = competitorBrand;
    }

    public String getOtherBrand() {
        return OtherBrand;
    }

    public void setOtherBrand(String otherBrand) {
        OtherBrand = otherBrand;
    }

    public String getNodeID() {
        return NodeID;
    }

    public void setNodeID(String nodeID) {
        NodeID = nodeID;
    }

    public int getNodeType() {
        return NodeType;
    }

    public void setNodeType(int nodeType) {
        NodeType = nodeType;
    }

    @Override
    public String toString() {
        return "TblPotentialDistributorCompetitorBrandMstr{" +
                "CompetitorBrandID=" + CompetitorBrandID +
                ", CompetitorBrand='" + CompetitorBrand + '\'' +
                ", OtherBrand='" + OtherBrand + '\'' +
                ", NodeID='" + NodeID + '\'' +
                ", NodeType=" + NodeType +
                ", flgOther=" + flgOther +
                ", SalesQty='" + SalesQty + '\'' +
                ", OtherBrandCode=" + OtherBrandCode +
                ", flgCheck=" + flgCheck +
                '}';
    }
}
