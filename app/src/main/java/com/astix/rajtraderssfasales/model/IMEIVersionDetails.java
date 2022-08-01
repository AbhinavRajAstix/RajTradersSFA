
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IMEIVersionDetails {

    @SerializedName("VersionId")
    @Expose
    private Integer versionId;
    @SerializedName("FCMTokenNo")
    @Expose
    private String FCMTokenNo;



    @SerializedName("PDACode")
    @Expose
    private String iMEINo;
    @SerializedName("ApplicationTypeId")
    @Expose
    private Integer applicationTypeId;



    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public String getIMEINo() {
        return iMEINo;
    }

    public void setIMEINo(String iMEINo) {
        this.iMEINo = iMEINo;
    }

    public Integer getApplicationType() {
        return applicationTypeId;
    }

    public void setApplicationType(Integer applicationType) {
        this.applicationTypeId = applicationType;
    }

    public String getFCMTokenNo() {
        return FCMTokenNo;
    }

    public void setFCMTokenNo(String FCMTokenNo) {
        this.FCMTokenNo = FCMTokenNo;
    }

    @Override
    public String toString() {
        return "IMEIVersionDetails{" +
                "versionId=" + versionId +
                ", FCMTokenNo='" + FCMTokenNo + '\'' +
                ", iMEINo='" + iMEINo + '\'' +
                ", applicationTypeId=" + applicationTypeId +
                '}';
    }
}
