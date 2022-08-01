package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblGetPDAStoreVisitHistory {

    @SerializedName("StoreID")
    @Expose
    private String storeID;

    @SerializedName("Contacted By")
    @Expose
    private String contactedBy;

    @SerializedName("LastCall/Visit")
    @Expose
    private String lastCallVisit;

    @SerializedName("OrderValue")
    @Expose
    private String orderValue;

    @SerializedName("OrderQty(Cases)")
    @Expose
    private String orderQty;

    @SerializedName("OrderStatus")
    @Expose
    private String orderStatus;

    private int flgSectinType=0;

    public int getFlgSectinType() {
        return flgSectinType;
    }

    public void setFlgSectinType(int flgSectinType) {
        this.flgSectinType = flgSectinType;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getContactedBy() {
        return contactedBy;
    }

    public void setContactedBy(String contactedBy) {
        this.contactedBy = contactedBy;
    }

    public String getLastCallVisit() {
        return lastCallVisit;
    }

    public void setLastCallVisit(String lastCallVisit) {
        this.lastCallVisit = lastCallVisit;
    }

    public String getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(String orderValue) {
        this.orderValue = orderValue;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(String orderQty) {
        this.orderQty = orderQty;
    }
}
