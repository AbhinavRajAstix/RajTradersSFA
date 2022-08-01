
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataParametersModelForTargetVsAcheived {

    @SerializedName("PDACode")
    @Expose
    private String iMEINo;
    @SerializedName("VersionId")
    @Expose
    private int versionId;
    @SerializedName("AppVersionNo")
    @Expose
    private String AppVersionNo;


    @SerializedName("ApplicationTypeId")
    @Expose
    private int applicationTypeId;
    @SerializedName("ForDate")
    @Expose
    private String forDate;
    @SerializedName("RegistrationId")
    @Expose
    private String registrationId;


    @SerializedName("RouteNodeId")
    @Expose
    private int routeNodeId;


    @SerializedName("RouteNodeType")
    @Expose
    private int routeNodeType;

    @SerializedName("flgSelf")
    @Expose
    private int flgSelf;
    @SerializedName("SalesmanNodeID")
    @Expose
    private int SalesmanNodeID ;
    @SerializedName("SalesmanNodeType")
    @Expose
    private int SalesmanNodeType ;

    public int getFlgSelf() {
        return flgSelf;
    }

    public void setFlgSelf(int flgSelf) {
        this.flgSelf = flgSelf;
    }

    public int getSalesmanNodeID() {
        return SalesmanNodeID;
    }

    public void setSalesmanNodeID(int salesmanNodeID) {
        SalesmanNodeID = salesmanNodeID;
    }

    public int getSalesmanNodeType() {
        return SalesmanNodeType;
    }

    public void setSalesmanNodeType(int salesmanNodeType) {
        SalesmanNodeType = salesmanNodeType;
    }




    @SerializedName("InvoiceList")
    @Expose
    private  List<InvoiceList> invoiceList;
    @SerializedName("FlgAllRouteData")
    @Expose
    private int flgAllRouteData;
    @SerializedName("CoverageAreaNodeId")
    @Expose
    private int coverageAreaNodeId;
    @SerializedName("CoverageAreaNodeType")
    @Expose
    private int coverageAreaNodeType;

    public String getIMEINo() {
        return iMEINo;
    }

    public void setIMEINo(String iMEINo) {
        this.iMEINo = iMEINo;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public int getApplicationTypeId() {
        return applicationTypeId;
    }

    public void setApplicationTypeId(int applicationTypeId) {
        this.applicationTypeId = applicationTypeId;
    }

    public String getForDate() {
        return forDate;
    }

    public void setForDate(String forDate) {
        this.forDate = forDate;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public int getRouteNodeId() {
        return routeNodeId;
    }

    public void setRouteNodeId(int routeNodeId) {
        this.routeNodeId = routeNodeId;
    }

    public int getRouteNodeType() {
        return routeNodeType;
    }

    public void setRouteNodeType(int routeNodeType) {
        this.routeNodeType = routeNodeType;
    }

    public List<InvoiceList> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<InvoiceList> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public int getFlgAllRouteData() {
        return flgAllRouteData;
    }

    public void setFlgAllRouteData(int flgAllRouteData) {
        this.flgAllRouteData = flgAllRouteData;
    }

    public String getAppVersionNo() {
        return AppVersionNo;
    }

    public void setAppVersionNo(String appVersionNo) {
        AppVersionNo = appVersionNo;
    }
    public int getCoverageAreaNodeId() {
        return coverageAreaNodeId;
    }

    public void setCoverageAreaNodeId(int coverageAreaNodeId) {
        this.coverageAreaNodeId = coverageAreaNodeId;
    }

    public int getCoverageAreaNodeType() {
        return coverageAreaNodeType;
    }

    public void setCoverageAreaNodeType(int coverageAreaNodeType) {
        this.coverageAreaNodeType = coverageAreaNodeType;
    }


    @Override
    public String toString() {
        return "DataParametersModelForTargetVsAcheived{" +
                "iMEINo='" + iMEINo + '\'' +
                ", versionId=" + versionId +
                ", AppVersionNo='" + AppVersionNo + '\'' +
                ", applicationTypeId=" + applicationTypeId +
                ", forDate='" + forDate + '\'' +
                ", registrationId='" + registrationId + '\'' +
                ", routeNodeId=" + routeNodeId +
                ", routeNodeType=" + routeNodeType +
                ", flgSelf=" + flgSelf +
                ", SalesmanNodeID=" + SalesmanNodeID +
                ", SalesmanNodeType=" + SalesmanNodeType +
                ", invoiceList=" + invoiceList +
                ", flgAllRouteData=" + flgAllRouteData +
                ", coverageAreaNodeId=" + coverageAreaNodeId +
                ", coverageAreaNodeType=" + coverageAreaNodeType +
                '}';
    }
}
