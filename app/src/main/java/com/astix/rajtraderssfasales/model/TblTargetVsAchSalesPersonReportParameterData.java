package com.astix.rajtraderssfasales.model;

public class TblTargetVsAchSalesPersonReportParameterData {

    String PDACode;
    String ForDate;
    int PersonNodeID;
    int PersonNodeType;

    public String getPDACode() {
        return PDACode;
    }

    public void setPDACode(String PDACode) {
        this.PDACode = PDACode;
    }

    public String getForDate() {
        return ForDate;
    }

    public void setForDate(String forDate) {
        ForDate = forDate;
    }

    public int getPersonNodeID() {
        return PersonNodeID;
    }

    public void setPersonNodeID(int personNodeID) {
        PersonNodeID = personNodeID;
    }

    public int getPersonNodeType() {
        return PersonNodeType;
    }

    public void setPersonNodeType(int personNodeType) {
        PersonNodeType = personNodeType;
    }

    @Override
    public String toString() {
        return "TblTargetVsAchSalesPersonReportParameterData{" +
                "PDACode='" + PDACode + '\'' +
                ", ForDate='" + ForDate + '\'' +
                ", PersonNodeID=" + PersonNodeID +
                ", PersonNodeType=" + PersonNodeType +
                '}';
    }


}
