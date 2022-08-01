package com.astix.rajtraderssfasales.model;

public class TblRouteValidation {

    String PDACode;
    int OldRouteNodeID;
    int OldRouteNodeTYpe;
    int NewRouteNodeID;
    int NewRouteNodeType;

    String OTP;

    String OTPTemplate;

    String selectedRouteChageComment;
    int flgStep;

    public String getSelectedRouteChageComment() {
        return selectedRouteChageComment;
    }

    public void setSelectedRouteChageComment(String selectedRouteChageComment) {
        this.selectedRouteChageComment = selectedRouteChageComment;
    }

    public String getOTPTemplate() {
        return OTPTemplate;
    }

    public void setOTPTemplate(String OTPTemplate) {
        this.OTPTemplate = OTPTemplate;
    }

    public String getPDACode() {
        return PDACode;
    }

    public void setPDACode(String PDACode) {
        this.PDACode = PDACode;
    }

    public int getOldRouteNodeID() {
        return OldRouteNodeID;
    }

    public void setOldRouteNodeID(int oldRouteNodeID) {
        OldRouteNodeID = oldRouteNodeID;
    }

    public int getOldRouteNodeTYpe() {
        return OldRouteNodeTYpe;
    }

    public void setOldRouteNodeTYpe(int oldRouteNodeTYpe) {
        OldRouteNodeTYpe = oldRouteNodeTYpe;
    }

    public int getNewRouteNodeID() {
        return NewRouteNodeID;
    }

    public void setNewRouteNodeID(int newRouteNodeID) {
        NewRouteNodeID = newRouteNodeID;
    }

    public int getNewRouteNodeType() {
        return NewRouteNodeType;
    }

    public void setNewRouteNodeType(int newRouteNodeType) {
        NewRouteNodeType = newRouteNodeType;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public int getFlgStep() {
        return flgStep;
    }

    public void setFlgStep(int flgStep) {
        this.flgStep = flgStep;
    }

    @Override
    public String toString() {
        return "TblRouteValidation{" +
                "PDACode='" + PDACode + '\'' +
                ", OldRouteNodeID=" + OldRouteNodeID +
                ", OldRouteNodeTYpe=" + OldRouteNodeTYpe +
                ", NewRouteNodeID=" + NewRouteNodeID +
                ", NewRouteNodeType=" + NewRouteNodeType +
                ", OTP='" + OTP + '\'' +
                ", OTPTemplate='" + OTPTemplate + '\'' +
                ", selectedRouteChageComment='" + selectedRouteChageComment + '\'' +
                ", flgStep=" + flgStep +
                '}';
    }
}
