
package com.astix.rajtraderssfasales.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblStoreListMaster {

    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("StoreID")
    @Expose
    private String storeID;
    @SerializedName("StoreName")
    @Expose
    private String storeName;
    @SerializedName("StoreLatitude")
    @Expose
    private double storeLatitude;
    @SerializedName("StoreLongitude")
    @Expose
    private double storeLongitude;
    @SerializedName("StoreType")
    @Expose
    private int storeType;
    @SerializedName("LastTransactionDate")
    @Expose
    private String lastTransactionDate;
    @SerializedName("LastVisitDate")
    @Expose
    private String lastVisitDate;
    @SerializedName("Sstat")
    @Expose
    private String sstat="0";
    @SerializedName("IsClose")
    @Expose
    private int isClose;
    @SerializedName("IsNextDat")
    @Expose
    private int isNextDat;
    @SerializedName("RouteID")
    @Expose
    private int routeID;
    @SerializedName("PaymentStage")
    @Expose
    private String paymentStage;
    @SerializedName("flgHasQuote")
    @Expose
    private int flgHasQuote;
    @SerializedName("flgAllowQuotation")
    @Expose
    private int flgAllowQuotation;
    @SerializedName("flgSubmitFromQuotation")
    @Expose
    private int flgSubmitFromQuotation;
    @SerializedName("RouteNodeType")
    @Expose
    private int routeNodeType;
    @SerializedName("DBR")
    @Expose
    private String dBR;
    @SerializedName("StoreCode")
    @Expose
    private String StoreCode;
    @SerializedName("StoreTypeDesc")
    @Expose
    private String StoreTypeDesc;
    @SerializedName("CollectionReq")
    @Expose
    private Object collectionReq;
    @SerializedName("StoreIDPDA")
    @Expose
    private String storeIDPDA;
    @SerializedName("flgHasBussiness")
    @Expose
    private int flgHasBussiness;
    @SerializedName("flgfeedbackReq")
    @Expose
    private int flgfeedbackReq;
    @SerializedName("OwnerName")
    @Expose
    private String ownerName;
    @SerializedName("StoreContactNo")
    @Expose
    private String storeContactNo;
    @SerializedName("StoreCatType")
    @Expose
    private String storeCatType;
    @SerializedName("StoreAddress")
    @Expose
    private String storeAddress;
    @SerializedName("StoreCity")
    @Expose
    private String storeCity;
    @SerializedName("StorePinCode")
    @Expose
    private String storePinCode;
    @SerializedName("StoreState")
    @Expose
    private String storeState;
    @SerializedName("flgRuleTaxVal")
    @Expose
    private int flgRuleTaxVal;
    @SerializedName("OutStanding")
    @Expose
    private double outStanding;
    @SerializedName("OverDue")
    @Expose
    private double overDue;
    @SerializedName("flgTransType")
    @Expose
    private int flgTransType;
    @SerializedName("SalesPersonName")
    @Expose
    private String salesPersonName;
    @SerializedName("SalesPersonContact")
    @Expose
    private String salesPersonContact;
    @SerializedName("TaxNumber")
    @Expose
    private String taxNumber;
    @SerializedName("IsComposite")
    @Expose
    private int isComposite;
    @SerializedName("CityID")
    @Expose
    private int cityID;
    @SerializedName("StateID")
    @Expose
    private int stateID;

    @SerializedName("flgCollDefControl")
    @Expose
    private int flgCollDefControl;


    @SerializedName("DBRName")
    @Expose
    private String Distributor;


    @SerializedName("IsDiscountApplicable")
    @Expose
    private int IsDiscountApplicable;


    @SerializedName("IsDiscountAllow")
    @Expose
    private String IsDiscountAllow;

    public String getIsDiscountAllow() {
        return IsDiscountAllow;
    }

    public void setIsDiscountAllow(String isDiscountAllow) {
        IsDiscountAllow = isDiscountAllow;
    }

    public int getIsDiscountApplicable() {
        return IsDiscountApplicable;
    }

    public void setIsDiscountApplicable(int isDiscountApplicable) {
        IsDiscountApplicable = isDiscountApplicable;
    }

    public String getStoreTypeDesc() {
        return StoreTypeDesc;
    }

    public void setStoreTypeDesc(String storeTypeDesc) {
        StoreTypeDesc = storeTypeDesc;
    }

    public String getDistributor() {
        return Distributor;
    }

    public void setDistributor(String distributor) {
        Distributor = distributor;
    }

    public int getFlgCollDefControl() {
        return flgCollDefControl;
    }

    public void setFlgCollDefControl(int flgCollDefControl) {
        this.flgCollDefControl = flgCollDefControl;
    }

    public double getCollectionPer() {
        return CollectionPer;
    }

    public void setCollectionPer(double collectionPer) {
        CollectionPer = collectionPer;
    }

    @SerializedName("CollectionPer")
    @Expose
    private double CollectionPer;

    public int getID() {
        return iD;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public double getStoreLatitude() {
        return storeLatitude;
    }

    public void setStoreLatitude(double storeLatitude) {
        this.storeLatitude = storeLatitude;
    }

    public double getStoreLongitude() {
        return storeLongitude;
    }

    public void setStoreLongitude(double storeLongitude) {
        this.storeLongitude = storeLongitude;
    }

    public int getStoreType() {
        return storeType;
    }

    public void setStoreType(int storeType) {
        this.storeType = storeType;
    }

    public String getLastTransactionDate() {
        return lastTransactionDate;
    }

    public void setLastTransactionDate(String lastTransactionDate) {
        this.lastTransactionDate = lastTransactionDate;
    }

    public String getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(String lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public String getSstat() {
        return sstat;
    }

    public void setSstat(String sstat) {
        this.sstat = sstat;
    }

    public int getIsClose() {
        return isClose;
    }

    public void setIsClose(int isClose) {
        this.isClose = isClose;
    }

    public int getIsNextDat() {
        return isNextDat;
    }

    public void setIsNextDat(int isNextDat) {
        this.isNextDat = isNextDat;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public String getPaymentStage() {
        return paymentStage;
    }

    public void setPaymentStage(String paymentStage) {
        this.paymentStage = paymentStage;
    }

    public int getFlgHasQuote() {
        return flgHasQuote;
    }

    public void setFlgHasQuote(int flgHasQuote) {
        this.flgHasQuote = flgHasQuote;
    }

    public int getFlgAllowQuotation() {
        return flgAllowQuotation;
    }

    public void setFlgAllowQuotation(int flgAllowQuotation) {
        this.flgAllowQuotation = flgAllowQuotation;
    }

    public int getFlgSubmitFromQuotation() {
        return flgSubmitFromQuotation;
    }

    public void setFlgSubmitFromQuotation(int flgSubmitFromQuotation) {
        this.flgSubmitFromQuotation = flgSubmitFromQuotation;
    }

    public int getRouteNodeType() {
        return routeNodeType;
    }

    public void setRouteNodeType(int routeNodeType) {
        this.routeNodeType = routeNodeType;
    }

    public String getDBR() {
        return dBR;
    }

    public void setDBR(String dBR) {
        this.dBR = dBR;
    }

    public Object getCollectionReq() {
        return collectionReq;
    }

    public void setCollectionReq(Object collectionReq) {
        this.collectionReq = collectionReq;
    }

    public String getStoreIDPDA() {
        return storeIDPDA;
    }

    public void setStoreIDPDA(String storeIDPDA) {
        this.storeIDPDA = storeIDPDA;
    }

    public int getFlgHasBussiness() {
        return flgHasBussiness;
    }

    public void setFlgHasBussiness(int flgHasBussiness) {
        this.flgHasBussiness = flgHasBussiness;
    }

    public int getFlgfeedbackReq() {
        return flgfeedbackReq;
    }

    public void setFlgfeedbackReq(int flgfeedbackReq) {
        this.flgfeedbackReq = flgfeedbackReq;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getStoreContactNo() {
        return storeContactNo;
    }

    public void setStoreContactNo(String storeContactNo) {
        this.storeContactNo = storeContactNo;
    }

    public String getStoreCatType() {
        return storeCatType;
    }

    public void setStoreCatType(String storeCatType) {
        this.storeCatType = storeCatType;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
    }

    public String getStorePinCode() {
        return storePinCode;
    }

    public void setStorePinCode(String storePinCode) {
        this.storePinCode = storePinCode;
    }

    public String getStoreState() {
        return storeState;
    }

    public void setStoreState(String storeState) {
        this.storeState = storeState;
    }

    public int getFlgRuleTaxVal() {
        return flgRuleTaxVal;
    }

    public void setFlgRuleTaxVal(int flgRuleTaxVal) {
        this.flgRuleTaxVal = flgRuleTaxVal;
    }

    public double getOutStanding() {
        return outStanding;
    }

    public void setOutStanding(double outStanding) {
        this.outStanding = outStanding;
    }

    public double getOverDue() {
        return overDue;
    }

    public void setOverDue(double overDue) {
        this.overDue = overDue;
    }

    public int getFlgTransType() {
        return flgTransType;
    }

    public void setFlgTransType(int flgTransType) {
        this.flgTransType = flgTransType;
    }

    public String getSalesPersonName() {
        return salesPersonName;
    }

    public void setSalesPersonName(String salesPersonName) {
        this.salesPersonName = salesPersonName;
    }

    public String getSalesPersonContact() {
        return salesPersonContact;
    }

    public void setSalesPersonContact(String salesPersonContact) {
        this.salesPersonContact = salesPersonContact;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public int getIsComposite() {
        return isComposite;
    }

    public void setIsComposite(int isComposite) {
        this.isComposite = isComposite;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public int getStateID() {
        return stateID;
    }

    public void setStateID(int stateID) {
        this.stateID = stateID;
    }


    @SerializedName("flgLocationEdit")
    @Expose
    private int flgLocationEdit;

    public int getFlgLocationEdit() {
        return flgLocationEdit;
    }

    public void setFlgLocationEdit(int flgLocationEdit) {
        this.flgLocationEdit = flgLocationEdit;
    }


    @SerializedName("WhatsappNumber")
    @Expose
    private String WhatsappNumber;

    public String getWhatsappNumber() {
        return WhatsappNumber;
    }

    public void setWhatsappNumber(String whatsappNumber) {
        WhatsappNumber = whatsappNumber;
    }

    public String getLandlineNum() {
        return landlineNum;
    }

    public void setLandlineNum(String landlineNum) {
        this.landlineNum = landlineNum;
    }

    @SerializedName("Landline")
    @Expose
    private String landlineNum;


    @SerializedName("EmailID")
    @Expose
    private String EmailID;

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }


    @SerializedName("flgStoreEdit")
    @Expose
    private int flgStoreEdit;

    public int getFlgStoreEdit() {
        return flgStoreEdit;
    }

    public void setFlgStoreEdit(int flgStoreEdit) {
        this.flgStoreEdit = flgStoreEdit;
    }


    @SerializedName("flgGSTCapture")
    @Expose
    private int flgGSTCapture;


    @SerializedName("GSTNumber")
    @Expose
    private String GSTNumber;


    @SerializedName("Region")
    @Expose
    private String Region;


    @SerializedName("RegionID")
    @Expose
    private int RegionID;

    public String getRegion() {
        return Region;
    }

    public String getStoreCode() {
        return StoreCode;
    }

    public void setStoreCode(String storeCode) {
        StoreCode = storeCode;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public int getRegionID() {
        return RegionID;
    }

    public void setRegionID(int regionID) {
        RegionID = regionID;
    }

    public int getFlgGSTCapture() {
        return flgGSTCapture;
    }

    public void setFlgGSTCapture(int flgGSTCapture) {
        this.flgGSTCapture = flgGSTCapture;
    }

    public String getGSTNumber() {
        return GSTNumber;
    }

    public void setGSTNumber(String GSTNumber) {
        this.GSTNumber = GSTNumber;
    }

    @SerializedName("flgStarOutlet")
    private int flgStarStore;

    @SerializedName("flgUnproductive(P3M)")
    private int flgUnProductiveStore;

    public int getFlgStarStore() {
        return flgStarStore;
    }

    public void setFlgStarStore(int flgStarStore) {
        this.flgStarStore = flgStarStore;
    }

    public int getFlgUnProductiveStore() {
        return flgUnProductiveStore;
    }

    public void setFlgUnProductiveStore(int flgUnProductiveStore) {
        this.flgUnProductiveStore = flgUnProductiveStore;
    }

    @Override
    public String toString() {
        return "TblStoreListMaster{" +
                "iD=" + iD +
                ", storeID='" + storeID + '\'' +
                ", storeName='" + storeName + '\'' +
                ", storeLatitude=" + storeLatitude +
                ", storeLongitude=" + storeLongitude +
                ", storeType=" + storeType +
                ", lastTransactionDate='" + lastTransactionDate + '\'' +
                ", lastVisitDate='" + lastVisitDate + '\'' +
                ", sstat='" + sstat + '\'' +
                ", isClose=" + isClose +
                ", isNextDat=" + isNextDat +
                ", routeID=" + routeID +
                ", paymentStage='" + paymentStage + '\'' +
                ", flgHasQuote=" + flgHasQuote +
                ", flgAllowQuotation=" + flgAllowQuotation +
                ", flgSubmitFromQuotation=" + flgSubmitFromQuotation +
                ", routeNodeType=" + routeNodeType +
                ", dBR='" + dBR + '\'' +
                ", StoreCode='" + StoreCode + '\'' +
                ", StoreTypeDesc='" + StoreTypeDesc + '\'' +
                ", collectionReq=" + collectionReq +
                ", storeIDPDA='" + storeIDPDA + '\'' +
                ", flgHasBussiness=" + flgHasBussiness +
                ", flgfeedbackReq=" + flgfeedbackReq +
                ", ownerName='" + ownerName + '\'' +
                ", storeContactNo='" + storeContactNo + '\'' +
                ", storeCatType='" + storeCatType + '\'' +
                ", storeAddress='" + storeAddress + '\'' +
                ", storeCity='" + storeCity + '\'' +
                ", storePinCode='" + storePinCode + '\'' +
                ", storeState='" + storeState + '\'' +
                ", flgRuleTaxVal=" + flgRuleTaxVal +
                ", outStanding=" + outStanding +
                ", overDue=" + overDue +
                ", flgTransType=" + flgTransType +
                ", salesPersonName='" + salesPersonName + '\'' +
                ", salesPersonContact='" + salesPersonContact + '\'' +
                ", taxNumber='" + taxNumber + '\'' +
                ", isComposite=" + isComposite +
                ", cityID=" + cityID +
                ", stateID=" + stateID +
                ", flgCollDefControl=" + flgCollDefControl +
                ", Distributor='" + Distributor + '\'' +
                ", IsDiscountApplicable=" + IsDiscountApplicable +
                ", IsDiscountAllow='" + IsDiscountAllow + '\'' +
                ", CollectionPer=" + CollectionPer +
                ", flgLocationEdit=" + flgLocationEdit +
                ", WhatsappNumber='" + WhatsappNumber + '\'' +
                ", landlineNum='" + landlineNum + '\'' +
                ", EmailID='" + EmailID + '\'' +
                ", flgStoreEdit=" + flgStoreEdit +
                ", flgGSTCapture=" + flgGSTCapture +
                ", GSTNumber='" + GSTNumber + '\'' +
                ", Region='" + Region + '\'' +
                ", RegionID=" + RegionID +
                ", flgStarStore=" + flgStarStore +
                ", flgUnProductiveStore=" + flgUnProductiveStore +
                '}';
    }
}
