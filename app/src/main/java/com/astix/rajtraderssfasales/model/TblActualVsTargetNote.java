
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblActualVsTargetNote {


    public String getMsgToDisplay() {
        return msgToDisplay;
    }

    public void setMsgToDisplay(String msgToDisplay) {
        this.msgToDisplay = msgToDisplay;
    }

    @SerializedName("TotalStores")
    @Expose
    private String msgToDisplay;

   
}
