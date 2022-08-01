
package com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPotentialDistributorCompetitorCompanyMstr {


    @SerializedName("CompetitorCompanyID")
    @Expose
    private int CompetitorCompanyID;


    @SerializedName("CompetitorCompany")
    @Expose
    private String CompetitorCompany;

    @SerializedName("OtherCompany")
    @Expose
    private String OtherCompany;

    @SerializedName("NodeID")
    @Expose
    private String NodeID;

    @SerializedName("NodeType")
    @Expose
    private int NodeType=140;


    @SerializedName("flgOther")
    @Expose
    private transient  int flgOther;


    @SerializedName("SalesValue_Lacs")
    @Expose
    private String SalesQty;

    @SerializedName("OtherCompanyCode")
    @Expose
    private int OtherCompanyCode;

    public void setOtherCompany(String otherCompany) {
        OtherCompany = otherCompany;
    }

    public int getOtherCompanyCode() {
        return OtherCompanyCode;
    }

    public void setOtherCompanyCode(int otherCompanyCode) {
        OtherCompanyCode = otherCompanyCode;
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

    public int getCompetitorCompanyID() {
        return CompetitorCompanyID;
    }

    public void setCompetitorCompanyID(int competitorCompanyID) {
        CompetitorCompanyID = competitorCompanyID;
    }

    public String getCompetitorCompany() {
        return CompetitorCompany;
    }

    public void setCompetitorCompany(String competitorCompany) {
        CompetitorCompany = competitorCompany;
    }

    public String getOtherCompany() {
        return OtherCompany;
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

    public String getSalesQty() {
        return SalesQty;
    }

    public void setSalesQty(String salesQty) {
        SalesQty = salesQty;
    }

    @Override
    public String toString() {
        return "TblPotentialDistributorCompetitorCompanyMstr{" +
                "CompetitorCompanyID=" + CompetitorCompanyID +
                ", CompetitorCompany='" + CompetitorCompany + '\'' +
                ", OtherCompany='" + OtherCompany + '\'' +
                ", NodeID='" + NodeID + '\'' +
                ", NodeType=" + NodeType +
                ", flgOther=" + flgOther +
                ", SalesQty='" + SalesQty + '\'' +
                ", OtherCompanyCode=" + OtherCompanyCode +
                ", flgCheck=" + flgCheck +
                '}';
    }
}
