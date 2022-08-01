
package com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPotentialDistributorVehicleMasterList {


    @SerializedName("VehicleTypeID")
    @Expose
    private int VehicleTypeID;

    @SerializedName("VehicleType")
    @Expose
    private String VehicleType;

    @SerializedName("FlgEnable")
    @Expose
    private int FlgEnable;

    @SerializedName("No Of Vehicles")
    @Expose
    private String NoOfVechile;


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

    public int getFlgEnable() {
        return FlgEnable;
    }

    public void setFlgEnable(int flgEnable) {
        FlgEnable = flgEnable;
    }

    public String getNoOfVechile() {
        return NoOfVechile;
    }

    public void setNoOfVechile(String noOfVechile) {
        NoOfVechile = noOfVechile;
    }
}
