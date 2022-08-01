
package com.astix.rajtraderssfasales.model;

import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblProductADDONScheme;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblProductRelatedScheme;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeMaster;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeMstr;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBenefitsBucketDetails;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBenefitsProductMappingDetail;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBenefitsValueDetail;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBucketDetails;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBucketProductMapping;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabDetail;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeStoreMapping;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllMasterTablesModel {


    public List<TblQuestionsSurvey> getTblQuestionsSurvey() {
        return tblQuestionsSurvey;
    }

    public void setTblQuestionsSurvey(List<TblQuestionsSurvey> tblQuestionsSurvey) {
        this.tblQuestionsSurvey = tblQuestionsSurvey;

    }

    public List<TblOptionSurvey> getTblOptionSurvey() {
        return tblOptionSurvey;
    }

    public void setTblOptionSurvey(List<TblOptionSurvey> tblOptionSurvey) {
        this.tblOptionSurvey = tblOptionSurvey;

    }

    public List<TblNoOrderReasonMaster> getTblNoOrderReasonMaster() {
        return tblNoOrderReasonMaster;
    }

    public void setTblNoOrderReasonMaster(List<TblNoOrderReasonMaster> tblNoOrderReasonMaster) {
        this.tblNoOrderReasonMaster = tblNoOrderReasonMaster;
    }


    @SerializedName("tblAppliedOrderDiscount")
    @Expose
    private List<TblAppliedOrderDiscount> tblAppliedOrderDiscountList = null;

    public List<TblAppliedOrderDiscount> getTblAppliedOrderDiscountList() {
        return tblAppliedOrderDiscountList;
    }

    public void setTblAppliedOrderDiscountList(List<TblAppliedOrderDiscount> tblAppliedOrderDiscountList) {
        this.tblAppliedOrderDiscountList = tblAppliedOrderDiscountList;
    }

    @SerializedName("tblStoreSummaryDetails")
    @Expose
    private List<TblStoreSummaryDetails> tblStoreSummaryDetails = null;

    public List<TblStoreSummaryDetails> getTblStoreSummaryDetails() {
        return tblStoreSummaryDetails;
    }

    public void setTblStoreSummaryDetails(List<TblStoreSummaryDetails> tblStoreSummaryDetails) {
        this.tblStoreSummaryDetails = tblStoreSummaryDetails;
    }

    @SerializedName("tblNoOrderReasonMaser")
    @Expose
    private List<TblNoOrderReasonMaster> tblNoOrderReasonMaster = null;

    @SerializedName("tblStoreListForUpdateMstr")
    @Expose
    private List<TblStoreListForUpdateMstr> tblStoreListForUpdateMstr = null;

    public List<TblStoreListForUpdateMstr> getTblStoreListForUpdateMstr() {
        return tblStoreListForUpdateMstr;
    }

    public void setTblStoreListForUpdateMstr(List<TblStoreListForUpdateMstr> tblStoreListForUpdateMstr) {
        this.tblStoreListForUpdateMstr = tblStoreListForUpdateMstr;
    }


    @SerializedName("tblReplenishmentSKU")
    @Expose
    private List<TblReplenishmentSKU> tblReplenishmentSKUList = null;

    public List<TblReplenishmentSKU> getTblReplenishmentSKUList() {
        return tblReplenishmentSKUList;
    }

    public void setTblReplenishmentSKUList(List<TblReplenishmentSKU> tblReplenishmentSKUList) {
        this.tblReplenishmentSKUList = tblReplenishmentSKUList;
    }

    @SerializedName("tblFocusbrandSKU")
    @Expose
    private List<TblFocusbrandSKU> tblFocusbrandSKUMstrList = null;

    public List<TblFocusbrandSKU> getTblFocusbrandSKUMstrList() {
        return tblFocusbrandSKUMstrList;
    }

    public void setTblFocusbrandSKUMstrList(List<TblFocusbrandSKU> tblFocusbrandSKUMstrList) {
        this.tblFocusbrandSKUMstrList = tblFocusbrandSKUMstrList;
    }

    @SerializedName("tblStoreSuggestedQty")
    @Expose
    private List<TblStoreSuggestedQtyMstr> tblStoreSuggestedQtyMstrList = null;

    public List<TblStoreSuggestedQtyMstr> getTblStoreSuggestedQtyMstrList() {
        return tblStoreSuggestedQtyMstrList;
    }

    public void setTblStoreSuggestedQtyMstrList(List<TblStoreSuggestedQtyMstr> tblStoreSuggestedQtyMstrList) {
        this.tblStoreSuggestedQtyMstrList = tblStoreSuggestedQtyMstrList;
    }


    @SerializedName("tblProductSectorMapping")
    @Expose
    private List<TblProductSectorMapping> tblProductSectorMapping = null;

    public List<TblProductSectorMapping> getTblProductSectorMapping() {
        return tblProductSectorMapping;
    }

    public void setTblProductSectorMapping(List<TblProductSectorMapping> tblProductSectorMapping) {
        this.tblProductSectorMapping = tblProductSectorMapping;
    }

    @SerializedName("tblQuestionsSurvey")
    @Expose
    private List<TblQuestionsSurvey> tblQuestionsSurvey = null;

    @SerializedName("tblInvoiceHeader")
    @Expose
    private List<TblInvoiceHeaderServer> tblInvoiceHeader = null;


    @SerializedName("tblInvoiceDetail")
    @Expose
    private List<TblInvoiceDetailsServer> tblInvoiceDetail = null;


    public List<TblRouteChangeListMstr> getTblRouteChangeListMstr() {
        return tblRouteChangeListMstr;
    }

    public void setTblRouteChangeListMstr(List<TblRouteChangeListMstr> tblRouteChangeListMstr) {
        this.tblRouteChangeListMstr = tblRouteChangeListMstr;
    }

    @SerializedName("tblRouteChangeListMstr")
    @Expose
    private List<TblRouteChangeListMstr> tblRouteChangeListMstr = null;

    @SerializedName("tblOptionSurvey")
    @Expose
    private List<TblOptionSurvey> tblOptionSurvey = null;


    @SerializedName("tblStoreCloseReasonMaster")
    @Expose
    private List<TblStoreCloseReasonMaster> tblStoreCloseReasonMaster = null;


    @SerializedName("tblProductLevelData")
    @Expose
    private List<TblProductLevelData> tblProductLevelData = null;


    @SerializedName("tblPrdMstrTransactionUOMConfig")
    @Expose
    private List<TblPrdMstrTransactionUOMConfig> tblPrdMstrTransactionUOMConfig = null;

    public List<TblPrdMstrTransactionUOMConfig> getTblPrdMstrTransactionUOMConfig() {
        return tblPrdMstrTransactionUOMConfig;
    }

    public void setTblPrdMstrTransactionUOMConfig(List<TblPrdMstrTransactionUOMConfig> tblPrdMstrTransactionUOMConfig) {
        this.tblPrdMstrTransactionUOMConfig = tblPrdMstrTransactionUOMConfig;
    }

    public List<TblProductLevelData> getTblProductLevelData() {
        return tblProductLevelData;
    }

    public void setTblProductLevelData(List<TblProductLevelData> tblProductLevelData) {
        this.tblProductLevelData = tblProductLevelData;
    }

    public List<TblSalesPersonTodaysTarget> getTblSalesPersonTodaysTarget() {
        return tblSalesPersonTodaysTarget;
    }

    public void setTblSalesPersonTodaysTarget(List<TblSalesPersonTodaysTarget> tblSalesPersonTodaysTarget) {
        this.tblSalesPersonTodaysTarget = tblSalesPersonTodaysTarget;
    }

    @SerializedName("tblSalesPersonTodaysTarget")
    @Expose
    private List<TblSalesPersonTodaysTarget> tblSalesPersonTodaysTarget = null;


    public List<TblIsDBRStockSubmitted> getTblIsDBRStockSubmitted() {
        return tblIsDBRStockSubmitted;
    }


   /* public List<TblIncentiveMainMaster> getTblIncentiveMainMaster() {
        return tblIncentiveMainMaster;
    }

    public void setTblIncentiveMainMaster(List<TblIncentiveMainMaster> tblIncentiveMainMaster) {
        this.tblIncentiveMainMaster = tblIncentiveMainMaster;
    }

    @SerializedName("tblIncentiveMainMaster")
    @Expose
    private List<TblIncentiveMainMaster> tblIncentiveMainMaster = null;

    public List<TblIncentiveSecondaryMaster> getTblIncentiveSecondaryMaster() {
        return tblIncentiveSecondaryMaster;
    }

    public void setTblIncentiveSecondaryMaster(List<TblIncentiveSecondaryMaster> tblIncentiveSecondaryMaster) {
        this.tblIncentiveSecondaryMaster = tblIncentiveSecondaryMaster;

    }

    @SerializedName("tblIncentiveSecondaryMaster")
    @Expose
    private List<TblIncentiveSecondaryMaster> tblIncentiveSecondaryMaster = null;


    public Object getTblIncentiveDetailsData() {
        return tblIncentiveDetailsData;
    }

    public void setTblIncentiveDetailsData(Object tblIncentiveDetailsData) {
        this.tblIncentiveDetailsData = tblIncentiveDetailsData;

    }

    @SerializedName("tblIncentiveDetailsData")
    @Expose
    private Object  tblIncentiveDetailsData = null;

    public List<TblIncentiveDetailsColumnsDesc> getTblIncentiveDetailsColumnsDesc() {
        return tblIncentiveDetailsColumnsDesc;
    }

    public void setTblIncentiveDetailsColumnsDesc(List<TblIncentiveDetailsColumnsDesc> tblIncentiveDetailsColumnsDesc) {
        this.tblIncentiveDetailsColumnsDesc = tblIncentiveDetailsColumnsDesc;
    }

    @SerializedName("tblIncentiveDetailsColumnsDesc")
    @Expose
    private List<TblIncentiveDetailsColumnsDesc>  tblIncentiveDetailsColumnsDesc = null;


    public List<TblTotalEarning> getTblTotalEarning() {
        return tblTotalEarning;
    }

    public void setTblTotalEarning(List<TblTotalEarning> tblTotalEarning) {
        this.tblTotalEarning = tblTotalEarning;
    }

    @SerializedName("tblTotalEarning")
    @Expose
    private List<TblTotalEarning>  tblTotalEarning = null;

    public Object getTblIncentivePastDetailsData() {
        return tblIncentivePastDetailsData;
    }

    public void setTblIncentivePastDetailsData(Object tblIncentivePastDetailsData) {
        this.tblIncentivePastDetailsData = tblIncentivePastDetailsData;
    }

    @SerializedName("tblIncentivePastDetailsData")
    @Expose
    private Object  tblIncentivePastDetailsData = null;


    public List<TblIncentivePastDetailsColumnsDesc> getTblIncentivePastDetailsColumnsDesc() {
        return tblIncentivePastDetailsColumnsDesc;
    }

    public void setTblIncentivePastDetailsColumnsDesc(List<TblIncentivePastDetailsColumnsDesc> tblIncentivePastDetailsColumnsDesc) {
        this.tblIncentivePastDetailsColumnsDesc = tblIncentivePastDetailsColumnsDesc;
    }

    @SerializedName("tblIncentivePastDetailsColumnsDesc")
    @Expose
    private List<TblIncentivePastDetailsColumnsDesc>  tblIncentivePastDetailsColumnsDesc = null;

    public List<TblIncentiveMsgToDisplay> getTblIncentiveMsgToDisplay() {
        return tblIncentiveMsgToDisplay;
    }

    public void setTblIncentiveMsgToDisplay(List<TblIncentiveMsgToDisplay> tblIncentiveMsgToDisplay) {
        this.tblIncentiveMsgToDisplay = tblIncentiveMsgToDisplay;
    }

    @SerializedName("tblIncentiveMsgToDisplay")
    @Expose
    private List<TblIncentiveMsgToDisplay>  tblIncentiveMsgToDisplay = null;

    public List<TblIncentiveBankDetails> getTblIncentiveBankDetails() {
        return tblIncentiveBankDetails;
    }

    public void setTblIncentiveBankDetails(List<TblIncentiveBankDetails> tblIncentiveBankDetails) {
        this.tblIncentiveBankDetails = tblIncentiveBankDetails;
    }

    @SerializedName("tblIncentiveBankDetails")
    @Expose
    private List<TblIncentiveBankDetails>  tblIncentiveBankDetails = null;
*/
   /* tblIncentiveMainMaster
            tblIncentiveSecondaryMaster
    tblIncentiveDetailsData
            tblIncentiveDetailsColumnsDesc
    tblTotalEarning
            tblIncentivePastDetailsData
    tblIncentivePastDetailsColumnsDesc
            tblIncentiveMsgToDisplay
    tblIncentiveBankDetails*/

    public void setTblIsDBRStockSubmitted(List<TblIsDBRStockSubmitted> tblIsDBRStockSubmitted) {
        this.tblIsDBRStockSubmitted = tblIsDBRStockSubmitted;
    }

    @SerializedName("tblIsDBRStockSubmitted")
    @Expose
    private List<TblIsDBRStockSubmitted> tblIsDBRStockSubmitted = null;

    @SerializedName("tblStockUploadedStatus")
    @Expose
    private List<TblStockUploadedStatus> tblStockUploadedStatus = null;

    @SerializedName("tblVanProductStock")
    @Expose
    private List<TblVanProductStock> tblVanProductStock = null;

    @SerializedName("tblCycleID")
    @Expose
    private List<TblCycleID> tblCycleID = null;

    @SerializedName("tblVanStockOutFlg")
    @Expose
    private List<TblVanStockOutFlg> tblVanStockOutFlg = null;

    @SerializedName("tblVanIDOrderIDLeft")
    @Expose
    private List<TblVanIDOrderIDLeft> tblVanIDOrderIDLeft = null;

    @SerializedName("tblInvoiceCaption")
    @Expose
    private List<TblInvoiceCaption> tblInvoiceCaption = null;

    @SerializedName("tblGetReturnsReasonForPDAMstr")
    @Expose
    private List<TblGetReturnsReasonForPDAMstr> tblGetReturnsReasonForPDAMstr = null;

    @SerializedName("tblIsSchemeApplicable")
    @Expose
    private List<TblIsSchemeApplicable> tblIsSchemeApplicable = null;

    @SerializedName("tblStoreListMaster")
    @Expose
    private List<TblStoreListMaster> tblStoreListMaster = null;

    public List<TblStockUploadedStatus> getTblStockUploadedStatus() {
        return tblStockUploadedStatus;
    }

    public void setTblStockUploadedStatus(List<TblStockUploadedStatus> tblStockUploadedStatus) {
        this.tblStockUploadedStatus = tblStockUploadedStatus;
    }

    public List<TblVanProductStock> getTblVanProductStock() {
        return tblVanProductStock;
    }

    public void setTblVanProductStock(List<TblVanProductStock> tblVanProductStock) {
        this.tblVanProductStock = tblVanProductStock;
    }

    public List<TblCycleID> getTblCycleID() {
        return tblCycleID;
    }

    public void setTblCycleID(List<TblCycleID> tblCycleID) {
        this.tblCycleID = tblCycleID;
    }

    public List<TblVanStockOutFlg> getTblVanStockOutFlg() {
        return tblVanStockOutFlg;
    }

    public void setTblVanStockOutFlg(List<TblVanStockOutFlg> tblVanStockOutFlg) {
        this.tblVanStockOutFlg = tblVanStockOutFlg;
    }

    public List<TblVanIDOrderIDLeft> getTblVanIDOrderIDLeft() {
        return tblVanIDOrderIDLeft;
    }

    public void setTblVanIDOrderIDLeft(List<TblVanIDOrderIDLeft> tblVanIDOrderIDLeft) {
        this.tblVanIDOrderIDLeft = tblVanIDOrderIDLeft;
    }

    public List<TblInvoiceCaption> getTblInvoiceCaption() {
        return tblInvoiceCaption;
    }

    public void setTblInvoiceCaption(List<TblInvoiceCaption> tblInvoiceCaption) {
        this.tblInvoiceCaption = tblInvoiceCaption;
    }

    public List<TblGetReturnsReasonForPDAMstr> getTblGetReturnsReasonForPDAMstr() {
        return tblGetReturnsReasonForPDAMstr;
    }

    public void setTblGetReturnsReasonForPDAMstr(List<TblGetReturnsReasonForPDAMstr> tblGetReturnsReasonForPDAMstr) {
        this.tblGetReturnsReasonForPDAMstr = tblGetReturnsReasonForPDAMstr;
    }

    public List<TblIsSchemeApplicable> getTblIsSchemeApplicable() {
        return tblIsSchemeApplicable;
    }

    public void setTblIsSchemeApplicable(List<TblIsSchemeApplicable> tblIsSchemeApplicable) {
        this.tblIsSchemeApplicable = tblIsSchemeApplicable;
    }

    public List<TblStoreListMaster> getTblStoreListMaster() {
        return tblStoreListMaster;
    }

    public void setTblStoreListMaster(List<TblStoreListMaster> tblStoreListMaster) {
        this.tblStoreListMaster = tblStoreListMaster;
    }

    public List<TblStoreListWithPaymentAddress> getTblStoreListWithPaymentAddress() {
        return tblStoreListWithPaymentAddress;
    }

    public void setTblStoreListWithPaymentAddress(List<TblStoreListWithPaymentAddress> tblStoreListWithPaymentAddress) {
        this.tblStoreListWithPaymentAddress = tblStoreListWithPaymentAddress;
    }

    public List<TblStoreSomeProdQuotePriceMstr> getTblStoreSomeProdQuotePriceMstr() {
        return tblStoreSomeProdQuotePriceMstr;
    }

    public void setTblStoreSomeProdQuotePriceMstr(List<TblStoreSomeProdQuotePriceMstr> tblStoreSomeProdQuotePriceMstr) {
        this.tblStoreSomeProdQuotePriceMstr = tblStoreSomeProdQuotePriceMstr;
    }

    public List<TblStoreLastDeliveryNoteNumber> getTblStoreLastDeliveryNoteNumber() {
        return tblStoreLastDeliveryNoteNumber;
    }

    public void setTblStoreLastDeliveryNoteNumber(List<TblStoreLastDeliveryNoteNumber> tblStoreLastDeliveryNoteNumber) {
        this.tblStoreLastDeliveryNoteNumber = tblStoreLastDeliveryNoteNumber;
    }

    @SerializedName("tblStoreListWithPaymentAddress")
    @Expose
    private List<TblStoreListWithPaymentAddress> tblStoreListWithPaymentAddress = null;

    @SerializedName("tblStoreSomeProdQuotePriceMstr")
    @Expose
    private List<TblStoreSomeProdQuotePriceMstr> tblStoreSomeProdQuotePriceMstr = null;

    @SerializedName("tblStoreLastDeliveryNoteNumber")
    @Expose
    private List<TblStoreLastDeliveryNoteNumber> tblStoreLastDeliveryNoteNumber = null;


    public List<TblDayStartAttendanceOption> getTblDayStartAttendanceOptions() {
        return tblDayStartAttendanceOptions;
    }

    public void setTblDayStartAttendanceOptions(List<TblDayStartAttendanceOption> tblDayStartAttendanceOptions) {
        this.tblDayStartAttendanceOptions = tblDayStartAttendanceOptions;
    }

    @SerializedName("tblDayStartAttendanceOptions")
    @Expose
    private List<TblDayStartAttendanceOption> tblDayStartAttendanceOptions = null;

    @SerializedName("tblProductListLastVisitStockOrOrderMstr")
    @Expose
    private List<TblProductListLastVisitStockOrOrderMstr> tblProductListLastVisitStockOrOrderMstr = null;

    @SerializedName("tblPDAGetLODQty")
    @Expose
    private List<TblPDAGetLODQty> tblPDAGetLODQty = null;

    @SerializedName("tblPDAGetLastVisitDate")
    @Expose
    private List<TblPDAGetLastVisitDate> tblPDAGetLastVisitDate = null;

    @SerializedName("tblPDAGetLastOrderDate")
    @Expose
    private List<TblPDAGetLastOrderDate> tblPDAGetLastOrderDate = null;

    @SerializedName("tblPDAGetLastVisitDetails")
    @Expose
    private List<TblPDAGetLastVisitDetails> tblPDAGetLastVisitDetails = null;

    public List<TblProductListLastVisitStockOrOrderMstr> getTblProductListLastVisitStockOrOrderMstr() {
        return tblProductListLastVisitStockOrOrderMstr;
    }

    public void setTblProductListLastVisitStockOrOrderMstr(List<TblProductListLastVisitStockOrOrderMstr> tblProductListLastVisitStockOrOrderMstr) {
        this.tblProductListLastVisitStockOrOrderMstr = tblProductListLastVisitStockOrOrderMstr;
    }

    public List<TblPDAGetLODQty> getTblPDAGetLODQty() {
        return tblPDAGetLODQty;
    }

    public void setTblPDAGetLODQty(List<TblPDAGetLODQty> tblPDAGetLODQty) {
        this.tblPDAGetLODQty = tblPDAGetLODQty;
    }

    public List<TblPDAGetLastVisitDate> getTblPDAGetLastVisitDate() {
        return tblPDAGetLastVisitDate;
    }

    public void setTblPDAGetLastVisitDate(List<TblPDAGetLastVisitDate> tblPDAGetLastVisitDate) {
        this.tblPDAGetLastVisitDate = tblPDAGetLastVisitDate;
    }

    public List<TblPDAGetLastOrderDate> getTblPDAGetLastOrderDate() {
        return tblPDAGetLastOrderDate;
    }

    public void setTblPDAGetLastOrderDate(List<TblPDAGetLastOrderDate> tblPDAGetLastOrderDate) {
        this.tblPDAGetLastOrderDate = tblPDAGetLastOrderDate;
    }

    public List<TblPDAGetLastVisitDetails> getTblPDAGetLastVisitDetails() {
        return tblPDAGetLastVisitDetails;
    }

    public void setTblPDAGetLastVisitDetails(List<TblPDAGetLastVisitDetails> tblPDAGetLastVisitDetails) {
        this.tblPDAGetLastVisitDetails = tblPDAGetLastVisitDetails;
    }

    public List<TblPDAGetLastOrderDetails> getTblPDAGetLastOrderDetails() {
        return tblPDAGetLastOrderDetails;
    }

    public void setTblPDAGetLastOrderDetails(List<TblPDAGetLastOrderDetails> tblPDAGetLastOrderDetails) {
        this.tblPDAGetLastOrderDetails = tblPDAGetLastOrderDetails;
    }

    public List<TblPDAGetLastOrderDetailsTotalValues> getTblPDAGetLastOrderDetailsTotalValues() {
        return tblPDAGetLastOrderDetailsTotalValues;
    }

    public void setTblPDAGetLastOrderDetailsTotalValues(List<TblPDAGetLastOrderDetailsTotalValues> tblPDAGetLastOrderDetailsTotalValues) {
        this.tblPDAGetLastOrderDetailsTotalValues = tblPDAGetLastOrderDetailsTotalValues;
    }

    public List<TblPDAGetExecutionSummary> getTblPDAGetExecutionSummary() {
        return tblPDAGetExecutionSummary;
    }

    public void setTblPDAGetExecutionSummary(List<TblPDAGetExecutionSummary> tblPDAGetExecutionSummary) {
        this.tblPDAGetExecutionSummary = tblPDAGetExecutionSummary;
    }

    public List<TblLastOutstanding> getTblLastOutstanding() {
        return tblLastOutstanding;
    }

    public void setTblLastOutstanding(List<TblLastOutstanding> tblLastOutstanding) {
        this.tblLastOutstanding = tblLastOutstanding;
    }

    public List<TblInvoiceLastVisitDetails> getTblInvoiceLastVisitDetails() {
        return tblInvoiceLastVisitDetails;
    }

    public void setTblInvoiceLastVisitDetails(List<TblInvoiceLastVisitDetails> tblInvoiceLastVisitDetails) {
        this.tblInvoiceLastVisitDetails = tblInvoiceLastVisitDetails;
    }

    @SerializedName("tblPDAGetLastOrderDetails")
    @Expose
    private List<TblPDAGetLastOrderDetails> tblPDAGetLastOrderDetails = null;

    @SerializedName("tblPDAGetLastOrderDetailsTotalValues")
    @Expose
    private List<TblPDAGetLastOrderDetailsTotalValues> tblPDAGetLastOrderDetailsTotalValues = null;

    @SerializedName("tblPDAGetExecutionSummary")
    @Expose
    private List<TblPDAGetExecutionSummary> tblPDAGetExecutionSummary = null;

    @SerializedName("tblLastOutstanding")
    @Expose
    private List<TblLastOutstanding> tblLastOutstanding = null;

    @SerializedName("tblInvoiceLastVisitDetails")
    @Expose
    private List<TblInvoiceLastVisitDetails> tblInvoiceLastVisitDetails = null;


    @SerializedName("tblUOMMaster")
    @Expose
    private List<TblUOMMaster> tblUOMMaster = null;

    public List<TblUOMMaster> getTblUOMMaster() {
        return tblUOMMaster;
    }

    public void setTblUOMMaster(List<TblUOMMaster> tblUOMMaster) {
        this.tblUOMMaster = tblUOMMaster;
    }

    public List<TblUOMMapping> getTblUOMMapping() {
        return tblUOMMapping;
    }

    public void setTblUOMMapping(List<TblUOMMapping> tblUOMMapping) {
        this.tblUOMMapping = tblUOMMapping;
    }

    public List<TblManagerMstr> getTblManagerMstr() {
        return tblManagerMstr;
    }

    public void setTblManagerMstr(List<TblManagerMstr> tblManagerMstr) {
        this.tblManagerMstr = tblManagerMstr;
    }

    public List<TblCategoryMaster> getTblCategoryMaster() {
        return tblCategoryMaster;
    }

    public void setTblCategoryMaster(List<TblCategoryMaster> tblCategoryMaster) {
        this.tblCategoryMaster = tblCategoryMaster;
    }

    public List<TblBankMaster> getTblBankMaster() {
        return tblBankMaster;
    }

    public void setTblBankMaster(List<TblBankMaster> tblBankMaster) {
        this.tblBankMaster = tblBankMaster;
    }

    public List<TblInstrumentMaster> getTblInstrumentMaster() {
        return tblInstrumentMaster;
    }

    public void setTblInstrumentMaster(List<TblInstrumentMaster> tblInstrumentMaster) {
        this.tblInstrumentMaster = tblInstrumentMaster;
    }

    @SerializedName("tblUOMMapping")
    @Expose
    private List<TblUOMMapping> tblUOMMapping = null;

    @SerializedName("tblManagerMstr")
    @Expose
    private List<TblManagerMstr> tblManagerMstr = null;

    @SerializedName("tblCategoryMaster")
    @Expose
    private List<TblCategoryMaster> tblCategoryMaster = null;

    @SerializedName("tblBankMaster")
    @Expose
    private List<TblBankMaster> tblBankMaster = null;

    @SerializedName("tblInstrumentMaster")
    @Expose
    private List<TblInstrumentMaster> tblInstrumentMaster = null;

    public List<TblPreAddedStoresDataDetails> getTblPreAddedStoresDataDetails() {
        return tblPreAddedStoresDataDetails;
    }

    public void setTblPreAddedStoresDataDetails(List<TblPreAddedStoresDataDetails> tblPreAddedStoresDataDetails) {
        this.tblPreAddedStoresDataDetails = tblPreAddedStoresDataDetails;
    }

    public List<TblStateCityMaster> getTblStateCityMaster() {
        return tblStateCityMaster;
    }

    public void setTblStateCityMaster(List<TblStateCityMaster> tblStateCityMaster) {
        this.tblStateCityMaster = tblStateCityMaster;
    }

    public List<TblProductListMaster> getTblProductListMaster() {
        return tblProductListMaster;
    }

    public void setTblProductListMaster(List<TblProductListMaster> tblProductListMaster) {
        this.tblProductListMaster = tblProductListMaster;
    }

    public List<TblProductSegementMap> getTblProductSegementMap() {
        return tblProductSegementMap;
    }

    public void setTblProductSegementMap(List<TblProductSegementMap> tblProductSegementMap) {
        this.tblProductSegementMap = tblProductSegementMap;
    }

    public List<TblPriceApplyType> getTblPriceApplyType() {
        return tblPriceApplyType;
    }

    public void setTblPriceApplyType(List<TblPriceApplyType> tblPriceApplyType) {
        this.tblPriceApplyType = tblPriceApplyType;
    }

    @SerializedName("tblPreAddedStoresDataDetails")
    @Expose
    private List<TblPreAddedStoresDataDetails> tblPreAddedStoresDataDetails = null;

    @SerializedName("tblStateCityMaster")
    @Expose
    private List<TblStateCityMaster> tblStateCityMaster = null;

    @SerializedName("tblProductListMaster")
    @Expose
    private List<TblProductListMaster> tblProductListMaster = null;

    @SerializedName("tblProductSegementMap")
    @Expose
    private List<TblProductSegementMap> tblProductSegementMap = null;

    @SerializedName("tblPriceApplyType")
    @Expose
    private List<TblPriceApplyType> tblPriceApplyType = null;

    public List<TblPreAddedStores> getTblPreAddedStores() {
        return tblPreAddedStores;
    }

    public void setTblPreAddedStores(List<TblPreAddedStores> tblPreAddedStores) {
        this.tblPreAddedStores = tblPreAddedStores;
    }


    public List<TblStoreCountDetails> getTblStoreCountDetails() {
        return tblStoreCountDetails;
    }

    public void setTblStoreCountDetails(List<TblStoreCountDetails> tblStoreCountDetails) {
        this.tblStoreCountDetails = tblStoreCountDetails;
    }

    @SerializedName("tblStoreCountDetails")
    @Expose
    private List<TblStoreCountDetails> tblStoreCountDetails = null;

    @SerializedName("tblPreAddedStores")
    @Expose
    private List<TblPreAddedStores> tblPreAddedStores = null;

    public List<TblPDANotificationMaster> getTblPDANotificationMaster() {
        return tblPDANotificationMaster;
    }

    public void setTblPDANotificationMaster(List<TblPDANotificationMaster> tblPDANotificationMaster) {
        this.tblPDANotificationMaster = tblPDANotificationMaster;
    }

    @SerializedName("tblPDANotificationMaster")
    @Expose
    private List<TblPDANotificationMaster> tblPDANotificationMaster = null;

    public List<TblPDAQuestOptionValuesDependentMstr> getTblPDAQuestOptionValuesDependentMstr() {
        return tblPDAQuestOptionValuesDependentMstr;
    }

    public void setTblPDAQuestOptionValuesDependentMstr(List<TblPDAQuestOptionValuesDependentMstr> tblPDAQuestOptionValuesDependentMstr) {
        this.tblPDAQuestOptionValuesDependentMstr = tblPDAQuestOptionValuesDependentMstr;
    }

    @SerializedName("tblPDAQuestOptionValuesDependentMstr")
    @Expose
    private List<TblPDAQuestOptionValuesDependentMstr> tblPDAQuestOptionValuesDependentMstr = null;

    @SerializedName("tblWarehouseList")
    @Expose
    private List<TblSupplierMstrList> tblSupplierMstrList = null;

    @SerializedName("tblRouteListMaster")
    @Expose
    private List<TblRouteListMaster> tblRouteListMaster = null;

    @SerializedName("tblBloodGroup")
    @Expose
    private List<TblBloodGroup> tblBloodGroup = null;

    @SerializedName("tblEducationQuali")
    @Expose
    private List<TblEducationQuali> tblEducationQuali = null;

    @SerializedName("tblAppMasterFlags")
    @Expose
    private List<TblAppMasterFlags> tblAppMasterFlags = null;


    @SerializedName("tblGetPDAQuestMstr")
    @Expose
    private List<TblGetPDAQuestMstr> tblGetPDAQuestMstr = null;

    @SerializedName("tblQuestIDForOutChannel")
    @Expose
    private List<TblQuestIDForOutChannel> tblQuestIDForOutChannel = null;

    @SerializedName("tblQuestIDForName")
    @Expose
    private List<TblQuestIDForName> tblQuestIDForName = null;

    public List<TblSupplierMstrList> getTblSupplierMstrList() {
        return tblSupplierMstrList;
    }

    public void setTblSupplierMstrList(List<TblSupplierMstrList> tblSupplierMstrList) {
        this.tblSupplierMstrList = tblSupplierMstrList;
    }

    public List<TblRouteListMaster> getTblRouteListMaster() {
        return tblRouteListMaster;
    }

    public void setTblRouteListMaster(List<TblRouteListMaster> tblRouteListMaster) {
        this.tblRouteListMaster = tblRouteListMaster;
    }

    public List<TblBloodGroup> getTblBloodGroup() {
        return tblBloodGroup;
    }

    public void setTblBloodGroup(List<TblBloodGroup> tblBloodGroup) {
        this.tblBloodGroup = tblBloodGroup;
    }

    public List<TblEducationQuali> getTblEducationQuali() {
        return tblEducationQuali;
    }

    public void setTblEducationQuali(List<TblEducationQuali> tblEducationQuali) {
        this.tblEducationQuali = tblEducationQuali;
    }

    public List<TblAppMasterFlags> getTblAppMasterFlags() {
        return tblAppMasterFlags;
    }

    public void setTblAppMasterFlags(List<TblAppMasterFlags> tblAppMasterFlags) {
        this.tblAppMasterFlags = tblAppMasterFlags;
    }

    public List<TblGetPDAQuestMstr> getTblGetPDAQuestMstr() {
        return tblGetPDAQuestMstr;
    }

    public void setTblGetPDAQuestMstr(List<TblGetPDAQuestMstr> tblGetPDAQuestMstr) {
        this.tblGetPDAQuestMstr = tblGetPDAQuestMstr;
    }

    public List<TblQuestIDForOutChannel> getTblQuestIDForOutChannel() {
        return tblQuestIDForOutChannel;
    }

    public void setTblQuestIDForOutChannel(List<TblQuestIDForOutChannel> tblQuestIDForOutChannel) {
        this.tblQuestIDForOutChannel = tblQuestIDForOutChannel;
    }

    public List<TblQuestIDForName> getTblQuestIDForName() {
        return tblQuestIDForName;
    }

    public void setTblQuestIDForName(List<TblQuestIDForName> tblQuestIDForName) {
        this.tblQuestIDForName = tblQuestIDForName;
    }

    public List<TblGetPDAQuestGrpMapping> getTblGetPDAQuestGrpMapping() {
        return tblGetPDAQuestGrpMapping;
    }

    public void setTblGetPDAQuestGrpMapping(List<TblGetPDAQuestGrpMapping> tblGetPDAQuestGrpMapping) {
        this.tblGetPDAQuestGrpMapping = tblGetPDAQuestGrpMapping;
    }


    public List<TblStoreCloseReasonMaster> getTblStoreCloseReasonMaster() {
        return tblStoreCloseReasonMaster;
    }

    public void setTblStoreCloseReasonMaster(List<TblStoreCloseReasonMaster> tblStoreCloseReasonMaster) {
        this.tblStoreCloseReasonMaster = tblStoreCloseReasonMaster;

    }


    public List<TblGetPDAQuestionDependentMstr> getTblGetPDAQuestionDependentMstr() {
        return tblGetPDAQuestionDependentMstr;
    }

    public void setTblGetPDAQuestionDependentMstr(List<TblGetPDAQuestionDependentMstr> tblGetPDAQuestionDependentMstr) {
        this.tblGetPDAQuestionDependentMstr = tblGetPDAQuestionDependentMstr;
    }

    public List<TblPDAQuestOptionDependentMstr> getTblPDAQuestOptionDependentMstr() {
        return tblPDAQuestOptionDependentMstr;
    }

    public void setTblPDAQuestOptionDependentMstr(List<TblPDAQuestOptionDependentMstr> tblPDAQuestOptionDependentMstr) {
        this.tblPDAQuestOptionDependentMstr = tblPDAQuestOptionDependentMstr;
    }

    @SerializedName("tblGetPDAQuestGrpMapping")
    @Expose
    private List<TblGetPDAQuestGrpMapping> tblGetPDAQuestGrpMapping = null;

    @SerializedName("tblGetPDAQuestionDependentMstr")
    @Expose
    private List<TblGetPDAQuestionDependentMstr> tblGetPDAQuestionDependentMstr = null;

    @SerializedName("tblPDAQuestOptionDependentMstr")
    @Expose
    private List<TblPDAQuestOptionDependentMstr> tblPDAQuestOptionDependentMstr = null;


    public List<TblUserName> getTblUserName() {
        return tblUserName;
    }

    public void setTblUserName(List<TblUserName> tblUserName) {
        this.tblUserName = tblUserName;
    }

    @SerializedName("tblUserName")
    @Expose
    private List<TblUserName> tblUserName = null;

    public List<TblGetPDAQuestOptionMstr> getTblGetPDAQuestOptionMstr() {
        return tblGetPDAQuestOptionMstr;
    }

    public void setTblGetPDAQuestOptionMstr(List<TblGetPDAQuestOptionMstr> tblGetPDAQuestOptionMstr) {
        this.tblGetPDAQuestOptionMstr = tblGetPDAQuestOptionMstr;
    }

    @SerializedName("tblGetPDAQuestOptionMstr")
    @Expose
    private List<TblGetPDAQuestOptionMstr> tblGetPDAQuestOptionMstr = null;


    public List<TblPendingInvoices> getTblPendingInvoices() {
        return tblPendingInvoices;
    }

    public void setTblPendingInvoices(List<TblPendingInvoices> tblPendingInvoices) {
        this.tblPendingInvoices = tblPendingInvoices;

    }

    public List<TblInvoiceExecutionProductList> getTblInvoiceExecutionProductList() {
        return tblInvoiceExecutionProductList;
    }

    public void setTblInvoiceExecutionProductList(List<TblInvoiceExecutionProductList> tblInvoiceExecutionProductList) {
        this.tblInvoiceExecutionProductList = tblInvoiceExecutionProductList;

    }

    public List<TblProductWiseInvoice> getTblProductWiseInvoice() {
        return tblProductWiseInvoice;
    }

    public void setTblProductWiseInvoice(List<TblProductWiseInvoice> tblProductWiseInvoice) {
        this.tblProductWiseInvoice = tblProductWiseInvoice;

    }

    @SerializedName("tblPendingInvoices")
    @Expose
    private List<TblPendingInvoices> tblPendingInvoices = null;


    @SerializedName("tblInvoiceExecutionProductList")
    @Expose
    private List<TblInvoiceExecutionProductList> tblInvoiceExecutionProductList = null;


    @SerializedName("tblProductWiseInvoice")
    @Expose
    private List<TblProductWiseInvoice> tblProductWiseInvoice = null;

    @SerializedName("tblForPDAGetPDAStoreSummary")
    @Expose
    private List<TblGetPDAStoreSummary> tblForPDAGetPDAStoreSummary = null;

    @SerializedName("tblStoreSummary")
    @Expose
    private List<TblGetPDAStoreVisitHistory> tblForPDAStoreVisitHistory = null;

    @SerializedName("tblStoreTARSSummary")
    @Expose
    private List<TblGetPDAStoreVisitHistory> tblStoreTARSSummary = null;

    public List<TblGetPDAStoreVisitHistory> getTblStoreTARSSummary() {
        return tblStoreTARSSummary;
    }

    public void setTblStoreTARSSummary(List<TblGetPDAStoreVisitHistory> tblStoreTARSSummary) {
        this.tblStoreTARSSummary = tblStoreTARSSummary;
    }

    @SerializedName("tblOrderHistory")
    @Expose
    private List<TblGetPDAStoreOrderHistory> tblForPDAStoreOrderHistory = null;

    public List<TblGetPDAStoreOrderHistory> getTblForPDAStoreOrderHistory() {
        return tblForPDAStoreOrderHistory;
    }

    public void setTblForPDAStoreOrderHistory(List<TblGetPDAStoreOrderHistory> tblForPDAStoreOrderHistory) {
        this.tblForPDAStoreOrderHistory = tblForPDAStoreOrderHistory;
    }


    @SerializedName("tblUOMTypeConverstion")
    @Expose
    private List<TblUOMTypeConverstion> tblUOMTypeConverstionList = null;

    public List<TblUOMTypeConverstion> getTblUOMTypeConverstionList() {
        return tblUOMTypeConverstionList;
    }

    public void setTblUOMTypeConverstionList(List<TblUOMTypeConverstion> tblUOMTypeConverstionList) {
        this.tblUOMTypeConverstionList = tblUOMTypeConverstionList;
    }

    @SerializedName("tblDistributorProductStock")
    @Expose
    private List<TblDistributorProductStock> tblDistributorProductStock = null;
    @SerializedName("tblDistributorIDOrderIDLeft")
    @Expose
    private List<TblDistributorIDOrderIDLeft> tblDistributorIDOrderIDLeft = null;

    public List<TblDistributorProductStock> getTblDistributorProductStock() {
        return tblDistributorProductStock;
    }

    public void setTblDistributorProductStock(List<TblDistributorProductStock> tblDistributorProductStock) {
        this.tblDistributorProductStock = tblDistributorProductStock;
    }

    public List<TblGetPDAStoreVisitHistory> getTblForPDAStoreVisitHistory() {
        return tblForPDAStoreVisitHistory;
    }

    public void setTblForPDAStoreVisitHistory(List<TblGetPDAStoreVisitHistory> tblForPDAStoreVisitHistory) {
        this.tblForPDAStoreVisitHistory = tblForPDAStoreVisitHistory;
    }

    public List<TblDistributorIDOrderIDLeft> getTblDistributorIDOrderIDLeft() {
        return tblDistributorIDOrderIDLeft;
    }

    public void setTblDistributorIDOrderIDLeft(List<TblDistributorIDOrderIDLeft> tblDistributorIDOrderIDLeft) {
        this.tblDistributorIDOrderIDLeft = tblDistributorIDOrderIDLeft;
    }

    public List<TblGetPDAStoreSummary> getTblForPDAGetPDAStoreSummary() {
        return tblForPDAGetPDAStoreSummary;
    }

    public void setTblForPDAGetPDAStoreSummary(List<TblGetPDAStoreSummary> tblForPDAGetPDAStoreSummary) {
        this.tblForPDAGetPDAStoreSummary = tblForPDAGetPDAStoreSummary;
    }

    public List<TblReasonOrderCncl> getTblReasonOrderCncl() {
        return tblReasonOrderCncl;
    }

    public void setTblReasonOrderCncl(List<TblReasonOrderCncl> tblReasonOrderCncl) {
        this.tblReasonOrderCncl = tblReasonOrderCncl;
    }

    @SerializedName("tblReasonOrderCncl")
    @Expose
    private List<TblReasonOrderCncl> tblReasonOrderCncl = null;

  /*


    public List<TblDayStartAttendanceOption> getTblDayStartAttendanceOption() {
        return tblDayStartAttendanceOptions;
    }
    public void setTblDayStartAttendanceOption(List<TblDayStartAttendanceOption> tblDayStartAttendanceOptions) {
        this.tblDayStartAttendanceOptions = tblDayStartAttendanceOptions;
    }



    public List<TblSupplierMstrList> getTblSupplierMstrList() {
        return tblSupplierMstrList;
    }

    public void setTblSupplierMstrList(List<TblSupplierMstrList> tblSupplierMstrList) {
        this.tblSupplierMstrList = tblSupplierMstrList;
    }

    public List<TblRouteListMaster> getTblRouteListMaster() {
        return tblRouteListMaster;
    }

    public void setTblRouteListMaster(List<TblRouteListMaster> tblRouteListMaster) {
        this.tblRouteListMaster = tblRouteListMaster;
    }

    public List<TblBloodGroup> getTblBloodGroup() {
        return tblBloodGroup;
    }

    public void setTblBloodGroup(List<TblBloodGroup> tblBloodGroup) {
        this.tblBloodGroup = tblBloodGroup;
    }

    public List<TblEducationQuali> getTblEducationQuali() {
        return tblEducationQuali;
    }

    public void setTblEducationQuali(List<TblEducationQuali> tblEducationQuali) {
        this.tblEducationQuali = tblEducationQuali;
    }

    public List<TblAppMasterFlags> getTblAppMasterFlags() {
        return tblAppMasterFlags;
    }

    public void setTblAppMasterFlags(List<TblAppMasterFlags> tblAppMasterFlags) {
        this.tblAppMasterFlags = tblAppMasterFlags;
    }

   *//* public List<TblPDAIMEIGCMRegistration> getTblPDAIMEIGCMRegistration() {
        return tblPDAIMEIGCMRegistration;
    }

    public void setTblPDAIMEIGCMRegistration(List<TblPDAIMEIGCMRegistration> tblPDAIMEIGCMRegistration) {
        this.tblPDAIMEIGCMRegistration = tblPDAIMEIGCMRegistration;
    }*//*

    public List<TblGetPDAQuestMstr> getTblGetPDAQuestMstr() {
        return tblGetPDAQuestMstr;
    }

    public void setTblGetPDAQuestMstr(List<TblGetPDAQuestMstr> tblGetPDAQuestMstr) {
        this.tblGetPDAQuestMstr = tblGetPDAQuestMstr;
    }

    public List<TblQuestIDForOutChannel> getTblQuestIDForOutChannel() {
        return tblQuestIDForOutChannel;
    }

    public void setTblQuestIDForOutChannel(List<TblQuestIDForOutChannel> tblQuestIDForOutChannel) {
        this.tblQuestIDForOutChannel = tblQuestIDForOutChannel;
    }

    public List<TblQuestIDForName> getTblQuestIDForName() {
        return tblQuestIDForName;
    }

    public void setTblQuestIDForName(List<TblQuestIDForName> tblQuestIDForName) {
        this.tblQuestIDForName = tblQuestIDForName;
    }

    public List<TblGetPDAQuestGrpMapping> getTblGetPDAQuestGrpMapping() {
        return tblGetPDAQuestGrpMapping;
    }

    public void setTblGetPDAQuestGrpMapping(List<TblGetPDAQuestGrpMapping> tblGetPDAQuestGrpMapping) {
        this.tblGetPDAQuestGrpMapping = tblGetPDAQuestGrpMapping;
    }

    public List<TblGetPDAQuestionDependentMstr> getTblGetPDAQuestionDependentMstr() {
        return tblGetPDAQuestionDependentMstr;
    }

    public void setTblGetPDAQuestionDependentMstr(List<TblGetPDAQuestionDependentMstr> tblGetPDAQuestionDependentMstr) {
        this.tblGetPDAQuestionDependentMstr = tblGetPDAQuestionDependentMstr;
    }

    public List<TblPDAQuestOptionDependentMstr> getTblPDAQuestOptionDependentMstr() {
        return tblPDAQuestOptionDependentMstr;
    }

    public void setTblPDAQuestOptionDependentMstr(List<TblPDAQuestOptionDependentMstr> tblPDAQuestOptionDependentMstr) {
        this.tblPDAQuestOptionDependentMstr = tblPDAQuestOptionDependentMstr;
    }

    public List<TblPDAQuestOptionValuesDependentMstr> getTblPDAQuestOptionValuesDependentMstr() {
        return tblPDAQuestOptionValuesDependentMstr;
    }

    public void setTblPDAQuestOptionValuesDependentMstr(List<TblPDAQuestOptionValuesDependentMstr> tblPDAQuestOptionValuesDependentMstr) {
        this.tblPDAQuestOptionValuesDependentMstr = tblPDAQuestOptionValuesDependentMstr;
    }

    public List<TblPDANotificationMaster> getTblPDANotificationMaster() {
        return tblPDANotificationMaster;
    }

    public void setTblPDANotificationMaster(List<TblPDANotificationMaster> tblPDANotificationMaster) {
        this.tblPDANotificationMaster = tblPDANotificationMaster;
    }

    public List<TblUserName> getTblUserName() {
        return tblUserName;
    }

    public void setTblUserName(List<TblUserName> tblUserName) {
        this.tblUserName = tblUserName;
    }

    public List<TblStoreCountDetails> getTblStoreCountDetails() {
        return tblStoreCountDetails;
    }

    public void setTblStoreCountDetails(List<TblStoreCountDetails> tblStoreCountDetails) {
        this.tblStoreCountDetails = tblStoreCountDetails;
    }

    public List<TblPreAddedStores> getTblPreAddedStores() {
        return tblPreAddedStores;
    }

    public void setTblPreAddedStores(List<TblPreAddedStores> tblPreAddedStores) {
        this.tblPreAddedStores = tblPreAddedStores;
    }

    public List<TblPreAddedStoresDataDetails> getTblPreAddedStoresDataDetails() {
        return tblPreAddedStoresDataDetails;
    }

    public void setTblPreAddedStoresDataDetails(List<TblPreAddedStoresDataDetails> tblPreAddedStoresDataDetails) {
        this.tblPreAddedStoresDataDetails = tblPreAddedStoresDataDetails;
    }

    public List<TblStateCityMaster> getTblStateCityMaster() {
        return tblStateCityMaster;
    }

    public void setTblStateCityMaster(List<TblStateCityMaster> tblStateCityMaster) {
        this.tblStateCityMaster = tblStateCityMaster;
    }

    public List<TblProductListMaster> getTblProductListMaster() {
        return tblProductListMaster;
    }

    public void setTblProductListMaster(List<TblProductListMaster> tblProductListMaster) {
        this.tblProductListMaster = tblProductListMaster;
    }

    public List<TblProductSegementMap> getTblProductSegementMap() {
        return tblProductSegementMap;
    }

    public void setTblProductSegementMap(List<TblProductSegementMap> tblProductSegementMap) {
        this.tblProductSegementMap = tblProductSegementMap;
    }

    public List<TblPriceApplyType> getTblPriceApplyType() {
        return tblPriceApplyType;
    }

    public void setTblPriceApplyType(List<TblPriceApplyType> tblPriceApplyType) {
        this.tblPriceApplyType = tblPriceApplyType;
    }

    public List<TblUOMMaster> getTblUOMMaster() {
        return tblUOMMaster;
    }

    public void setTblUOMMaster(List<TblUOMMaster> tblUOMMaster) {
        this.tblUOMMaster = tblUOMMaster;
    }

    public List<TblUOMMapping> getTblUOMMapping() {
        return tblUOMMapping;
    }

    public void setTblUOMMapping(List<TblUOMMapping> tblUOMMapping) {
        this.tblUOMMapping = tblUOMMapping;
    }

    public List<TblManagerMstr> getTblManagerMstr() {
        return tblManagerMstr;
    }

    public void setTblManagerMstr(List<TblManagerMstr> tblManagerMstr) {
        this.tblManagerMstr = tblManagerMstr;
    }

    public List<TblCategoryMaster> getTblCategoryMaster() {
        return tblCategoryMaster;
    }

    public void setTblCategoryMaster(List<TblCategoryMaster> tblCategoryMaster) {
        this.tblCategoryMaster = tblCategoryMaster;
    }

    public List<TblBankMaster> getTblBankMaster() {
        return tblBankMaster;
    }

    public void setTblBankMaster(List<TblBankMaster> tblBankMaster) {
        this.tblBankMaster = tblBankMaster;
    }

    public List<TblInstrumentMaster> getTblInstrumentMaster() {
        return tblInstrumentMaster;
    }

    public void setTblInstrumentMaster(List<TblInstrumentMaster> tblInstrumentMaster) {
        this.tblInstrumentMaster = tblInstrumentMaster;
    }

    public List<TblStockUploadedStatus> getTblStockUploadedStatus() {
        return tblStockUploadedStatus;
    }

    public void setTblStockUploadedStatus(List<TblStockUploadedStatus> tblStockUploadedStatus) {
        this.tblStockUploadedStatus = tblStockUploadedStatus;
    }

    public List<TblDistributorProductStock> getTblDistributorProductStock() {
        return tblDistributorProductStock;
    }

    public void setTblDistributorProductStock(List<TblDistributorProductStock> tblDistributorProductStock) {
        this.tblDistributorProductStock = tblDistributorProductStock;
    }

    public List<TblCycleID> getTblCycleID() {
        return tblCycleID;
    }

    public void setTblCycleID(List<TblCycleID> tblCycleID) {
        this.tblCycleID = tblCycleID;
    }

    public List<TblDistributorStockOutFlg> getTblDistributorStockOutFlg() {
        return tblDistributorStockOutFlg;
    }

    public void setTblDistributorStockOutFlg(List<TblDistributorStockOutFlg> tblDistributorStockOutFlg) {
        this.tblDistributorStockOutFlg = tblDistributorStockOutFlg;
    }

    public List<TblDistributorIDOrderIDLeft> getTblDistributorIDOrderIDLeft() {
        return tblDistributorIDOrderIDLeft;
    }

    public void setTblDistributorIDOrderIDLeft(List<TblDistributorIDOrderIDLeft> tblDistributorIDOrderIDLeft) {
        this.tblDistributorIDOrderIDLeft = tblDistributorIDOrderIDLeft;
    }

    public List<TblInvoiceCaption> getTblInvoiceCaption() {
        return tblInvoiceCaption;
    }

    public void setTblInvoiceCaption(List<TblInvoiceCaption> tblInvoiceCaption) {
        this.tblInvoiceCaption = tblInvoiceCaption;
    }

    public List<TblGetReturnsReasonForPDAMstr> getTblGetReturnsReasonForPDAMstr() {
        return tblGetReturnsReasonForPDAMstr;
    }

    public void setTblGetReturnsReasonForPDAMstr(List<TblGetReturnsReasonForPDAMstr> tblGetReturnsReasonForPDAMstr) {
        this.tblGetReturnsReasonForPDAMstr = tblGetReturnsReasonForPDAMstr;
    }

    public List<TblIsSchemeApplicable> getTblIsSchemeApplicable() {
        return tblIsSchemeApplicable;
    }

    public void setTblIsSchemeApplicable(List<TblIsSchemeApplicable> tblIsSchemeApplicable) {
        this.tblIsSchemeApplicable = tblIsSchemeApplicable;
    }

    public List<TblStoreListMaster> getTblStoreListMaster() {
        return tblStoreListMaster;
    }

    public void setTblStoreListMaster(List<TblStoreListMaster> tblStoreListMaster) {
        this.tblStoreListMaster = tblStoreListMaster;
    }

    public List<TblStoreListWithPaymentAddressMR> getTblStoreListWithPaymentAddressMR() {
        return tblStoreListWithPaymentAddressMR;
    }

    public void setTblStoreListWithPaymentAddressMR(List<TblStoreListWithPaymentAddressMR> tblStoreListWithPaymentAddressMR) {
        this.tblStoreListWithPaymentAddressMR = tblStoreListWithPaymentAddressMR;
    }

    public List<TblStoreSomeProdQuotePriceMstr> getTblStoreSomeProdQuotePriceMstr() {
        return tblStoreSomeProdQuotePriceMstr;
    }

    public void setTblStoreSomeProdQuotePriceMstr(List<TblStoreSomeProdQuotePriceMstr> tblStoreSomeProdQuotePriceMstr) {
        this.tblStoreSomeProdQuotePriceMstr = tblStoreSomeProdQuotePriceMstr;
    }

    public List<TblStoreLastDeliveryNoteNumber> getTblStoreLastDeliveryNoteNumber() {
        return tblStoreLastDeliveryNoteNumber;
    }

    public void setTblStoreLastDeliveryNoteNumber(List<TblStoreLastDeliveryNoteNumber> tblStoreLastDeliveryNoteNumber) {
        this.tblStoreLastDeliveryNoteNumber = tblStoreLastDeliveryNoteNumber;
    }

    public List<TblProductListLastVisitStockOrOrderMstr> getTblProductListLastVisitStockOrOrderMstr() {
        return tblProductListLastVisitStockOrOrderMstr;
    }

    public void setTblProductListLastVisitStockOrOrderMstr(List<TblProductListLastVisitStockOrOrderMstr> tblProductListLastVisitStockOrOrderMstr) {
        this.tblProductListLastVisitStockOrOrderMstr = tblProductListLastVisitStockOrOrderMstr;
    }

    public List<TblPDAGetLODQty> getTblPDAGetLODQty() {
        return tblPDAGetLODQty;
    }

    public void setTblPDAGetLODQty(List<TblPDAGetLODQty> tblPDAGetLODQty) {
        this.tblPDAGetLODQty = tblPDAGetLODQty;
    }

    public List<TblPDAGetLastVisitDate> getTblPDAGetLastVisitDate() {
        return tblPDAGetLastVisitDate;
    }

    public void setTblPDAGetLastVisitDate(List<TblPDAGetLastVisitDate> tblPDAGetLastVisitDate) {
        this.tblPDAGetLastVisitDate = tblPDAGetLastVisitDate;
    }

    public List<TblPDAGetLastOrderDate> getTblPDAGetLastOrderDate() {
        return tblPDAGetLastOrderDate;
    }

    public void setTblPDAGetLastOrderDate(List<TblPDAGetLastOrderDate> tblPDAGetLastOrderDate) {
        this.tblPDAGetLastOrderDate = tblPDAGetLastOrderDate;
    }

    public List<TblPDAGetLastVisitDetails> getTblPDAGetLastVisitDetails() {
        return tblPDAGetLastVisitDetails;
    }

    public void setTblPDAGetLastVisitDetails(List<TblPDAGetLastVisitDetails> tblPDAGetLastVisitDetails) {
        this.tblPDAGetLastVisitDetails = tblPDAGetLastVisitDetails;
    }

    public List<TblPDAGetLastOrderDetails> getTblPDAGetLastOrderDetails() {
        return tblPDAGetLastOrderDetails;
    }

    public void setTblPDAGetLastOrderDetails(List<TblPDAGetLastOrderDetails> tblPDAGetLastOrderDetails) {
        this.tblPDAGetLastOrderDetails = tblPDAGetLastOrderDetails;
    }

    public List<TblPDAGetLastOrderDetailsTotalValues> getTblPDAGetLastOrderDetailsTotalValues() {
        return tblPDAGetLastOrderDetailsTotalValues;
    }

    public void setTblPDAGetLastOrderDetailsTotalValues(List<TblPDAGetLastOrderDetailsTotalValues> tblPDAGetLastOrderDetailsTotalValues) {
        this.tblPDAGetLastOrderDetailsTotalValues = tblPDAGetLastOrderDetailsTotalValues;
    }

    public List<TblPDAGetExecutionSummary> getTblPDAGetExecutionSummary() {
        return tblPDAGetExecutionSummary;
    }

    public void setTblPDAGetExecutionSummary(List<TblPDAGetExecutionSummary> tblPDAGetExecutionSummary) {
        this.tblPDAGetExecutionSummary = tblPDAGetExecutionSummary;
    }

    public List<TblLastOutstanding> getTblLastOutstanding() {
        return tblLastOutstanding;
    }

    public void setTblLastOutstanding(List<TblLastOutstanding> tblLastOutstanding) {
        this.tblLastOutstanding = tblLastOutstanding;
    }

    public List<TblInvoiceLastVisitDetails> getTblInvoiceLastVisitDetails() {
        return tblInvoiceLastVisitDetails;
    }

    public void setTblInvoiceLastVisitDetails(List<TblInvoiceLastVisitDetails> tblInvoiceLastVisitDetails) {
        this.tblInvoiceLastVisitDetails = tblInvoiceLastVisitDetails;
    }

*/

    @SerializedName("tblSchememaster")
    @Expose
    private List<TblSchemeMaster> tblSchememaster;
    @SerializedName("tblSchemeStoreMapping")
    @Expose
    private List<TblSchemeStoreMapping> tblSchemeStoreMapping = null;
    @SerializedName("tblSchemeMstr")
    @Expose
    private final List<TblSchemeMstr> tblSchemeMstr = null;
    @SerializedName("tblSchemeSlabDetail")
    @Expose
    private final List<TblSchemeSlabDetail> tblSchemeSlabDetail = null;
    @SerializedName("tblSchemeSlabBucketDetails")
    @Expose
    private final List<TblSchemeSlabBucketDetails> tblSchemeSlabBucketDetails = null;
    @SerializedName("tblSchemeSlabBucketProductMapping")
    @Expose
    private List<TblSchemeSlabBucketProductMapping> tblSchemeSlabBucketProductMappings = null;
    @SerializedName("tblSchemeSlabBenefitsBucketDetails")
    @Expose
    private List<TblSchemeSlabBenefitsBucketDetails> tblSchemeSlabBenefitsBucketDetails = null;
    @SerializedName("tblSchemeSlabBenefitsProductMappingDetail")
    @Expose
    private List<TblSchemeSlabBenefitsProductMappingDetail> tblSchemeSlabBenefitsProductMappingDetail = null;
    @SerializedName("tblSchemeSlabBenefitsValueDetail")
    @Expose
    private List<TblSchemeSlabBenefitsValueDetail> tblSchemeSlabBenefitsValueDetail = null;
    @SerializedName("tblProductRelatedScheme")
    @Expose
    private List<TblProductRelatedScheme> tblProductRelatedScheme = null;
    @SerializedName("tblProductADDONScheme")
    @Expose
    private List<TblProductADDONScheme> tblProductADDONScheme = null;

    public List<TblSchemeMaster> getTblSchememaster() {
        return tblSchememaster;
    }

    public void setTblSchememaster(List<TblSchemeMaster> tblSchememaster) {
        this.tblSchememaster = tblSchememaster;
    }

    public List<TblSchemeStoreMapping> getTblSchemeStoreMapping() {
        return tblSchemeStoreMapping;
    }

    public void setTblSchemeStoreMapping(List<TblSchemeStoreMapping> tblSchemeStoreMapping) {
        this.tblSchemeStoreMapping = tblSchemeStoreMapping;
    }

    public List<TblSchemeMstr> getTblSchemeMstr() {
        return tblSchemeMstr;
    }

    public List<TblSchemeSlabDetail> getTblSchemeSlabDetail() {
        return tblSchemeSlabDetail;
    }

    public List<TblSchemeSlabBucketDetails> getTblSchemeSlabBucketDetails() {
        return tblSchemeSlabBucketDetails;
    }

    public List<TblSchemeSlabBucketProductMapping> getTblSchemeSlabBucketProductMappings() {
        return tblSchemeSlabBucketProductMappings;
    }

    public void setTblSchemeSlabBucketProductMappings(List<TblSchemeSlabBucketProductMapping> tblSchemeSlabBucketProductMappings) {
        this.tblSchemeSlabBucketProductMappings = tblSchemeSlabBucketProductMappings;
    }

    public List<TblSchemeSlabBenefitsBucketDetails> getTblSchemeSlabBenefitsBucketDetails() {
        return tblSchemeSlabBenefitsBucketDetails;
    }

    public void setTblSchemeSlabBenefitsBucketDetails(List<TblSchemeSlabBenefitsBucketDetails> tblSchemeSlabBenefitsBucketDetails) {
        this.tblSchemeSlabBenefitsBucketDetails = tblSchemeSlabBenefitsBucketDetails;
    }

    public List<TblSchemeSlabBenefitsProductMappingDetail> getTblSchemeSlabBenefitsProductMappingDetail() {
        return tblSchemeSlabBenefitsProductMappingDetail;
    }

    public void setTblSchemeSlabBenefitsProductMappingDetail(List<TblSchemeSlabBenefitsProductMappingDetail> tblSchemeSlabBenefitsProductMappingDetail) {
        this.tblSchemeSlabBenefitsProductMappingDetail = tblSchemeSlabBenefitsProductMappingDetail;
    }

    public List<TblSchemeSlabBenefitsValueDetail> getTblSchemeSlabBenefitsValueDetail() {
        return tblSchemeSlabBenefitsValueDetail;
    }

    public void setTblSchemeSlabBenefitsValueDetail(List<TblSchemeSlabBenefitsValueDetail> tblSchemeSlabBenefitsValueDetail) {
        this.tblSchemeSlabBenefitsValueDetail = tblSchemeSlabBenefitsValueDetail;
    }

    public List<TblProductRelatedScheme> getTblProductRelatedScheme() {
        return tblProductRelatedScheme;
    }

    public void setTblProductRelatedScheme(List<TblProductRelatedScheme> tblProductRelatedScheme) {
        this.tblProductRelatedScheme = tblProductRelatedScheme;
    }

    public List<TblProductADDONScheme> getTblProductADDONScheme() {
        return tblProductADDONScheme;
    }

    public void setTblProductADDONScheme(List<TblProductADDONScheme> tblProductADDONScheme) {
        this.tblProductADDONScheme = tblProductADDONScheme;
    }

    public List<TblInvoiceHeaderServer> getTblInvoiceHeader() {
        return tblInvoiceHeader;
    }

    public void setTblInvoiceHeader(List<TblInvoiceHeaderServer> tblInvoiceHeader) {
        this.tblInvoiceHeader = tblInvoiceHeader;
    }

    public List<TblInvoiceDetailsServer> getTblInvoiceDetail() {
        return tblInvoiceDetail;
    }

    public void setTblInvoiceDetail(List<TblInvoiceDetailsServer> tblInvoiceDetail) {
        this.tblInvoiceDetail = tblInvoiceDetail;

    }
    @SerializedName("tblBeatWiseSpokeStoreVisitSchedule")
    @Expose
    private List<TblBeatPlans> tblBeatPlans;

    @SerializedName("tblBeatPlans")
    @Expose
    private List<TblBeatWiseSpokeStoreVisitSchedule> tblBeatWiseSpokeStoreVisitSchedule;
    public List<TblBeatPlans> getTblBeatPlan() {
        return tblBeatPlans;
    }

    public void setTblBeatPlan(List<TblBeatPlans> tblBeatPlan) {
        this.tblBeatPlans = tblBeatPlan;
    }

    public List<TblBeatWiseSpokeStoreVisitSchedule> getTblBeatWiseSpokeStoreVisitSchedule() {
        return tblBeatWiseSpokeStoreVisitSchedule;
    }

    public void setTblBeatWiseSpokeStoreVisitSchedule(List<TblBeatWiseSpokeStoreVisitSchedule> tblBeatWiseSpokeStoreVisitSchedule) {
        this.tblBeatWiseSpokeStoreVisitSchedule = tblBeatWiseSpokeStoreVisitSchedule;
    }
}
