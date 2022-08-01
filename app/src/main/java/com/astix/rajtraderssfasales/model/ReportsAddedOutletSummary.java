
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportsAddedOutletSummary {



    @SerializedName("PDACode")
    @Expose
    private String iMEINo;
    @SerializedName("VersionId")
    @Expose
    private Integer versionId;

    @Override
    public String toString() {
        return "ReportsAddedOutletSummary{" +
                "iMEINo='" + iMEINo + '\'' +
                ", versionId=" + versionId +
                ", applicationTypeId=" + applicationTypeId +
                ", forDate='" + forDate + '\'' +
                ", FlgDrillLevel=" + FlgDrillLevel +
                '}';
    }

    @SerializedName("ApplicationTypeId")
    @Expose
    private Integer applicationTypeId;
    @SerializedName("ForDate")
    @Expose
    private String forDate;
    @SerializedName("flgDrillLevel")
    @Expose
    private Integer FlgDrillLevel;

    public Integer getFlgDrillLevel() {
        return FlgDrillLevel;
    }

    public void setFlgDrillLevel(Integer flgDrillLevel) {
        FlgDrillLevel = flgDrillLevel;
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





}
