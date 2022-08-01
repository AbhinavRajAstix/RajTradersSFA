package com.astix.rajtraderssfasales.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblRouteListMaster {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("RouteType")
    @Expose
    private Integer routeType;
    @SerializedName("Descr")
    @Expose
    private String descr;
    @SerializedName("Active")
    @Expose
    private Integer active;


    @SerializedName("flgApproved")
    @Expose
    private int flgApproved;


    @SerializedName("DBNodeID")
    @Expose
    private int DBNodeID;

    public int getDBNodeID() {
        return DBNodeID;
    }

    public void setDBNodeID(int DBNodeID) {
        this.DBNodeID = DBNodeID;
    }

    public int getFlgApproved() {
        return flgApproved;
    }

    public void setFlgApproved(int flgApproved) {
        this.flgApproved = flgApproved;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public Integer getRouteType() {
        return routeType;
    }

    public void setRouteType(Integer routeType) {
        this.routeType = routeType;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

}
