
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblAttandanceDetails {



    private String AttandanceTime;
    private String PersonNodeID;
    private String PersonNodeType;

    private String OptionID;
    private String OptionDesc;
    private String ReasonID;
    private String ReasonDesc;
    private String Comment;
    private String Address;
    private String PinCode;
    private String City;
    private String State;
    private String fnLati;
    private String fnLongi;
    private String fnAccuracy;
    private String flgLocNotFound;
    private String fnAccurateProvider;
    private String AllProvidersLocation;
    private String fnAddress;
    private String GpsLat;
    private String GpsLong;
    private String GpsAccuracy;
    private String GpsAddress;

    private String NetwLat;
    private String NetwLong;
    private String NetwAccuracy;
    private String NetwAddress;
    private String FusedLat;
    private String FusedLong;
    private String FusedAccuracy;
    private String FusedAddress;
    private String FusedLocationLatitudeWithFirstAttempt;
    private String FusedLocationLongitudeWithFirstAttempt;
    private String FusedLocationAccuracyWithFirstAttempt;
    private int Sstat;
    private int flgLocationServicesOnOff;
    private int flgGPSOnOff;
    private int flgNetworkOnOff;
    private int flgFusedOnOff;
    private int flgInternetOnOffWhileLocationTracking;
    private int flgRestart;
    private String MapAddress;
    private String MapCity;
    private String MapPinCode;
    private String MapState;


    private String CityId;
    private String StateId;
    private String DistributorId;
    private String DistributorNodeType;
    private String DistributorName;
    private String AttenDate;
    private String LeaveStartDate;
    private String LeaveEndDate;

    private String SelfieName;

    private String SelfieURL;

    public String getSelfieName() {
        return SelfieName;
    }

    public void setSelfieName(String selfieName) {
        SelfieName = selfieName;
    }

    public String getSelfieURL() {
        return SelfieURL;
    }

    public void setSelfieURL(String selfieURL) {
        SelfieURL = selfieURL;
    }

    public String getAttandanceTime() {
        return AttandanceTime;
    }

    public void setAttandanceTime(String attandanceTime) {
        AttandanceTime = attandanceTime;
    }

    public String getPersonNodeID() {
        return PersonNodeID;
    }

    public void setPersonNodeID(String personNodeID) {
        PersonNodeID = personNodeID;
    }

    public String getPersonNodeType() {
        return PersonNodeType;
    }

    public void setPersonNodeType(String personNodeType) {
        PersonNodeType = personNodeType;
    }

    public String getOptionID() {
        return OptionID;
    }

    public void setOptionID(String optionID) {
        OptionID = optionID;
    }

    public String getOptionDesc() {
        return OptionDesc;
    }

    public void setOptionDesc(String optionDesc) {
        OptionDesc = optionDesc;
    }

    public String getReasonID() {
        return ReasonID;
    }

    public void setReasonID(String reasonID) {
        ReasonID = reasonID;
    }

    public String getReasonDesc() {
        return ReasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        ReasonDesc = reasonDesc;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getFnLati() {
        return fnLati;
    }

    public void setFnLati(String fnLati) {
        this.fnLati = fnLati;
    }

    public String getFnLongi() {
        return fnLongi;
    }

    public void setFnLongi(String fnLongi) {
        this.fnLongi = fnLongi;
    }

    public String getFnAccuracy() {
        return fnAccuracy;
    }

    public void setFnAccuracy(String fnAccuracy) {
        this.fnAccuracy = fnAccuracy;
    }

    public String getFlgLocNotFound() {
        return flgLocNotFound;
    }

    public void setFlgLocNotFound(String flgLocNotFound) {
        this.flgLocNotFound = flgLocNotFound;
    }

    public String getFnAccurateProvider() {
        return fnAccurateProvider;
    }

    public void setFnAccurateProvider(String fnAccurateProvider) {
        this.fnAccurateProvider = fnAccurateProvider;
    }

    public String getAllProvidersLocation() {
        return AllProvidersLocation;
    }

    public void setAllProvidersLocation(String allProvidersLocation) {
        AllProvidersLocation = allProvidersLocation;
    }

    public String getFnAddress() {
        return fnAddress;
    }

    public void setFnAddress(String fnAddress) {
        this.fnAddress = fnAddress;
    }

    public String getGpsLat() {
        return GpsLat;
    }

    public void setGpsLat(String gpsLat) {
        GpsLat = gpsLat;
    }

    public String getGpsLong() {
        return GpsLong;
    }

    public void setGpsLong(String gpsLong) {
        GpsLong = gpsLong;
    }

    public String getGpsAccuracy() {
        return GpsAccuracy;
    }

    public void setGpsAccuracy(String gpsAccuracy) {
        GpsAccuracy = gpsAccuracy;
    }

    public String getGpsAddress() {
        return GpsAddress;
    }

    public void setGpsAddress(String gpsAddress) {
        GpsAddress = gpsAddress;
    }

    public String getNetwLat() {
        return NetwLat;
    }

    public void setNetwLat(String netwLat) {
        NetwLat = netwLat;
    }

    public String getNetwLong() {
        return NetwLong;
    }

    public void setNetwLong(String netwLong) {
        NetwLong = netwLong;
    }

    public String getNetwAccuracy() {
        return NetwAccuracy;
    }

    public void setNetwAccuracy(String netwAccuracy) {
        NetwAccuracy = netwAccuracy;
    }

    public String getNetwAddress() {
        return NetwAddress;
    }

    public void setNetwAddress(String netwAddress) {
        NetwAddress = netwAddress;
    }

    public String getFusedLat() {
        return FusedLat;
    }

    public void setFusedLat(String fusedLat) {
        FusedLat = fusedLat;
    }

    public String getFusedLong() {
        return FusedLong;
    }

    public void setFusedLong(String fusedLong) {
        FusedLong = fusedLong;
    }

    public String getFusedAccuracy() {
        return FusedAccuracy;
    }

    public void setFusedAccuracy(String fusedAccuracy) {
        FusedAccuracy = fusedAccuracy;
    }

    public String getFusedAddress() {
        return FusedAddress;
    }

    public void setFusedAddress(String fusedAddress) {
        FusedAddress = fusedAddress;
    }

    public String getFusedLocationLatitudeWithFirstAttempt() {
        return FusedLocationLatitudeWithFirstAttempt;
    }

    public void setFusedLocationLatitudeWithFirstAttempt(String fusedLocationLatitudeWithFirstAttempt) {
        FusedLocationLatitudeWithFirstAttempt = fusedLocationLatitudeWithFirstAttempt;
    }

    public String getFusedLocationLongitudeWithFirstAttempt() {
        return FusedLocationLongitudeWithFirstAttempt;
    }

    public void setFusedLocationLongitudeWithFirstAttempt(String fusedLocationLongitudeWithFirstAttempt) {
        FusedLocationLongitudeWithFirstAttempt = fusedLocationLongitudeWithFirstAttempt;
    }

    public String getFusedLocationAccuracyWithFirstAttempt() {
        return FusedLocationAccuracyWithFirstAttempt;
    }

    public void setFusedLocationAccuracyWithFirstAttempt(String fusedLocationAccuracyWithFirstAttempt) {
        FusedLocationAccuracyWithFirstAttempt = fusedLocationAccuracyWithFirstAttempt;
    }

    public int getSstat() {
        return Sstat;
    }

    public void setSstat(int sstat) {
        Sstat = sstat;
    }

    public int getFlgLocationServicesOnOff() {
        return flgLocationServicesOnOff;
    }

    public void setFlgLocationServicesOnOff(int flgLocationServicesOnOff) {
        this.flgLocationServicesOnOff = flgLocationServicesOnOff;
    }

    public int getFlgGPSOnOff() {
        return flgGPSOnOff;
    }

    public void setFlgGPSOnOff(int flgGPSOnOff) {
        this.flgGPSOnOff = flgGPSOnOff;
    }

    public int getFlgNetworkOnOff() {
        return flgNetworkOnOff;
    }

    public void setFlgNetworkOnOff(int flgNetworkOnOff) {
        this.flgNetworkOnOff = flgNetworkOnOff;
    }

    public int getFlgFusedOnOff() {
        return flgFusedOnOff;
    }

    public void setFlgFusedOnOff(int flgFusedOnOff) {
        this.flgFusedOnOff = flgFusedOnOff;
    }

    public int getFlgInternetOnOffWhileLocationTracking() {
        return flgInternetOnOffWhileLocationTracking;
    }

    public void setFlgInternetOnOffWhileLocationTracking(int flgInternetOnOffWhileLocationTracking) {
        this.flgInternetOnOffWhileLocationTracking = flgInternetOnOffWhileLocationTracking;
    }

    public int getFlgRestart() {
        return flgRestart;
    }

    public void setFlgRestart(int flgRestart) {
        this.flgRestart = flgRestart;
    }

    public String getMapAddress() {
        return MapAddress;
    }

    public void setMapAddress(String mapAddress) {
        MapAddress = mapAddress;
    }

    public String getMapCity() {
        return MapCity;
    }

    public void setMapCity(String mapCity) {
        MapCity = mapCity;
    }

    public String getMapPinCode() {
        return MapPinCode;
    }

    public void setMapPinCode(String mapPinCode) {
        MapPinCode = mapPinCode;
    }

    public String getMapState() {
        return MapState;
    }

    public void setMapState(String mapState) {
        MapState = mapState;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getStateId() {
        return StateId;
    }

    public void setStateId(String stateId) {
        StateId = stateId;
    }

    public String getDistributorId() {
        return DistributorId;
    }

    public void setDistributorId(String distributorId) {
        DistributorId = distributorId;
    }

    public String getDistributorNodeType() {
        return DistributorNodeType;
    }

    public void setDistributorNodeType(String distributorNodeType) {
        DistributorNodeType = distributorNodeType;
    }

    public String getDistributorName() {
        return DistributorName;
    }

    public void setDistributorName(String distributorName) {
        DistributorName = distributorName;
    }

    public String getAttenDate() {
        return AttenDate;
    }

    public void setAttenDate(String attenDate) {
        AttenDate = attenDate;
    }

    public String getLeaveStartDate() {
        return LeaveStartDate;
    }

    public void setLeaveStartDate(String leaveStartDate) {
        LeaveStartDate = leaveStartDate;
    }

    public String getLeaveEndDate() {
        return LeaveEndDate;
    }

    public void setLeaveEndDate(String leaveEndDate) {
        LeaveEndDate = leaveEndDate;
    }

    @Override
    public String toString() {
        return "TblAttandanceDetails{" +
                "AttandanceTime='" + AttandanceTime + '\'' +
                ", PersonNodeID='" + PersonNodeID + '\'' +
                ", PersonNodeType='" + PersonNodeType + '\'' +
                ", OptionID='" + OptionID + '\'' +
                ", OptionDesc='" + OptionDesc + '\'' +
                ", ReasonID='" + ReasonID + '\'' +
                ", ReasonDesc='" + ReasonDesc + '\'' +
                ", Comment='" + Comment + '\'' +
                ", Address='" + Address + '\'' +
                ", PinCode='" + PinCode + '\'' +
                ", City='" + City + '\'' +
                ", State='" + State + '\'' +
                ", fnLati='" + fnLati + '\'' +
                ", fnLongi='" + fnLongi + '\'' +
                ", fnAccuracy='" + fnAccuracy + '\'' +
                ", flgLocNotFound='" + flgLocNotFound + '\'' +
                ", fnAccurateProvider='" + fnAccurateProvider + '\'' +
                ", AllProvidersLocation='" + AllProvidersLocation + '\'' +
                ", fnAddress='" + fnAddress + '\'' +
                ", GpsLat='" + GpsLat + '\'' +
                ", GpsLong='" + GpsLong + '\'' +
                ", GpsAccuracy='" + GpsAccuracy + '\'' +
                ", GpsAddress='" + GpsAddress + '\'' +
                ", NetwLat='" + NetwLat + '\'' +
                ", NetwLong='" + NetwLong + '\'' +
                ", NetwAccuracy='" + NetwAccuracy + '\'' +
                ", NetwAddress='" + NetwAddress + '\'' +
                ", FusedLat='" + FusedLat + '\'' +
                ", FusedLong='" + FusedLong + '\'' +
                ", FusedAccuracy='" + FusedAccuracy + '\'' +
                ", FusedAddress='" + FusedAddress + '\'' +
                ", FusedLocationLatitudeWithFirstAttempt='" + FusedLocationLatitudeWithFirstAttempt + '\'' +
                ", FusedLocationLongitudeWithFirstAttempt='" + FusedLocationLongitudeWithFirstAttempt + '\'' +
                ", FusedLocationAccuracyWithFirstAttempt='" + FusedLocationAccuracyWithFirstAttempt + '\'' +
                ", Sstat=" + Sstat +
                ", flgLocationServicesOnOff=" + flgLocationServicesOnOff +
                ", flgGPSOnOff=" + flgGPSOnOff +
                ", flgNetworkOnOff=" + flgNetworkOnOff +
                ", flgFusedOnOff=" + flgFusedOnOff +
                ", flgInternetOnOffWhileLocationTracking=" + flgInternetOnOffWhileLocationTracking +
                ", flgRestart=" + flgRestart +
                ", MapAddress='" + MapAddress + '\'' +
                ", MapCity='" + MapCity + '\'' +
                ", MapPinCode='" + MapPinCode + '\'' +
                ", MapState='" + MapState + '\'' +
                ", CityId='" + CityId + '\'' +
                ", StateId='" + StateId + '\'' +
                ", DistributorId='" + DistributorId + '\'' +
                ", DistributorNodeType='" + DistributorNodeType + '\'' +
                ", DistributorName='" + DistributorName + '\'' +
                ", AttenDate='" + AttenDate + '\'' +
                ", LeaveStartDate='" + LeaveStartDate + '\'' +
                ", LeaveEndDate='" + LeaveEndDate + '\'' +
                ", SelfieName='" + SelfieName + '\'' +
                ", SelfieURL='" + SelfieURL + '\'' +
                '}';
    }
}
