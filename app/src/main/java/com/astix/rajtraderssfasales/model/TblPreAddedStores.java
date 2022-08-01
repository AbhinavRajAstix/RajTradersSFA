
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPreAddedStores {

    @SerializedName("StoreID")
    @Expose
    private String storeID;
    @SerializedName("StoreIDDB")
    @Expose
    private Integer storeIDDB;
    @SerializedName("StoreName")
    @Expose
    private String storeName;
    @SerializedName("LatCode")
    @Expose
    private String latCode;
    @SerializedName("LongCode")
    @Expose
    private String longCode;
    @SerializedName("DateAdded")
    @Expose
    private String dateAdded;
    @SerializedName("flgRemap")
    @Expose
    private Integer flgRemap;
    @SerializedName("RouteNodeID")
    @Expose
    private Integer routeNodeID;
    @SerializedName("RouteNodeType")
    @Expose
    private Integer routeNodeType;

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public Integer getStoreIDDB() {
        return storeIDDB;
    }

    public void setStoreIDDB(Integer storeIDDB) {
        this.storeIDDB = storeIDDB;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLatCode() {
        return latCode;
    }

    public void setLatCode(String latCode) {
        this.latCode = latCode;
    }

    public String getLongCode() {
        return longCode;
    }

    public void setLongCode(String longCode) {
        this.longCode = longCode;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Integer getFlgRemap() {
        return flgRemap;
    }

    public void setFlgRemap(Integer flgRemap) {
        this.flgRemap = flgRemap;
    }

    public Integer getRouteNodeID() {
        return routeNodeID;
    }

    public void setRouteNodeID(Integer routeNodeID) {
        this.routeNodeID = routeNodeID;
    }

    public Integer getRouteNodeType() {
        return routeNodeType;
    }

    public void setRouteNodeType(Integer routeNodeType) {
        this.routeNodeType = routeNodeType;
    }

}
