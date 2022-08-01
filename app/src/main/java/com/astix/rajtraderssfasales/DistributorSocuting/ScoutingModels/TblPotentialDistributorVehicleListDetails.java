
package com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPotentialDistributorVehicleListDetails {

    @SerializedName("NodeID")
    @Expose
    private String NodeID;
    @SerializedName("NodeType")
    @Expose
    private int NodeType=140;
    @SerializedName("VehicleTypeID")
    @Expose
    private int VehicleTypeID;

    @SerializedName("VehicleType")
    @Expose
    private String VehicleType;

    @SerializedName("No Of Vehicles")
    @Expose
    private int NoOfVehicles;

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

    public int getVehicleTypeID() {
        return VehicleTypeID;
    }

    public void setVehicleTypeID(int vehicleTypeID) {
        VehicleTypeID = vehicleTypeID;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public int getNoOfVehicles() {
        return NoOfVehicles;
    }

    public void setNoOfVehicles(int noOfVehicles) {
        NoOfVehicles = noOfVehicles;
    }

    @Override
    public String toString() {
        return "TblPotentialDistributorVehicleListDetails{" +
                "NodeID='" + NodeID + '\'' +
                ", NodeType=" + NodeType +
                ", VehicleTypeID=" + VehicleTypeID +
                ", VehicleType=" + VehicleType +
                ", NoOfVehicles=" + NoOfVehicles +
                '}';
    }
}
