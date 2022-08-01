
package com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPotentialDistributorRetailersFeedBackDetails {

    @SerializedName("RetailerCode")
    @Expose
    private String RetailerSectionID;

    @SerializedName("RetailerName")
    @Expose
    private String RetailerName;

    @SerializedName("Address")
    @Expose
    private String Address;
    @SerializedName("Comment")
    @Expose
    private String Comment;

    @SerializedName("RetFeedback")
    @Expose
    private int Feedback=-1;

    @SerializedName("PDACode")
    @Expose
    private String PDAcode;

    @SerializedName("DBNodeID")
    @Expose
    private String NodeID;

    @SerializedName("DBNodeType")
    @Expose
    private int NodeType;

    @SerializedName("EntryDate")
    @Expose
    private String EntryDate;

    @SerializedName("NewDBRCode")
    @Expose
    private String NewDBRCode;

    @SerializedName("flgFinalSubmit")
    @Expose
    private int flgFinalSubmit;

    @SerializedName("Distributor")
    @Expose
    private String DBRName;

    @SerializedName("Sstat")
    @Expose
    private int Sstat;

    @SerializedName("ContactNumber")
    @Expose
    private String ContactNumber;


    public String getRetailerSectionID() {
        return RetailerSectionID;
    }

    public void setRetailerSectionID(String retailerSectionID) {
        RetailerSectionID = retailerSectionID;
    }

    public String getRetailerName() {
        return RetailerName;
    }

    public void setRetailerName(String retailerName) {
        RetailerName = retailerName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public int getFeedback() {
        return Feedback;
    }

    public void setFeedback(int feedback) {
        Feedback = feedback;
    }

    public String getPDAcode() {
        return PDAcode;
    }

    public void setPDAcode(String PDAcode) {
        this.PDAcode = PDAcode;
    }

    public String getNodeID() {
        return NodeID;
    }

    public void setNodeID(String nodeID) {
        NodeID = nodeID;
    }

    public int getNodeType() {
        return NodeType;
    }

    public void setNodeType(int nodeType) {
        NodeType = nodeType;
    }

    public String getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(String entryDate) {
        EntryDate = entryDate;
    }

    public String getNewDBRCode() {
        return NewDBRCode;
    }

    public void setNewDBRCode(String newDBRCode) {
        NewDBRCode = newDBRCode;
    }

    public int getFlgFinalSubmit() {
        return flgFinalSubmit;
    }

    public void setFlgFinalSubmit(int flgFinalSubmit) {
        this.flgFinalSubmit = flgFinalSubmit;
    }

    public String getDBRName() {
        return DBRName;
    }

    public void setDBRName(String DBRName) {
        this.DBRName = DBRName;
    }

    public int getSstat() {
        return Sstat;
    }

    public void setSstat(int sstat) {
        Sstat = sstat;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "TblPotentialDistributorRetailersFeedBackDetails{" +
                "RetailerSectionID='" + RetailerSectionID + '\'' +
                ", RetailerName='" + RetailerName + '\'' +
                ", Address='" + Address + '\'' +
                ", Comment='" + Comment + '\'' +
                ", Feedback=" + Feedback +
                ", PDAcode='" + PDAcode + '\'' +
                ", NodeID='" + NodeID + '\'' +
                ", NodeType=" + NodeType +
                ", EntryDate='" + EntryDate + '\'' +
                ", NewDBRCode='" + NewDBRCode + '\'' +
                ", flgFinalSubmit=" + flgFinalSubmit +
                ", DBRName='" + DBRName + '\'' +
                ", Sstat=" + Sstat +
                ", ContactNumber='" + ContactNumber + '\'' +
                '}';
    }
}
