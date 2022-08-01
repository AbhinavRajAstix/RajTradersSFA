package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.SerializedName;

public class TblBeatPlans {


    public String getmDayName() {
        return mDayName;
    }

    public void setmDayName(String mDayName) {
        this.mDayName = mDayName;
    }

    public String getmWeek1() {
        return mWeek1;
    }

    public void setmWeek1(String mWeek1) {
        this.mWeek1 = mWeek1;
    }

    public String getmWeek2() {
        return mWeek2;
    }

    public void setmWeek2(String mWeek2) {
        this.mWeek2 = mWeek2;
    }

    public String getmWeek3() {
        return mWeek3;
    }

    public void setmWeek3(String mWeek3) {
        this.mWeek3 = mWeek3;
    }

    public String getmWeek4() {
        return mWeek4;
    }

    public void setmWeek4(String mWeek4) {
        this.mWeek4 = mWeek4;
    }

    public String getmWWeek5() {
        return mWWeek5;
    }

    public void setmWWeek5(String mWWeek5) {
        this.mWWeek5 = mWWeek5;
    }

    public int getmMonthValue() {
        return mMonthValue;
    }

    public void setmMonthValue(int mMonthValue) {
        this.mMonthValue = mMonthValue;
    }

    @SerializedName("Dayname")
    private String mDayName;
    @SerializedName("Week1")
    private String mWeek1;
    @SerializedName("Week2")
    private String mWeek2;
    @SerializedName("Week3")
    private String mWeek3;

    @SerializedName("Week4")
    private String mWeek4;

    @SerializedName("Week5")
    private String mWWeek5;

    @SerializedName("Week6")
    private String mWWeek6;

    @SerializedName("MonthValue")
    private int mMonthValue;

    @SerializedName("MonthYear")
    private String MonthYear;



    public String getmWWeek6() {
        return mWWeek6;
    }

    public void setmWWeek6(String mWWeek6) {
        this.mWWeek6 = mWWeek6;
    }

    public String getMonthYear() {
        return MonthYear;
    }

    public void setMonthYear(String monthYear) {
        MonthYear = monthYear;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }



    public int getmflgAttendance() {
        return mflgAttendance;
    }

    public void setmflgAttendance(int mflgAttendance) {
        this.mflgAttendance = mflgAttendance;
    }

    public String getmPlannedSpoke() {
        return mPlannedSpoke;
    }

    public void setmPlannedSpoke(String mPlannedSpoke) {
        this.mPlannedSpoke = mPlannedSpoke;
    }

    public String getmActualVisitedSpokes() {
        return mActualVisitedSpokes;
    }

    public void setmActualVisitedSpokes(String mActualVisitedSpokes) {
        this.mActualVisitedSpokes = mActualVisitedSpokes;
    }

    public String getmPlannedStores() {
        return mPlannedStores;
    }

    public void setmPlannedStores(String mPlannedStores) {
        this.mPlannedStores = mPlannedStores;
    }

    public String getmActualVisitedStores() {
        return mActualVisitedStores;
    }

    public void setmActualVisitedStores(String mActualVisitedStores) {
        this.mActualVisitedStores = mActualVisitedStores;
    }

    public String getmColor() {
        return mcolor;
    }

    public void setmColor(String mcolor) {
        this.mcolor = mcolor;
    }

    @SerializedName("DateStr")
    private String mDate;

    @SerializedName("flgAttendance")
    private int mflgAttendance;
    private int mflg;
    @SerializedName("PlannedSpoke")
    private String mPlannedSpoke;
    @SerializedName("WeekID")
    private String WeekID;

    public int getMflg() {
        return mflg;
    }

    public void setMflg(int mflg) {
        this.mflg = mflg;
    }

    public String getWeekID() {
        return WeekID;
    }

    public void setWeekID(String weekID) {
        WeekID = weekID;
    }

    @SerializedName("ActualVisitedSpokes")
    private String mActualVisitedSpokes;

    @SerializedName("PlannedStores")
    private String mPlannedStores;
    @SerializedName("ActualVisitedStores")
    private String mActualVisitedStores;

    @SerializedName("Color")
    private String mcolor;

    @SerializedName("LegendText")
    private String legendText;

    public String getMonthAndYear() {
        return MonthAndYear;
    }

    public void setMonthAndYear(String monthAndYear) {
        MonthAndYear = monthAndYear;
    }

    public String getDayOfMonth() {
        return DayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        DayOfMonth = dayOfMonth;
    }

    private String MonthAndYear;


    private String DayOfMonth;

    public String getLegendText() {
        return legendText;
    }

    public void setLegendText(String legendText) {
        this.legendText = legendText;
    }
}
