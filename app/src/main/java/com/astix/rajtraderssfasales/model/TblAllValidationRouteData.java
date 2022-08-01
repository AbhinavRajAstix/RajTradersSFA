package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TblAllValidationRouteData {


    @SerializedName("tblPDARouteChangeApproval")
    List<TblPDARouteChangeApproval> tblPDARouteChangeApprovals;

    public List<TblPDARouteChangeApproval> getTblPDARouteChangeApprovals() {
        return tblPDARouteChangeApprovals;
    }

    public void setTblPDARouteChangeApprovals(List<TblPDARouteChangeApproval> tblPDARouteChangeApprovals) {
        this.tblPDARouteChangeApprovals = tblPDARouteChangeApprovals;
    }
}
