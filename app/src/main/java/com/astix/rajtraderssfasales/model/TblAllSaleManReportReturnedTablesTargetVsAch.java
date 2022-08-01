package com.astix.rajtraderssfasales.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TblAllSaleManReportReturnedTablesTargetVsAch {
   @SerializedName("tblDateWiseSalesManTargetVsAch")
    @Expose
    private List<TblDayWiseActualVsTargetReportSalesMan> tblDateWiseSalesManTargetVsAch = null;

    public List<TblDayWiseActualVsTargetReportSalesMan> getTblDateWiseSalesManTargetVsAch() {
        return tblDateWiseSalesManTargetVsAch;
    }

    public void setTblDateWiseSalesManTargetVsAch(List<TblDayWiseActualVsTargetReportSalesMan> tblDateWiseSalesManTargetVsAch) {
        this.tblDateWiseSalesManTargetVsAch = tblDateWiseSalesManTargetVsAch;
    }

    @SerializedName("tblDailyDateWiseSalesManTargetVsAch")
    @Expose
    private List<TblDailyDateWiseSalesManTargetVsAch> tblDailyDateWiseSalesManTargetVsAchList = null;

    public List<TblDailyDateWiseSalesManTargetVsAch> getTblDailyDateWiseSalesManTargetVsAchList() {
        return tblDailyDateWiseSalesManTargetVsAchList;
    }

    public void setTblDailyDateWiseSalesManTargetVsAchList(List<TblDailyDateWiseSalesManTargetVsAch> tblDailyDateWiseSalesManTargetVsAchList) {
        this.tblDailyDateWiseSalesManTargetVsAchList = tblDailyDateWiseSalesManTargetVsAchList;
    }
}
