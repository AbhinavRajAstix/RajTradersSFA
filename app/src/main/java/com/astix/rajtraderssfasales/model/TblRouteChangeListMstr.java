
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblRouteChangeListMstr {

    public String getReasonID() {
        return ReasonID;
    }

    public void setReasonID(String reasonID) {
        ReasonID = reasonID;
    }

    @SerializedName("ReasonID")
    @Expose
    private String ReasonID;

    public String getReasonDescr() {
        return ReasonDescr;
    }

    public void setReasonDescr(String reasonDescr) {
        ReasonDescr = reasonDescr;
    }

    @SerializedName("ReasonDescr")
    @Expose
    private String ReasonDescr;

}
