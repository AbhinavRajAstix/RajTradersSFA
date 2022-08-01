package com.astix.rajtraderssfasales.mTASModule;

public class TblMTASCallLogs {
    private String PhoneNumber;
    private String CallInitiateDateTime;
    private String CallDuration;
    private String CallPickedTime;
    private int flgCallPicked;
    private String recordingFileName;
    private String PDACode;
    private int flgRecording;
    private String storeID;
    private int sStat;
    private int teleCallingID;
    private int State;
    private String DebugState;
    private String StateCreatedDateTime;
    private String CallEndedDateTime;

    public TblMTASCallLogs() {
    }

    public TblMTASCallLogs(String phoneNumber, String callInitiateDateTime, String callDuration, String callPickedTime, int flgCallPicked, String recordingFileName, String PDACode, int flgRecording, String storeID, int sStat, int teleCallingID, int state, String debugState, String stateCreatedDateTime, String callEndedDateTime) {
        PhoneNumber = phoneNumber;
        CallInitiateDateTime = callInitiateDateTime;
        CallDuration = callDuration;
        CallPickedTime = callPickedTime;
        this.flgCallPicked = flgCallPicked;
        this.recordingFileName = recordingFileName;
        this.PDACode = PDACode;
        this.flgRecording = flgRecording;
        this.storeID = storeID;
        this.sStat = sStat;
        this.teleCallingID = teleCallingID;
        State = state;
        DebugState = debugState;
        StateCreatedDateTime = stateCreatedDateTime;
        CallEndedDateTime = callEndedDateTime;
    }

    public String getCallEndedDateTime() {
        return CallEndedDateTime;
    }

    public void setCallEndedDateTime(String callEndedDateTime) {
        CallEndedDateTime = callEndedDateTime;
    }

    public String getStateCreatedDateTime() {
        return StateCreatedDateTime;
    }

    public void setStateCreatedDateTime(String stateCreatedDateTime) {
        StateCreatedDateTime = stateCreatedDateTime;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getDebugState() {
        return DebugState;
    }

    public void setDebugState(String debugState) {
        DebugState = debugState;
    }

    public int getTeleCallingID() {
        return teleCallingID;
    }

    public void setTeleCallingID(int teleCallingID) {
        this.teleCallingID = teleCallingID;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getCallInitiateDateTime() {
        return CallInitiateDateTime;
    }

    public void setCallInitiateDateTime(String callInitiateDateTime) {
        CallInitiateDateTime = callInitiateDateTime;
    }

    public int getFlgCallPicked() {
        return flgCallPicked;
    }

    public void setFlgCallPicked(int flgCallPicked) {
        this.flgCallPicked = flgCallPicked;
    }

    public String getRecordingFileName() {
        return recordingFileName;
    }

    public void setRecordingFileName(String recordingFileName) {
        this.recordingFileName = recordingFileName;
    }

    public String getPDACode() {
        return PDACode;
    }

    public void setPDACode(String PDACode) {
        this.PDACode = PDACode;
    }

    public int getFlgRecording() {
        return flgRecording;
    }

    public void setFlgRecording(int flgRecording) {
        this.flgRecording = flgRecording;
    }

    public String getCallPickedTime() {
        return CallPickedTime;
    }

    public void setCallPickedTime(String callPickedTime) {
        CallPickedTime = callPickedTime;
    }

    public int getsStat() {
        return sStat;
    }

    public void setsStat(int sStat) {
        this.sStat = sStat;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getCallDuration() {
        return CallDuration;
    }

    public void setCallDuration(String callDuration) {
        CallDuration = callDuration;
    }

    @Override
    public String toString() {
        return "TblMTASCallLogs{" +
                "PhoneNumber='" + PhoneNumber + '\'' +
                ", CallInitiateDateTime='" + CallInitiateDateTime + '\'' +
                ", CallDuration='" + CallDuration + '\'' +
                ", CallPickedTime='" + CallPickedTime + '\'' +
                ", flgCallPicked=" + flgCallPicked +
                ", recordingFileName='" + recordingFileName + '\'' +
                ", PDACode='" + PDACode + '\'' +
                ", flgRecording=" + flgRecording +
                ", storeID='" + storeID + '\'' +
                ", sStat=" + sStat +
                '}';
    }
}
