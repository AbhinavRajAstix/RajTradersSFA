
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblStateCityMaster {

    @SerializedName("StateID")
    @Expose
    private int stateID;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("CityID")
    @Expose
    private int cityID;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("CityDefault")
    @Expose
    private int cityDefault;


    @SerializedName("RegionID")
    @Expose
    private int RegionID;

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCityDefault() {
        return cityDefault;
    }

    public void setCityDefault(int cityDefault) {
        this.cityDefault = cityDefault;
    }

    public int getRegionID() {
        return RegionID;
    }

    public void setRegionID(int regionID) {
        RegionID = regionID;
    }
}
