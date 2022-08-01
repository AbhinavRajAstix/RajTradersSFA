
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblStoreListForUpdateMstr {

    @SerializedName("StoreID")
    @Expose
    private String StoreID;
    @SerializedName("StoreName")
    @Expose
    private String StoreName;

    @SerializedName("StoreLat")
    @Expose
    private String StoreLat;

    @SerializedName("StoreLong")
    @Expose
    private String StoreLong;

    @SerializedName("Ownername")
    @Expose
    private String Ownername;

    @SerializedName("Contactnumber")
    @Expose
    private String Contactnumber;


    @SerializedName("Address")
    @Expose
    private String Address;

    @SerializedName("Reasonforupdate")
    @Expose
    private String Reasonforupdate;

    @SerializedName("RouteNodeID")
    @Expose
    private int RouteNodeID;

    @SerializedName("RouteNodeType")
    @Expose
    private int RouteNodeType;


    private String UpdatedContactnumber;


    private int flgMapped;


    private String OTP;


    private int Sstat;

    private double Distance;
    private int flgRadioButtonSelected;

    public int getFlgRadioButtonSelected() {
        return flgRadioButtonSelected;
    }

    public void setFlgRadioButtonSelected(int flgRadioButtonSelected) {
        this.flgRadioButtonSelected = flgRadioButtonSelected;
    }

    public double getDistance() {
        return Distance;
    }

    public void setDistance(double distance) {
        Distance = distance;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getStoreLat() {
        return StoreLat;
    }

    public void setStoreLat(String storeLat) {
        StoreLat = storeLat;
    }

    public String getStoreLong() {
        return StoreLong;
    }

    public void setStoreLong(String storeLong) {
        StoreLong = storeLong;
    }

    public String getOwnername() {
        return Ownername;
    }

    public void setOwnername(String ownername) {
        Ownername = ownername;
    }

    public String getContactnumber() {
        return Contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        Contactnumber = contactnumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getReasonforupdate() {
        return Reasonforupdate;
    }

    public void setReasonforupdate(String reasonforupdate) {
        Reasonforupdate = reasonforupdate;
    }

    public int getRouteNodeID() {
        return RouteNodeID;
    }

    public void setRouteNodeID(int routeNodeID) {
        RouteNodeID = routeNodeID;
    }

    public int getRouteNodeType() {
        return RouteNodeType;
    }

    public void setRouteNodeType(int routeNodeType) {
        RouteNodeType = routeNodeType;
    }

    public String getUpdatedContactnumber() {
        return UpdatedContactnumber;
    }

    public void setUpdatedContactnumber(String updatedContactnumber) {
        UpdatedContactnumber = updatedContactnumber;
    }

    public int getFlgMapped() {
        return flgMapped;
    }

    public void setFlgMapped(int flgMapped) {
        this.flgMapped = flgMapped;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public int getSstat() {
        return Sstat;
    }

    public void setSstat(int sstat) {
        Sstat = sstat;
    }
}
