
package com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPotentialDistributorCompetitorsDetails {

    @SerializedName("NodeID")
    @Expose
    private String NodeID;
    @SerializedName("NodeType")
    @Expose
    private int NodeType=140;
    @SerializedName("CompetitorCompanyID")
    @Expose
    private int CompetitorCompanyID;

    @SerializedName("OtherCompany")
    @Expose
    private String OtherCompany;

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

    public int getCompetitorCompanyID() {
        return CompetitorCompanyID;
    }

    public void setCompetitorCompanyID(int competitorCompanyID) {
        CompetitorCompanyID = competitorCompanyID;
    }

    public String getOtherCompany() {
        return OtherCompany;
    }

    public void setOtherCompany(String otherCompany) {
        OtherCompany = otherCompany;
    }

    @Override
    public String toString() {
        return "TblPotentialDistributorCompetitorsDetails{" +
                "NodeID='" + NodeID + '\'' +
                ", NodeType=" + NodeType +
                ", CompetitorCompanyID=" + CompetitorCompanyID +
                ", OtherCompany='" + OtherCompany + '\'' +
                '}';
    }
}
