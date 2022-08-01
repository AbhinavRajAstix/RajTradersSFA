
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblNoOrderReasonMaster {

    @SerializedName("NoOrderReasonID")
    @Expose
    private int reasonID;
    @SerializedName("NoOrderReason")
    @Expose
    private String resonDesc;


    public int getReasonID() {
        return reasonID;
    }

    public void setReasonID(int reasonID) {
        this.reasonID = reasonID;
    }

    public String getResonDesc() {
        return resonDesc;
    }

    public void setResonDesc(String resonDesc) {
        this.resonDesc = resonDesc;
    }
}
