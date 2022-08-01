
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblDailyDateWiseSalesManTargetVsAch {
    @SerializedName("LevelNo")
    @Expose
    private int LevelNo;

    @SerializedName("RptDate")
    @Expose
    private String RptDate;

    @SerializedName("MeasureID")
    @Expose
    private int MeasureID;

    @SerializedName("Measure")
    @Expose
    private String Measure;

    @SerializedName("Target")
    @Expose
    private String Target;


    @SerializedName("Achievement")
    @Expose
    private String Achievement;

    public int getLevelNo() {
        return LevelNo;
    }

    public void setLevelNo(int levelNo) {
        LevelNo = levelNo;
    }

    public String getRptDate() {
        return RptDate;
    }

    public void setRptDate(String rptDate) {
        RptDate = rptDate;
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

    public String getTarget() {
        return Target;
    }

    public void setTarget(String target) {
        Target = target;
    }

    public String getAchievement() {
        return Achievement;
    }

    public void setAchievement(String achievement) {
        Achievement = achievement;
    }
}
