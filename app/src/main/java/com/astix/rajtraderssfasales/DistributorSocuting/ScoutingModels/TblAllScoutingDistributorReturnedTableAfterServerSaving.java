package com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TblAllScoutingDistributorReturnedTableAfterServerSaving {


    @SerializedName("tblServerEntryStatus")
    @Expose
    private List<TblServerEntryStatus> tblServerEntryStatus = null;


    public List<TblServerEntryStatus> getTblServerEntryStatus() {
        return tblServerEntryStatus;
    }

    public void setTblServerEntryStatus(List<TblServerEntryStatus> tblServerEntryStatus) {
        this.tblServerEntryStatus = tblServerEntryStatus;
    }
}
