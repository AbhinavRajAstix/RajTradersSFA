
package com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPotentialDistributorForDelete {

    @SerializedName("PDACode")
    @Expose
    private String PDACode;




    @SerializedName("NodeID")
    @Expose
    private String NodeID;
    @SerializedName("NodeType")
    @Expose
    private int NodeType=180;




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

    public String getPDACode() {
        return PDACode;
    }

    public void setPDACode(String PDACode) {
        this.PDACode = PDACode;
    }

    @Override
    public String toString() {
        return "TblPotentialDistributorForDelete{" +
                "PDACode='" + PDACode + '\'' +
                ", NodeID='" + NodeID + '\'' +
                ", NodeType=" + NodeType +
                '}';
    }
}
