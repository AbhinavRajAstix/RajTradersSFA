
package com.astix.rajtraderssfasales.model;

public class TblLocationDetails {



    private String Lattitude;

    private String Longitude;
    private String Accuracy;
    private String Address;
    private String City;
    private String Pincode;
    private String State;
    private String fnAccurateProvider;
    private String GpsLat;

    private String GpsLong;
    private String GpsAccuracy;
    private String NetwLat;
    private String NetwLong;
    private String NetwAccuracy;
    private String FusedLat;
    private String FusedLong;
    private String FusedAccuracy;

    private String AllProvidersLocation;
    private String GpsAddress;
    private String NetwAddress;
    private String FusedAddress;
    private String FusedLocationLatitudeWithFirstAttempt;
    private String FusedLocationLongitudeWithFirstAttempt;
    private String FusedLocationAccuracyWithFirstAttempt;

    public String getLattitude() {
        return Lattitude;
    }

    public void setLattitude(String lattitude) {
        Lattitude = lattitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(String accuracy) {
        Accuracy = accuracy;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getFnAccurateProvider() {
        return fnAccurateProvider;
    }

    public void setFnAccurateProvider(String fnAccurateProvider) {
        this.fnAccurateProvider = fnAccurateProvider;
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

    public String getAllProvidersLocation() {
        return AllProvidersLocation;
    }

    public void setAllProvidersLocation(String allProvidersLocation) {
        AllProvidersLocation = allProvidersLocation;
    }

    public String getGpsAddress() {
        return GpsAddress;
    }

    public void setGpsAddress(String gpsAddress) {
        GpsAddress = gpsAddress;
    }

    public String getNetwAddress() {
        return NetwAddress;
    }

    public void setNetwAddress(String netwAddress) {
        NetwAddress = netwAddress;
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

    @Override
    public String toString() {
        return "TblLocationDetails{" +
                "Lattitude='" + Lattitude + '\'' +
                ", Longitude='" + Longitude + '\'' +
                ", Accuracy='" + Accuracy + '\'' +
                ", Address='" + Address + '\'' +
                ", City='" + City + '\'' +
                ", Pincode='" + Pincode + '\'' +
                ", State='" + State + '\'' +
                ", fnAccurateProvider='" + fnAccurateProvider + '\'' +
                ", GpsLat='" + GpsLat + '\'' +
                ", GpsLong='" + GpsLong + '\'' +
                ", GpsAccuracy='" + GpsAccuracy + '\'' +
                ", NetwLat='" + NetwLat + '\'' +
                ", NetwLong='" + NetwLong + '\'' +
                ", NetwAccuracy='" + NetwAccuracy + '\'' +
                ", FusedLat='" + FusedLat + '\'' +
                ", FusedLong='" + FusedLong + '\'' +
                ", FusedAccuracy='" + FusedAccuracy + '\'' +
                ", AllProvidersLocation='" + AllProvidersLocation + '\'' +
                ", GpsAddress='" + GpsAddress + '\'' +
                ", NetwAddress='" + NetwAddress + '\'' +
                ", FusedAddress='" + FusedAddress + '\'' +
                ", FusedLocationLatitudeWithFirstAttempt='" + FusedLocationLatitudeWithFirstAttempt + '\'' +
                ", FusedLocationLongitudeWithFirstAttempt='" + FusedLocationLongitudeWithFirstAttempt + '\'' +
                ", FusedLocationAccuracyWithFirstAttempt='" + FusedLocationAccuracyWithFirstAttempt + '\'' +
                '}';
    }
}
