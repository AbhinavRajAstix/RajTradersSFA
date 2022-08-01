
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblValueVolumeTarget {


    @SerializedName("Descr")
    @Expose
    private String descr;

    @SerializedName("TodayTarget")
    @Expose
    private double todayTarget;


    @SerializedName("TodayAchieved")
    @Expose
    private int todayAchieved;

    @SerializedName("TodayBal")
    @Expose
    private Float todayBal;

    @SerializedName("Todayflg")
    @Expose
    private int todayflg;

    @SerializedName("MonthTarget")
    @Expose
    private Float monthTarget;

    @SerializedName("MonthAchieved")
    @Expose
    private Float monthAchieved;

    @SerializedName("MonthBal")
    @Expose
    private Float monthBal;


    @SerializedName("Monthflg")
    @Expose
    private Float monthflg;


    @SerializedName("flgLevel")
    @Expose
    private int flgLevel;

    @SerializedName("flgStyleBold")
    @Expose
    private int flgStyleBold;

    @SerializedName("IsStoreLevel")
    @Expose
    private int IsStoreLevel;

    public int getIsStoreLevel() {
        return IsStoreLevel;
    }

    public void setIsStoreLevel(int isStoreLevel) {
        IsStoreLevel = isStoreLevel;
    }

    public int getFlgLevel() {
        return flgLevel;
    }

    public void setFlgLevel(int flgLevel) {
        this.flgLevel = flgLevel;
    }

    public int getFlgStyleBold() {
        return flgStyleBold;
    }

    public void setFlgStyleBold(int flgStyleBold) {
        this.flgStyleBold = flgStyleBold;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;

    }

    public double getTodayTarget() {
        return todayTarget;
    }

    public void setTodayTarget(double todayTarget) {
        this.todayTarget = todayTarget;

    }

    public int getTodayAchieved() {
        return todayAchieved;
    }

    public void setTodayAchieved(int todayAchieved) {
        this.todayAchieved = todayAchieved;

    }

    public Float getTodayBal() {
        return todayBal;
    }

    public void setTodayBal(Float todayBal) {
        this.todayBal = todayBal;

    }

    public int getTodayflg() {
        return todayflg;
    }

    public void setTodayflg(int todayflg) {
        this.todayflg = todayflg;

    }

    public Float getMonthTarget() {
        return monthTarget;
    }

    public void setMonthTarget(Float monthTarget) {
        this.monthTarget = monthTarget;

    }

    public Float getMonthAchieved() {
        return monthAchieved;
    }

    public void setMonthAchieved(Float monthAchieved) {
        this.monthAchieved = monthAchieved;

    }

    public Float getMonthBal() {
        return monthBal;
    }

    public void setMonthBal(Float monthBal) {
        this.monthBal = monthBal;

    }

    public Float getMonthflg() {
        return monthflg;
    }

    public void setMonthflg(Float monthflg) {
        this.monthflg = monthflg;

    }

   


}
