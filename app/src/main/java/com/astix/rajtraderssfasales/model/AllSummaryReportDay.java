
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllSummaryReportDay {


    public List<TblAllSummaryDay> getTblAllSummaryDay() {
        return tblAllSummaryDay;
    }

    public void setTblAllSummaryDay(List<TblAllSummaryDay> tblAllSummaryDay) {
        this.tblAllSummaryDay = tblAllSummaryDay;
    }

    @SerializedName("tblAllSummaryDay")
    @Expose
    private List<TblAllSummaryDay> tblAllSummaryDay = null;


    @SerializedName("tblLastProcessedTimeReport")
    @Expose
    private List<TblLastRefreshTimeReport> tblLastRefreshTimeReport = null;


    public List<TblLastRefreshTimeReport> getTblLastRefreshTimeReport() {
        return tblLastRefreshTimeReport;
    }

    public void setTblLastRefreshTimeReport(List<TblLastRefreshTimeReport> tblLastRefreshTimeReport) {
        this.tblLastRefreshTimeReport = tblLastRefreshTimeReport;
    }
}
