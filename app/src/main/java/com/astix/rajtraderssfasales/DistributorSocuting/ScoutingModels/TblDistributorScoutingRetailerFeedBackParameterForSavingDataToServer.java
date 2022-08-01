package com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TblDistributorScoutingRetailerFeedBackParameterForSavingDataToServer {


    @SerializedName("tblPotentialDistributorRetailersFeedBackDetailsList")
    @Expose
    List<TblPotentialDistributorRetailersFeedBackDetails> tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving;

    public List<TblPotentialDistributorRetailersFeedBackDetails> getTblPotentialDistributorRetailersFeedBackDetailsListForServerSaving() {
        return tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving;
    }

    public void setTblPotentialDistributorRetailersFeedBackDetailsListForServerSaving(List<TblPotentialDistributorRetailersFeedBackDetails> tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving) {
        this.tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving = tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving;
    }

    @Override
    public String toString() {
        return "TblDistributorScoutingRetailerFeedBackParameterForSavingDataToServer{" +
                "tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving=" + tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving +
                '}';
    }


}
