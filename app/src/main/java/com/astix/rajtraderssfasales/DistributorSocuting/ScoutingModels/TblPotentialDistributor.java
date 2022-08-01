
package com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels;

import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.PotentialDistributorImageTable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TblPotentialDistributor {

    @SerializedName("PDACode")
    @Expose
    private String PDACode;


    @SerializedName("NewDBRCode")
    @Expose
    private String NewDBRCode="";

    public String getNewDBRCode() {
        return NewDBRCode;
    }

    public void setNewDBRCode(String newDBRCode) {
        NewDBRCode = newDBRCode;
    }

    @SerializedName("NodeID")
    @Expose
    private String NodeID;
    @SerializedName("NodeType")
    @Expose
    private int NodeType=180;
    @SerializedName("LatCode")
    @Expose
    private double LatCode=0.0;

    @SerializedName("LongCode")
    @Expose
    private double LongCode=0.0;

    @SerializedName("DistributorName")
    @Expose
    private String Descr="";

    @SerializedName("ContactPersonName")
    @Expose
    private String ContactPersonName="";

    @SerializedName("ContactPersonMobNo")
    @Expose
    private String ContactPersonMobileNumber ="";


    @SerializedName("ConatctPersonEMailID")
    @Expose
    private String ContactPersonEMailID ="";

    @SerializedName("Telephonenumber")
    @Expose
    private String TelephoneNo="";

    @SerializedName("GSTNumber")
    @Expose
    private String GSTNumber="";

    @SerializedName("AreaCovered")
    @Expose
    private String AreaCovered="";

    @SerializedName("NoOFRetailersCovered")
    @Expose
    private String NoOfRetailersCovered="";

    @SerializedName("GodownArea")
    @Expose
    private String GodownAreaSqFt="";


    @SerializedName("MonthlyTurnOver")
    @Expose
    private String MonthlyTurnOver="";


    List<TblPotentialDistributorVehicleListDetails> tblPotentialDistributorVehicleListDetailsList;

    @SerializedName("OrderTATinDays")
    @Expose
    private int OrderTATinDays;

    @SerializedName("CompanyProductInvestment")
    @Expose
    private int CompanyProductInvestmentLacs;

    @SerializedName("RetailerCreditLimit")
    @Expose
    private int RetailerCreditLimit;

    @SerializedName("BusinessSince")
    @Expose
    private String BusinessSince="";

    @SerializedName("DistributorReady")
    @Expose
    private int DistributorReady;

    @SerializedName("NextFollowupDate")
    @Expose
    private String NextFollowupDate="";

    @SerializedName("NoEmployee_Dispatch")
    @Expose
    private int DBREmployee_Dispatch;

    @SerializedName("NoEmployee_Billing")
    @Expose
    private int DBREmployee_Billing;

    @SerializedName("NoEmployee_SalesStaff")
    @Expose
    private int DBREmployee_SalesStaff;


    @SerializedName("Address")
    @Expose
    private String Address="";


    @SerializedName("Pincode")
    @Expose
    private String Pincode="";


    @SerializedName("City")
    @Expose
    private String City="";

    @SerializedName("District")
    @Expose
    private String District="NA";


    @SerializedName("State")
    @Expose
    private String State="";

    @SerializedName("EntryPersonNodeID")
    @Expose
    private int EntryPersonNodeID;


    @SerializedName("EntryPersonNodeType")
    @Expose
    private int EntryPersonNodeType;

    @SerializedName("CreatedDate")
    @Expose
    private String CreatedDate="";

    @SerializedName("flgGSTTaken")
    @Expose
    private int flgGSTTaken=-1;


    @SerializedName("VehicleTypeID")
    @Expose
    private String VehicleTypeID="0";

    @SerializedName("NoOFVehicles")
    @Expose
    private int NoOfVehicles;


    @SerializedName("NoOfRetilersSection")
    @Expose
    private int NoOfRetilersSection=3;

    @SerializedName("flgViewOrEdit")
    @Expose
    private int flgViewOrEdit=0;

    @SerializedName("flgDelete")
    @Expose
    private int flgDelete=0;

    @SerializedName("IsOldDistributorReplaced")
    @Expose
    private int IsOldDistributorReplaced=-1;

    @SerializedName("IsOldDistributorDFinalPaymentDone")
    @Expose
    private int IsOldDistributorDFinalPaymentDone=-1;

    @SerializedName("flgDistributorSS")
    @Expose
    private int flgDistributorSS=-1;

    @SerializedName("IsNewLocation")
    @Expose
    private int IsNewLocation=-1;

    @SerializedName("flgTownDistributorSubD")
    @Expose
    private int flgTownDistributorSubD=-1;

    @SerializedName("Address_Godown")
    @Expose
    private String Address_Godown="";

    @SerializedName("Pincode_Godown")
    @Expose
    private String Pincode_Godown="";

    @SerializedName("City_Godown")
    @Expose
    private String City_Godown="";

    @SerializedName("District_Godown")
    @Expose
    private String District_Godown="";

    @SerializedName("State_Godown")
    @Expose
    private String State_Godown="";

    @SerializedName("flgProprietorPartner")
    @Expose
    private int flgProprietorPartner=-1;
    @SerializedName("IsPANOfFirmSubmitted")
    @Expose
    private int IsPANOfFirmSubmitted=-1;
    @SerializedName("BankAccountNumber")
    @Expose
    private String BankAccountNumber="";


    @SerializedName("IFSCCode")
    @Expose
    private String IFSCCode="";

    @SerializedName("BankAddress")
    @Expose
    private String BankAddress="";

    @SerializedName("ExpectedBusiness")
    @Expose
    private String ExpectedBusiness="";

    @SerializedName("ReqGodownSpace")
    @Expose
    private String ReqGodownSpace="";

    @SerializedName("AgreedGodownSpace")
    @Expose
    private String AgreedGodownSpace="";

    @SerializedName("AgreedInvestment")
    @Expose
    private String AgreedInvestment="";


    @SerializedName("IdealROI")
    @Expose
    private String IdealROI="";

    @SerializedName("ExpectedROI")
    @Expose
    private String ExpectedROI="";

    @SerializedName("IsCheckGiven")
    @Expose
    private int IsCheckGiven=-1;

    @SerializedName("ChequeNumber")
    @Expose
    private String ChequeNumber="";


    @SerializedName("IsGSTDetailsSubmitted")
    @Expose
    private int IsGSTDetailsSubmitted=-1;


    @SerializedName("IsProprietorPanSubmited")
    @Expose
    private int IsProprietorPanSubmited=-1;

    @SerializedName("ProprietorPanNumber")
    @Expose
    private String ProprietorPanNumber="";

    @SerializedName("IsPartnerDeedSubmitted")
    @Expose
    private int IsPartnerDeedSubmitted=-1;

    @SerializedName("PartnerDeedNumber")
    @Expose
    private String PartnerDeedNumber="";



    @SerializedName("IsPartner1PanSubmitted")
    @Expose
    private int IsPartner1PANSubmitted=-1;

    @SerializedName("IsPartner2PanSubmitted")
    @Expose
    private int IsPartner2PANSubmitted=-1;

    @SerializedName("PanNumber_Partner1")
    @Expose
    private String PanNumber_Partner1="";

    @SerializedName("PanNumber_Partner2")
    @Expose
    private String PanNumber_Partner2="";


    @SerializedName("PANNoOfFirm")
    @Expose
    private String PANNoOfFirm="";

    @SerializedName("flgGodownAddressSameAsOffic")
    @Expose
    private int flgGodownAddressSameAsOffic;


    @SerializedName("flgAppointed")
    @Expose
    private int flgAppointed;

    @SerializedName("OldDistributorID")
    @Expose
    private int OldDistriutorID;

    @SerializedName("LocationCapturedFrom")
    @Expose
    private int LocationCapturedFrom;

    public int getOldDistriutorID() {
        return OldDistriutorID;
    }

    public void setOldDistriutorID(int oldDistriutorID) {
        OldDistriutorID = oldDistriutorID;
    }


    public int getLocationCapturedFrom() {
        return LocationCapturedFrom;
    }

    public void setLocationCapturedFrom(int locationCapturedFrom) {
        LocationCapturedFrom = locationCapturedFrom;
    }

    public int getFlgAppointed() {
        return flgAppointed;
    }

    public void setFlgAppointed(int flgAppointed) {
        this.flgAppointed = flgAppointed;
    }

    public int getFlgGodownAddressSameAsOffic() {
        return flgGodownAddressSameAsOffic;
    }

    public void setFlgGodownAddressSameAsOffic(int flgGodownAddressSameAsOffic) {
        this.flgGodownAddressSameAsOffic = flgGodownAddressSameAsOffic;
    }

    private int flgLocationConfirm;

    private int flgRadioButtonSelected;

    private int Sstat;

    public String getPartnerDeedNumber() {
        return PartnerDeedNumber;
    }

    public void setPartnerDeedNumber(String partnerDeedNumber) {
        if(partnerDeedNumber!=null)
        PartnerDeedNumber = partnerDeedNumber;
    }

    public int getIsPartner1PANSubmitted() {
        return IsPartner1PANSubmitted;
    }

    public void setIsPartner1PANSubmitted(int isPartner1PANSubmitted) {
        IsPartner1PANSubmitted = isPartner1PANSubmitted;
    }

    public int getIsPartner2PANSubmitted() {
        return IsPartner2PANSubmitted;
    }

    public void setIsPartner2PANSubmitted(int isPartner2PANSubmitted) {
        IsPartner2PANSubmitted = isPartner2PANSubmitted;
    }

    public int getIsPANOfFirmSubmitted() {
        return IsPANOfFirmSubmitted;
    }

    public void setIsPANOfFirmSubmitted(int isPANOfFirmSubmitted) {
        IsPANOfFirmSubmitted = isPANOfFirmSubmitted;
    }

    public String getPANNoOfFirm() {
        return PANNoOfFirm;
    }

    public void setPANNoOfFirm(String PANNoOfFirm) {
        if(PANNoOfFirm!=null)
        this.PANNoOfFirm = PANNoOfFirm;
    }

    public int getFlgLocationConfirm() {
        return flgLocationConfirm;
    }

    public void setFlgLocationConfirm(int flgLocationConfirm) {
        this.flgLocationConfirm = flgLocationConfirm;
    }

    public int getFlgRadioButtonSelected() {
        return flgRadioButtonSelected;
    }

    public void setFlgRadioButtonSelected(int flgRadioButtonSelected) {
        this.flgRadioButtonSelected = flgRadioButtonSelected;
    }

    List<TblPotentialDistributorCompetitorCompanyMstr> tblPotentialDistributorCompetitorCompanyMstrList;
    List<TblPotentialDistributorCompetitorBrandMstr> tblPotentialDistributorCompetitorBrandMstrList;

    List<TblPotentialDistributorRetailersFeedBackDetails> tblPotentialDistributorRetailersFeedBackDetailsList;


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

    public double getLatCode() {
        return LatCode;
    }

    public void setLatCode(double latCode) {
        LatCode = latCode;
    }

    public double getLongCode() {
        return LongCode;
    }

    public void setLongCode(double longCode) {
        LongCode = longCode;
    }

    public String getDescr() {
        return Descr;
    }

    public void setDescr(String descr) {
        Descr = descr;
    }

    public String getContactPersonName() {
        return ContactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        if(contactPersonName!=null)
        ContactPersonName = contactPersonName;
    }

    public String getContactPersonMobileNumber() {
        return ContactPersonMobileNumber;
    }

    public void setContactPersonMobileNumber(String contactPersonMobileNumber) {
        if(contactPersonMobileNumber!=null)
        ContactPersonMobileNumber = contactPersonMobileNumber;
        else
            ContactPersonMobileNumber="";
    }

    public String getContactPersonEMailID() {
        return ContactPersonEMailID;
    }

    public void setContactPersonEMailID(String contactPersonEMailID) {
        if(contactPersonEMailID!=null)
        ContactPersonEMailID = contactPersonEMailID;
    }

    public String getTelephoneNo() {
        return TelephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        if(telephoneNo!=null)
        TelephoneNo = telephoneNo;
    }

    public String getGSTNumber() {
        return GSTNumber;
    }

    public void setGSTNumber(String GSTNumber) {

        if(GSTNumber!=null)
        this.GSTNumber = GSTNumber;
    }

    public String getAreaCovered() {
        return AreaCovered;
    }

    public void setAreaCovered(String areaCovered) {
        if(areaCovered!=null)
            AreaCovered = areaCovered;
    }

    public String getNoOfRetailersCovered() {
        return NoOfRetailersCovered;
    }

    public void setNoOfRetailersCovered(String noOfRetailersCovered) {
        if(noOfRetailersCovered!=null)
        NoOfRetailersCovered = noOfRetailersCovered;
    }

    public String getGodownAreaSqFt() {
        return GodownAreaSqFt;
    }

    public void setGodownAreaSqFt(String godownAreaSqFt) {
        if(godownAreaSqFt!=null)
        GodownAreaSqFt = godownAreaSqFt;
    }

    public String getMonthlyTurnOver() {
        return MonthlyTurnOver;
    }

    public void setMonthlyTurnOver(String monthlyTurnOver) {
        if(monthlyTurnOver!=null)
        MonthlyTurnOver = monthlyTurnOver;
    }

    public List<TblPotentialDistributorVehicleListDetails> getTblPotentialDistributorVehicleListDetailsList() {
        return tblPotentialDistributorVehicleListDetailsList;
    }

    public void setTblPotentialDistributorVehicleListDetailsList(List<TblPotentialDistributorVehicleListDetails> tblPotentialDistributorVehicleListDetailsList) {
        this.tblPotentialDistributorVehicleListDetailsList = tblPotentialDistributorVehicleListDetailsList;
    }

    public int getOrderTATinDays() {
        return OrderTATinDays;
    }

    public void setOrderTATinDays(int orderTATinDays) {
        OrderTATinDays = orderTATinDays;
    }

    public int getCompanyProductInvestmentLacs() {
        return CompanyProductInvestmentLacs;
    }

    public void setCompanyProductInvestmentLacs(int companyProductInvestmentLacs) {
        CompanyProductInvestmentLacs = companyProductInvestmentLacs;
    }

    public int getRetailerCreditLimit() {
        return RetailerCreditLimit;
    }

    public void setRetailerCreditLimit(int retailerCreditLimit) {
        RetailerCreditLimit = retailerCreditLimit;
    }

    public String getBusinessSince() {
        return BusinessSince;
    }

    public void setBusinessSince(String businessSince) {
        if(businessSince!=null)
        BusinessSince = businessSince;
    }

    public int getDistributorReady() {
        return DistributorReady;
    }

    public void setDistributorReady(int distributorReady) {
        DistributorReady = distributorReady;
    }

    public String getNextFollowupDate() {
        return NextFollowupDate;
    }

    public void setNextFollowupDate(String nextFollowupDate) {
        if(nextFollowupDate!=null)
        NextFollowupDate = nextFollowupDate;
    }

    public int getDBREmployee_Dispatch() {
        return DBREmployee_Dispatch;
    }

    public void setDBREmployee_Dispatch(int DBREmployee_Dispatch) {
        this.DBREmployee_Dispatch = DBREmployee_Dispatch;
    }

    public int getDBREmployee_Billing() {
        return DBREmployee_Billing;
    }

    public void setDBREmployee_Billing(int DBREmployee_Billing) {
        this.DBREmployee_Billing = DBREmployee_Billing;
    }

    public int getDBREmployee_SalesStaff() {
        return DBREmployee_SalesStaff;
    }

    public void setDBREmployee_SalesStaff(int DBREmployee_SalesStaff) {
        this.DBREmployee_SalesStaff = DBREmployee_SalesStaff;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        if(address!=null)
        Address = address;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        if(pincode!=null)
        Pincode = pincode;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        if(city!=null)
        City = city;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        if(district!=null)
        District = district;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        if(state!=null)
        State = state;
    }

    public int getEntryPersonNodeID() {
        return EntryPersonNodeID;
    }

    public void setEntryPersonNodeID(int entryPersonNodeID) {
        EntryPersonNodeID = entryPersonNodeID;
    }

    public int getEntryPersonNodeType() {
        return EntryPersonNodeType;
    }

    public void setEntryPersonNodeType(int entryPersonNodeType) {
        EntryPersonNodeType = entryPersonNodeType;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        if(createdDate!=null)
        CreatedDate = createdDate;
    }

    public int getFlgGSTTaken() {
        return flgGSTTaken;
    }

    public void setFlgGSTTaken(int flgGSTTaken) {
        this.flgGSTTaken = flgGSTTaken;
    }

    public String getVehicleTypeID() {
        return VehicleTypeID;
    }

    public void setVehicleTypeID(String vehicleTypeID) {
        if(vehicleTypeID!=null)
        VehicleTypeID = vehicleTypeID;
    }

    public int getNoOfVehicles() {
        return NoOfVehicles;
    }

    public void setNoOfVehicles(int noOfVehicles) {
        NoOfVehicles = noOfVehicles;
    }

    public int getSstat() {
        return Sstat;
    }

    public void setSstat(int sstat) {
        Sstat = sstat;
    }

    public int getNoOfRetilersSection() {
        return NoOfRetilersSection;
    }

    public void setNoOfRetilersSection(int noOfRetilersSection) {
        NoOfRetilersSection = noOfRetilersSection;
    }

    public int getFlgViewOrEdit() {
        return flgViewOrEdit;
    }

    public void setFlgViewOrEdit(int flgViewOrEdit) {
        this.flgViewOrEdit = flgViewOrEdit;
    }

    public List<TblPotentialDistributorCompetitorCompanyMstr> getTblPotentialDistributorCompetitorCompanyMstrList() {
        return tblPotentialDistributorCompetitorCompanyMstrList;
    }

    public void setTblPotentialDistributorCompetitorCompanyMstrList(List<TblPotentialDistributorCompetitorCompanyMstr> tblPotentialDistributorCompetitorCompanyMstrList) {
        this.tblPotentialDistributorCompetitorCompanyMstrList = tblPotentialDistributorCompetitorCompanyMstrList;
    }

    public List<TblPotentialDistributorCompetitorBrandMstr> getTblPotentialDistributorCompetitorBrandMstrList() {
        return tblPotentialDistributorCompetitorBrandMstrList;
    }

    public void setTblPotentialDistributorCompetitorBrandMstrList(List<TblPotentialDistributorCompetitorBrandMstr> tblPotentialDistributorCompetitorBrandMstrList) {
        this.tblPotentialDistributorCompetitorBrandMstrList = tblPotentialDistributorCompetitorBrandMstrList;
    }

    public List<TblPotentialDistributorRetailersFeedBackDetails> getTblPotentialDistributorRetailersFeedBackDetailsList() {
        return tblPotentialDistributorRetailersFeedBackDetailsList;
    }

    public void setTblPotentialDistributorRetailersFeedBackDetailsList(List<TblPotentialDistributorRetailersFeedBackDetails> tblPotentialDistributorRetailersFeedBackDetailsList) {
        this.tblPotentialDistributorRetailersFeedBackDetailsList = tblPotentialDistributorRetailersFeedBackDetailsList;
    }

    public String getPDACode() {
        return PDACode;
    }

    public void setPDACode(String PDACode) {
        this.PDACode = PDACode;
    }

    public int getFlgDelete() {
        return flgDelete;
    }

    public void setFlgDelete(int flgDelete) {
        this.flgDelete = flgDelete;
    }

    public int getIsOldDistributorReplaced() {
        return IsOldDistributorReplaced;
    }

    public void setIsOldDistributorReplaced(int isOldDistributorReplaced) {
        IsOldDistributorReplaced = isOldDistributorReplaced;
    }

    public int getIsOldDistributorDFinalPaymentDone() {
        return IsOldDistributorDFinalPaymentDone;
    }

    public void setIsOldDistributorDFinalPaymentDone(int isOldDistributorDFinalPaymentDone) {
        IsOldDistributorDFinalPaymentDone = isOldDistributorDFinalPaymentDone;
    }

    public int getFlgDistributorSS() {
        return flgDistributorSS;
    }

    public void setFlgDistributorSS(int flgDistributorSS) {
        this.flgDistributorSS = flgDistributorSS;
    }

    public int getIsNewLocation() {
        return IsNewLocation;
    }

    public void setIsNewLocation(int isNewLocation) {
        IsNewLocation = isNewLocation;
    }

    public int getFlgTownDistributorSubD() {
        return flgTownDistributorSubD;
    }

    public void setFlgTownDistributorSubD(int flgTownDistributorSubD) {
        this.flgTownDistributorSubD = flgTownDistributorSubD;
    }

    public String getAddress_Godown() {
        return Address_Godown;
    }

    public void setAddress_Godown(String address_Godown) {
        if(address_Godown!=null)
        Address_Godown = address_Godown;
    }

    public String getPincode_Godown() {
        return Pincode_Godown;
    }

    public void setPincode_Godown(String pincode_Godown) {
        if(pincode_Godown!=null)
        Pincode_Godown = pincode_Godown;
    }

    public String getCity_Godown() {
        return City_Godown;
    }

    public void setCity_Godown(String city_Godown) {
        if(city_Godown!=null)
        City_Godown = city_Godown;
    }

    public String getDistrict_Godown() {
        return District_Godown;
    }

    public void setDistrict_Godown(String district_Godown) {
        if(district_Godown!=null)
        District_Godown = district_Godown;
    }

    public String getState_Godown() {
        return State_Godown;
    }

    public void setState_Godown(String state_Godown) {
        if(state_Godown!=null)
        State_Godown = state_Godown;
    }

    public int getFlgProprietorPartner() {
        return flgProprietorPartner;
    }

    public void setFlgProprietorPartner(int flgProprietorPartner) {
        this.flgProprietorPartner = flgProprietorPartner;
    }

    public String getBankAccountNumber() {
        return BankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        if(bankAccountNumber!=null)
        BankAccountNumber = bankAccountNumber;
    }

    public String getIFSCCode() {
        return IFSCCode;
    }

    public void setIFSCCode(String IFSCCode) {
        if(IFSCCode!=null)
        this.IFSCCode = IFSCCode;
    }

    public String getBankAddress() {
        return BankAddress;
    }

    public void setBankAddress(String bankAddress) {
        if(bankAddress!=null)
        BankAddress = bankAddress;
    }

    public String getExpectedBusiness() {
        return ExpectedBusiness;
    }

    public void setExpectedBusiness(String expectedBusiness) {
        if(expectedBusiness!=null)
        ExpectedBusiness = expectedBusiness;
    }

    public String getReqGodownSpace() {
        return ReqGodownSpace;
    }

    public void setReqGodownSpace(String reqGodownSpace) {
        if(reqGodownSpace!=null)
        ReqGodownSpace = reqGodownSpace;
    }

    public String getAgreedGodownSpace() {
        return AgreedGodownSpace;
    }

    public void setAgreedGodownSpace(String agreedGodownSpace) {
        if(agreedGodownSpace!=null)
        AgreedGodownSpace = agreedGodownSpace;
    }

    public String getAgreedInvestment() {
        return AgreedInvestment;
    }

    public void setAgreedInvestment(String agreedInvestment) {
        if(agreedInvestment!=null)
        AgreedInvestment = agreedInvestment;
    }

    public String getIdealROI() {
        return IdealROI;
    }

    public void setIdealROI(String idealROI) {
        if(idealROI!=null)
        IdealROI = idealROI;
    }

    public String getExpectedROI() {
        return ExpectedROI;
    }

    public void setExpectedROI(String expectedROI) {
        if(expectedROI!=null)
        ExpectedROI = expectedROI;
    }

    public int getIsCheckGiven() {
        return IsCheckGiven;
    }

    public void setIsCheckGiven(int isCheckGiven) {
        IsCheckGiven = isCheckGiven;
    }

    public String getChequeNumber() {
        return ChequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        if(chequeNumber!=null)
        ChequeNumber = chequeNumber;
    }

    public int getIsGSTDetailsSubmitted() {
        return IsGSTDetailsSubmitted;
    }

    public void setIsGSTDetailsSubmitted(int isGSTDetailsSubmitted) {
        IsGSTDetailsSubmitted = isGSTDetailsSubmitted;
    }

    public int getIsProprietorPanSubmited() {
        return IsProprietorPanSubmited;
    }

    public void setIsProprietorPanSubmited(int isProprietorPanSubmited) {
        IsProprietorPanSubmited = isProprietorPanSubmited;
    }

    public String getProprietorPanNumber() {
        return ProprietorPanNumber;
    }

    public void setProprietorPanNumber(String proprietorPanNumber) {
        if(proprietorPanNumber!=null)
        ProprietorPanNumber = proprietorPanNumber;
    }

    public int getIsPartnerDeedSubmitted() {
        return IsPartnerDeedSubmitted;
    }

    public void setIsPartnerDeedSubmitted(int isPartnerDeedSubmitted) {
        IsPartnerDeedSubmitted = isPartnerDeedSubmitted;
    }

    public String getPanNumber_Partner1() {
        return PanNumber_Partner1;
    }

    public void setPanNumber_Partner1(String panNumber_Partner1) {
        if(panNumber_Partner1!=null)
        PanNumber_Partner1 = panNumber_Partner1;
    }

    public String getPanNumber_Partner2() {
        return PanNumber_Partner2;
    }

    public void setPanNumber_Partner2(String panNumber_Partner2) {
        if(panNumber_Partner2!=null)
        PanNumber_Partner2 = panNumber_Partner2;
    }

//tblPotentialDistributorImageMstrList

    List<PotentialDistributorImageTable>imageTableList;

    public List<PotentialDistributorImageTable> getImageTableList() {
        return imageTableList;
    }

    public void setImageTableList(List<PotentialDistributorImageTable> imageTableList) {
        this.imageTableList = imageTableList;
    }

    @Override
    public String toString() {
        return "TblPotentialDistributor{" +
                "PDACode='" + PDACode + '\'' +
                ", NewDBRCode='" + NewDBRCode + '\'' +
                ", NodeID='" + NodeID + '\'' +
                ", NodeType=" + NodeType +
                ", LatCode=" + LatCode +
                ", LongCode=" + LongCode +
                ", Descr='" + Descr + '\'' +
                ", ContactPersonName='" + ContactPersonName + '\'' +
                ", ContactPersonMobileNumber='" + ContactPersonMobileNumber + '\'' +
                ", ContactPersonEMailID='" + ContactPersonEMailID + '\'' +
                ", TelephoneNo='" + TelephoneNo + '\'' +
                ", GSTNumber='" + GSTNumber + '\'' +
                ", AreaCovered='" + AreaCovered + '\'' +
                ", NoOfRetailersCovered='" + NoOfRetailersCovered + '\'' +
                ", GodownAreaSqFt='" + GodownAreaSqFt + '\'' +
                ", MonthlyTurnOver='" + MonthlyTurnOver + '\'' +
                ", tblPotentialDistributorVehicleListDetailsList=" + tblPotentialDistributorVehicleListDetailsList +
                ", OrderTATinDays=" + OrderTATinDays +
                ", CompanyProductInvestmentLacs=" + CompanyProductInvestmentLacs +
                ", RetailerCreditLimit=" + RetailerCreditLimit +
                ", BusinessSince='" + BusinessSince + '\'' +
                ", DistributorReady=" + DistributorReady +
                ", NextFollowupDate='" + NextFollowupDate + '\'' +
                ", DBREmployee_Dispatch=" + DBREmployee_Dispatch +
                ", DBREmployee_Billing=" + DBREmployee_Billing +
                ", DBREmployee_SalesStaff=" + DBREmployee_SalesStaff +
                ", Address='" + Address + '\'' +
                ", Pincode='" + Pincode + '\'' +
                ", City='" + City + '\'' +
                ", District='" + District + '\'' +
                ", State='" + State + '\'' +
                ", EntryPersonNodeID=" + EntryPersonNodeID +
                ", EntryPersonNodeType=" + EntryPersonNodeType +
                ", CreatedDate='" + CreatedDate + '\'' +
                ", flgGSTTaken=" + flgGSTTaken +
                ", VehicleTypeID='" + VehicleTypeID + '\'' +
                ", NoOfVehicles=" + NoOfVehicles +
                ", NoOfRetilersSection=" + NoOfRetilersSection +
                ", flgViewOrEdit=" + flgViewOrEdit +
                ", flgDelete=" + flgDelete +
                ", IsOldDistributorReplaced=" + IsOldDistributorReplaced +
                ", IsOldDistributorDFinalPaymentDone=" + IsOldDistributorDFinalPaymentDone +
                ", flgDistributorSS=" + flgDistributorSS +
                ", IsNewLocation=" + IsNewLocation +
                ", flgTownDistributorSubD=" + flgTownDistributorSubD +
                ", Address_Godown='" + Address_Godown + '\'' +
                ", Pincode_Godown='" + Pincode_Godown + '\'' +
                ", City_Godown='" + City_Godown + '\'' +
                ", District_Godown='" + District_Godown + '\'' +
                ", State_Godown='" + State_Godown + '\'' +
                ", flgProprietorPartner=" + flgProprietorPartner +
                ", IsPANOfFirmSubmitted=" + IsPANOfFirmSubmitted +
                ", BankAccountNumber='" + BankAccountNumber + '\'' +
                ", IFSCCode='" + IFSCCode + '\'' +
                ", BankAddress='" + BankAddress + '\'' +
                ", ExpectedBusiness='" + ExpectedBusiness + '\'' +
                ", ReqGodownSpace='" + ReqGodownSpace + '\'' +
                ", AgreedGodownSpace='" + AgreedGodownSpace + '\'' +
                ", AgreedInvestment='" + AgreedInvestment + '\'' +
                ", IdealROI='" + IdealROI + '\'' +
                ", ExpectedROI='" + ExpectedROI + '\'' +
                ", IsCheckGiven=" + IsCheckGiven +
                ", ChequeNumber='" + ChequeNumber + '\'' +
                ", IsGSTDetailsSubmitted=" + IsGSTDetailsSubmitted +
                ", IsProprietorPanSubmited=" + IsProprietorPanSubmited +
                ", ProprietorPanNumber='" + ProprietorPanNumber + '\'' +
                ", IsPartnerDeedSubmitted=" + IsPartnerDeedSubmitted +
                ", PartnerDeedNumber='" + PartnerDeedNumber + '\'' +
                ", IsPartner1PANSubmitted=" + IsPartner1PANSubmitted +
                ", IsPartner2PANSubmitted=" + IsPartner2PANSubmitted +
                ", PanNumber_Partner1='" + PanNumber_Partner1 + '\'' +
                ", PanNumber_Partner2='" + PanNumber_Partner2 + '\'' +
                ", PANNoOfFirm='" + PANNoOfFirm + '\'' +
                ", flgGodownAddressSameAsOffic=" + flgGodownAddressSameAsOffic +
                ", flgAppointed=" + flgAppointed +
                ", OldDistriutorID=" + OldDistriutorID +
                ", LocationCapturedFrom=" + LocationCapturedFrom +
                ", flgLocationConfirm=" + flgLocationConfirm +
                ", flgRadioButtonSelected=" + flgRadioButtonSelected +
                ", Sstat=" + Sstat +
                ", tblPotentialDistributorCompetitorCompanyMstrList=" + tblPotentialDistributorCompetitorCompanyMstrList +
                ", tblPotentialDistributorCompetitorBrandMstrList=" + tblPotentialDistributorCompetitorBrandMstrList +
                ", tblPotentialDistributorRetailersFeedBackDetailsList=" + tblPotentialDistributorRetailersFeedBackDetailsList +
                ", imageTableList=" + imageTableList +
                '}';
    }
}
