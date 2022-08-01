
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblStoreSuggestedQtyMstr {

    @SerializedName("StoreId")
    @Expose
    private String StoreID;
    @SerializedName("PrdNodeId")
    @Expose
    private int PrdNodeId;

    @SerializedName("SuggestedQty")
    @Expose
    private int SuggestedQty;


    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public int getPrdNodeId() {
        return PrdNodeId;
    }

    public void setPrdNodeId(int prdNodeId) {
        PrdNodeId = prdNodeId;
    }

    public int getSuggestedQty() {
        return SuggestedQty;
    }

    public void setSuggestedQty(int suggestedQty) {
        SuggestedQty = suggestedQty;
    }
}
