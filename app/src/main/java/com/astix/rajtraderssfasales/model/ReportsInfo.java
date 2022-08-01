
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportsInfo {

    @SerializedName("PDACode")
    @Expose
    private String iMEINo;

    @Override
    public String toString() {
        return "ReportsInfo{" +
                "iMEINo='" + iMEINo + '\'' +
                ", versionId=" + versionId +
                ", applicationTypeId=" + applicationTypeId +
                ", forDate='" + forDate + '\'' +
                ", flgDataScope=" + flgDataScope +
                ", salesmanNodeId=" + salesmanNodeId +
                ", salesmanNodeType=" + salesmanNodeType +
                '}';
    }

    @SerializedName("VersionId")
    @Expose
    private Integer versionId;
    @SerializedName("ApplicationTypeId")
    @Expose
    private Integer applicationTypeId;
    @SerializedName("ForDate")
    @Expose
    private String forDate;


    @SerializedName("flgDataScope")
    @Expose
    private Integer flgDataScope;


    @SerializedName("SalesmanNodeId")
    @Expose
    private Integer salesmanNodeId;

    @SerializedName("SalesmanNodeType")
    @Expose
    private Integer salesmanNodeType;

    public Integer getFlgDataScope() {
        return flgDataScope;
    }

    public void setFlgDataScope(Integer flgDataScope) {
        this.flgDataScope = flgDataScope;
    }



    public String getIMEINo() {
        return iMEINo;
    }

    public void setIMEINo(String iMEINo) {
        this.iMEINo = iMEINo;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Integer getApplicationTypeId() {
        return applicationTypeId;
    }

    public void setApplicationTypeId(Integer applicationTypeId) {
        this.applicationTypeId = applicationTypeId;
    }

    public String getForDate() {
        return forDate;
    }

    public void setForDate(String forDate) {
        this.forDate = forDate;
    }


    public Integer getSalesmanNodeId() {
        return salesmanNodeId;
    }

    public void setSalesmanNodeId(Integer salesmanNodeId) {
        this.salesmanNodeId = salesmanNodeId;
    }

    public Integer getSalesmanNodeType() {
        return salesmanNodeType;
    }

    public void setSalesmanNodeType(Integer salesmanNodeType) {
        this.salesmanNodeType = salesmanNodeType;
    }


}
