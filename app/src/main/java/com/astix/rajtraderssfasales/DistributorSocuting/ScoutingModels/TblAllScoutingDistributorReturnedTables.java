package com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TblAllScoutingDistributorReturnedTables {


    @SerializedName("tblPotentialDistributor")
    @Expose
    private List<TblPotentialDistributor> tblPotentialDistributorList = null;

    @SerializedName("tblCompetitorCompany")
    @Expose
    private List<TblPotentialDistributorCompetitorCompanyMstr> tblCompetitorCompanyDetailsList = null;

    @SerializedName("tblCompetitorBrand")
    @Expose
    private List<TblPotentialDistributorCompetitorBrandMstr> tblCompetitorBrandDetailsList = null;


    @SerializedName("tblCompetitorCompanyMstr")
    @Expose
    private List<TblPotentialDistributorCompetitorCompanyMstr> tblCompetitorCompanyMstr = null;

    @SerializedName("tblCompetitorBrandMstr")
    @Expose
    private List<TblPotentialDistributorCompetitorBrandMstr> tblCompetitorBrandMstrList = null;

    @SerializedName("tblVehicalType")
    @Expose
    private List<TblPotentialDistributorVehicleMasterList> tblVehicalTypeMstrList = null;


    @SerializedName("tblPotentialDistributorRetailersFeedBackDetails")
    @Expose
    private List<TblPotentialDistributorRetailersFeedBackDetails> tblPotentialDistributorRetailersFeedBackDetails = null;

    public List<TblPotentialDistributor> getTblPotentialDistributorList() {
        return tblPotentialDistributorList;
    }

    public List<TblPotentialDistributorRetailersFeedBackDetails> getTblPotentialDistributorRetailersFeedBackDetails() {
        return tblPotentialDistributorRetailersFeedBackDetails;
    }

    public void setTblPotentialDistributorRetailersFeedBackDetails(List<TblPotentialDistributorRetailersFeedBackDetails> tblPotentialDistributorRetailersFeedBackDetails) {
        this.tblPotentialDistributorRetailersFeedBackDetails = tblPotentialDistributorRetailersFeedBackDetails;
    }

    public void setTblPotentialDistributorList(List<TblPotentialDistributor> tblPotentialDistributorList) {
        this.tblPotentialDistributorList = tblPotentialDistributorList;
    }

    public List<TblPotentialDistributorCompetitorCompanyMstr> getTblCompetitorCompanyDetailsList() {
        return tblCompetitorCompanyDetailsList;
    }

    public void setTblCompetitorCompanyDetailsList(List<TblPotentialDistributorCompetitorCompanyMstr> tblCompetitorCompanyDetailsList) {
        this.tblCompetitorCompanyDetailsList = tblCompetitorCompanyDetailsList;
    }

    public List<TblPotentialDistributorCompetitorBrandMstr> getTblCompetitorBrandDetailsList() {
        return tblCompetitorBrandDetailsList;
    }

    public void setTblCompetitorBrandDetailsList(List<TblPotentialDistributorCompetitorBrandMstr> tblCompetitorBrandDetailsList) {
        this.tblCompetitorBrandDetailsList = tblCompetitorBrandDetailsList;
    }

    public List<TblPotentialDistributorCompetitorCompanyMstr> getTblCompetitorCompanyMstr() {
        return tblCompetitorCompanyMstr;
    }

    public void setTblCompetitorCompanyMstr(List<TblPotentialDistributorCompetitorCompanyMstr> tblCompetitorCompanyMstr) {
        this.tblCompetitorCompanyMstr = tblCompetitorCompanyMstr;
    }

    public List<TblPotentialDistributorCompetitorBrandMstr> getTblCompetitorBrandMstrList() {
        return tblCompetitorBrandMstrList;
    }

    public void setTblCompetitorBrandMstrList(List<TblPotentialDistributorCompetitorBrandMstr> tblCompetitorBrandMstrList) {
        this.tblCompetitorBrandMstrList = tblCompetitorBrandMstrList;
    }

    public List<TblPotentialDistributorVehicleMasterList> getTblVehicalTypeMstrList() {
        return tblVehicalTypeMstrList;
    }

    public void setTblVehicalTypeMstrList(List<TblPotentialDistributorVehicleMasterList> tblVehicalTypeMstrList) {
        this.tblVehicalTypeMstrList = tblVehicalTypeMstrList;
    }
}
