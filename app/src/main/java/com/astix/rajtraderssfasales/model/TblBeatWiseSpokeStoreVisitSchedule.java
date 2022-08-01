
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.SerializedName;

public class TblBeatWiseSpokeStoreVisitSchedule {


    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmSpokeID() {
        return mSpokeID;
    }

    public void setmSpokeID(String mSpokeID) {
        this.mSpokeID = mSpokeID;
    }

    public String getmStoreID() {
        return mStoreID;
    }

    public void setmStoreID(String mStoreID) {
        this.mStoreID = mStoreID;
    }

    public int getmflgSpokeVisited() {
        return mflgSpokeVisited;
    }

    public void setnflgSpokeVisited(int mflgSpokeVisited) {
        this.mflgSpokeVisited = mflgSpokeVisited;
    }

    public int getMflgStoreVisited() {
        return mflgStoreVisited;
    }

    public void setMflgStoreVisited(int mflgStoreVisited) {
        this.mflgStoreVisited = mflgStoreVisited;
    }

    @SerializedName("Date")
    private String mDate;

    @SerializedName("SpokeID")
    private String mSpokeID;

    @SerializedName("StoreID")
    private String mStoreID;

    @SerializedName("flgSpokeVisited")
    private int mflgSpokeVisited;

    @SerializedName("flgStoreVisited")
    private int mflgStoreVisited;

    @SerializedName("flgStorePlanned")
    private int mflgStorePlanned;

    public int getMflgStorePlanned() {
        return mflgStorePlanned;
    }

    public void setMflgStorePlanned(int mflgStorePlanned) {
        this.mflgStorePlanned = mflgStorePlanned;
    }
}
