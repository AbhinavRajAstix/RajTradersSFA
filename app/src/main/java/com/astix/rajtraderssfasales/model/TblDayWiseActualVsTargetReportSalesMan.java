
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblDayWiseActualVsTargetReportSalesMan {
    @SerializedName("LevelNo")
    @Expose
    private int LevelNo;

    @SerializedName("MeasureID")
    @Expose
    private int MeasureID;

    @SerializedName("Measure")
    @Expose
    private String Measure;

    @SerializedName("MonthTgt")
    @Expose
    private String MonthTgt;


    @SerializedName("MTD_Ach")
    @Expose
    private String MTD_Ach;

    @SerializedName("CurrentDayRate")
    @Expose
    private String CurrentDayRate;

    @SerializedName("RequiredDayRate")
    @Expose
    private String RequiredDayRate;

    public int getLevelNo() {
        return LevelNo;
    }

    public void setLevelNo(int levelNo) {
        LevelNo = levelNo;
    }

    public int getMeasureID() {
        return MeasureID;
    }

    public void setMeasureID(int measureID) {
        MeasureID = measureID;
    }

    public String getMeasure() {
        return Measure;
    }

    public void setMeasure(String measure) {
        Measure = measure;
    }

    public String getMonthTgt() {
        return MonthTgt;
    }

    public void setMonthTgt(String monthTgt) {
        MonthTgt = monthTgt;
    }

    public String getMTD_Ach() {
        return MTD_Ach;
    }

    public void setMTD_Ach(String MTD_Ach) {
        this.MTD_Ach = MTD_Ach;
    }

    public String getCurrentDayRate() {
        return CurrentDayRate;
    }

    public void setCurrentDayRate(String currentDayRate) {
        CurrentDayRate = currentDayRate;
    }

    public String getRequiredDayRate() {
        return RequiredDayRate;
    }

    public void setRequiredDayRate(String requiredDayRate) {
        RequiredDayRate = requiredDayRate;
    }
}
