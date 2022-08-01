
package com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblServerEntryStatus {

    @SerializedName("flgStatus")
    @Expose
    private int flgStatus;

    public int getFlgStatus() {
        return flgStatus;
    }

    public void setFlgStatus(int flgStatus) {
        this.flgStatus = flgStatus;
    }
}
