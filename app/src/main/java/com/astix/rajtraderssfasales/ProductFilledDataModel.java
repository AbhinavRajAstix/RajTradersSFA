package com.astix.rajtraderssfasales;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;

import com.astix.rajtraderssfasales.model.TblSchemePerProduct;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ProductFilledDataModel  {//implements Parcelable

    //hmapCtgryPrdctDetail= key=prdctId,val=ProductFreeQty
    private LinkedHashMap<String, String> hmapPrdctOverAllDiscountToApply = new LinkedHashMap<String, String>();

    LinkedHashMap<String, String> hmapPrdctOrderQtyForDiscountApplyOverAll=new  LinkedHashMap<String, String>();

    public LinkedHashMap<String, String> getHmapPrdctOrderQtyForDiscountApplyOverAll() {
        return hmapPrdctOrderQtyForDiscountApplyOverAll;
    }

    public void setHmapPrdctOrderQtyForDiscountApplyOverAll(LinkedHashMap<String, String> hmapPrdctOrderQtyForDiscountApplyOverAll) {
        this.hmapPrdctOrderQtyForDiscountApplyOverAll = hmapPrdctOrderQtyForDiscountApplyOverAll;
    }

    public LinkedHashMap<String, String> getHmapPrdctOverAllDiscountToApply() {
        return hmapPrdctOverAllDiscountToApply;
    }

    public void setHmapPrdctOverAllDiscountToApply(LinkedHashMap<String, String> hmapPrdctOverAllDiscountToApply) {
        this.hmapPrdctOverAllDiscountToApply = hmapPrdctOverAllDiscountToApply;
    }

    private LinkedHashMap<String, String> hmapPrdctQtyInCartonID = new LinkedHashMap<String, String>();

    private LinkedHashMap<String, String> hmapPrdctNetRate = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> hmapPrdctNetValue = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> hmapPrdctDiscountPerUOM = new LinkedHashMap<String, String>();

    private LinkedHashMap<String, String> hmapPrdctOverAllDiscountCalculated = new LinkedHashMap<String, String>();

    private LinkedHashMap<String, String> hmapNetMarginPercentage = new LinkedHashMap<String, String>();

    public LinkedHashMap<String, String> getHmapPrdctNetRate() {
        return hmapPrdctNetRate;
    }

    public void setHmapPrdctNetRate(LinkedHashMap<String, String> hmapPrdctNetRate) {
        this.hmapPrdctNetRate = hmapPrdctNetRate;
    }

    public LinkedHashMap<String, String> getHmapPrdctNetValue() {
        return hmapPrdctNetValue;
    }

    public void setHmapPrdctNetValue(LinkedHashMap<String, String> hmapPrdctNetValue) {
        this.hmapPrdctNetValue = hmapPrdctNetValue;
    }

    public LinkedHashMap<String, String> getHmapPrdctDiscountPerUOM() {
        return hmapPrdctDiscountPerUOM;
    }

    public void setHmapPrdctDiscountPerUOM(LinkedHashMap<String, String> hmapPrdctDiscountPerUOM) {
        this.hmapPrdctDiscountPerUOM = hmapPrdctDiscountPerUOM;
    }

    public LinkedHashMap<String, String> getHmapPrdctOverAllDiscountCalculated() {
        return hmapPrdctOverAllDiscountCalculated;
    }

    public void setHmapPrdctOverAllDiscountCalculated(LinkedHashMap<String, String> hmapPrdctOverAllDiscountCalculated) {
        this.hmapPrdctOverAllDiscountCalculated = hmapPrdctOverAllDiscountCalculated;
    }

    public LinkedHashMap<String, String> getHmapNetMarginPercentage() {
        return hmapNetMarginPercentage;
    }

    public void setHmapNetMarginPercentage(LinkedHashMap<String, String> hmapNetMarginPercentage) {
        this.hmapNetMarginPercentage = hmapNetMarginPercentage;
    }

    public LinkedHashMap<String, String> getHmapPrdctQtyInCartonID() {
        return hmapPrdctQtyInCartonID;
    }

    public void setHmapPrdctQtyInCartonID(LinkedHashMap<String, String> hmapPrdctQtyInCartonID) {
        this.hmapPrdctQtyInCartonID = hmapPrdctQtyInCartonID;
    }




    public void setPrdctQtyInCartonID(String prdctId, String prdctQty) {
        hmapPrdctQtyInCartonID.put(prdctId, prdctQty);
    }

    public void removePrdctQtyInCartonID(String prdctId) {
        if ((hmapPrdctQtyInCartonID != null)) {
            hmapPrdctQtyInCartonID.remove(prdctId);
        }
    }



    private LinkedHashMap<String, String> hmapPrdctCartonID = new LinkedHashMap<String, String>();

    public LinkedHashMap<String, String> getHmapPrdctCartonID() {
        return hmapPrdctCartonID;
    }



    public void setPrdctCartonID(String prdctId, String prdctQty) {
        hmapPrdctCartonID.put(prdctId, prdctQty);
    }

    public void removePrdctCartonID(String prdctId) {
        if ((hmapPrdctCartonID != null)) {
            hmapPrdctCartonID.remove(prdctId);
        }
    }

    public String getPrdctCartonID(String prdctId) {
        String prdctVal = "0";
        if ((hmapPrdctCartonID != null) && (hmapPrdctCartonID.containsKey(prdctId))) {
            prdctVal = hmapPrdctCartonID.get(prdctId);
        }
        return prdctVal;
    }


    HashMap<String,ArrayList<TblSchemePerProduct>> hmapProductCurrentAppliedSchemeIDandDescrAndColorCode = new HashMap<String,ArrayList<TblSchemePerProduct>>();



    public void removhmapProductCurrentAppliedSchemeIDandDescrAndColorCode(String prdctId) {
        if ((hmapProductCurrentAppliedSchemeIDandDescrAndColorCode != null)) {
            hmapProductCurrentAppliedSchemeIDandDescrAndColorCode.remove(prdctId);
        }
    }

    public  ArrayList<TblSchemePerProduct> getArrListProductCurrentAppliedSchemeIDandDescrAndColorCode(String prdctId) {
        ArrayList<TblSchemePerProduct> tblSchemePerProducts=new  ArrayList<TblSchemePerProduct>();
        if ((hmapProductCurrentAppliedSchemeIDandDescrAndColorCode != null)) {
            if (hmapProductCurrentAppliedSchemeIDandDescrAndColorCode.containsKey(prdctId))
            {
                tblSchemePerProducts=hmapProductCurrentAppliedSchemeIDandDescrAndColorCode.get(prdctId);
            }
        }
        return  tblSchemePerProducts;
    }


    public HashMap<String, ArrayList<TblSchemePerProduct>> getHmapProductCurrentAppliedSchemeIDandDescrAndColorCode() {
        return hmapProductCurrentAppliedSchemeIDandDescrAndColorCode;
    }

    public void setHmapProductCurrentAppliedSchemeIDandDescrAndColorCode(HashMap<String,ArrayList<TblSchemePerProduct>> hmapProductCurrentAppliedSchemeIDandDescrAndColorCode) {
        this.hmapProductCurrentAppliedSchemeIDandDescrAndColorCode = hmapProductCurrentAppliedSchemeIDandDescrAndColorCode;
    }

    HashMap<String, String> hmapPrdctFreeQty = new HashMap<String, String>();
    HashMap<String, String> hmapPrdctFreeQtyFinal = new HashMap<String, String>();
    //hmapProductDiscountPercentageGive= key =ProductID         value=DiscountPercentageGivenOnProduct
    HashMap<String, String> hmapProductDiscountPercentageGive = new HashMap<String, String>();
    HashMap<String, String> hmapPrdctIdPrdctDscnt = new HashMap<String, String>();
    HashMap<String, String> hmapProductDiscountPercentageGiveFinal = new HashMap<String, String>();

    private LinkedHashMap<String,String> hmapPrdctOrderValBeforeGST=new LinkedHashMap<String,String>();


    private LinkedHashMap<String,String> hmapPrdctOrderValTaxAmount=new LinkedHashMap<String,String>();

    HashMap<String, String> hmapProductFlatDiscountGive = new HashMap<String, String>();

    public HashMap<String, String> getHmapProductFlatDiscountGive() {
        return hmapProductFlatDiscountGive;
    }

    public void setHmapProductFlatDiscountGive(HashMap<String, String> hmapProductFlatDiscountGive) {
        this.hmapProductFlatDiscountGive = hmapProductFlatDiscountGive;
    }

    public HashMap<String, String> getHmapProductFlatDiscountGiveFinal() {
        return hmapProductFlatDiscountGiveFinal;
    }

    public void setHmapProductFlatDiscountGiveFinal(HashMap<String, String> hmapProductFlatDiscountGiveFinal) {
        this.hmapProductFlatDiscountGiveFinal = hmapProductFlatDiscountGiveFinal;
    }

    HashMap<String, String> hmapProductFlatDiscountGiveFinal = new HashMap<String, String>();

    //hmapProductVolumePer= key =ProductID         value=Per
    HashMap<String, String> hmapProductVolumePer = new HashMap<String, String>();
    HashMap<String, String> hmapProductVolumePerFinal = new HashMap<String, String>();
    private LinkedHashMap<String, String> hmapPrdctOrderQty = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> hmapPrdctOrderQtyForReverseOrStraightCalculation = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> hmapPrdctOrderVal = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> hmapPrdctRateToApply = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> hmapProductRatePrice = new LinkedHashMap<String, String>();




    private LinkedHashMap<String, String> hmapPrdctOrderValWithTCOrder = new LinkedHashMap<String, String>();

    private LinkedHashMap<String, String> hmapPrdctQtyTCOrder = new LinkedHashMap<String, String>();


    private LinkedHashMap<String, String> hmapOrderQtyDataToShow = new LinkedHashMap<String, String>();
    private LinkedHashMap<String, String> hmapflgPicsOrCases = new LinkedHashMap<String, String>();

    private EditText lastEditText;
    private EditText focusLostEditText;
    private int holderPosition;

    public int getHolderPosition() {
        return holderPosition;
    }

    public void setHolderPosition(int holderPosition) {
        this.holderPosition = holderPosition;
    }

    public LinkedHashMap<String, String> getHmapPrdctOrderQtyForReverseOrStraightCalculation() {
        return hmapPrdctOrderQtyForReverseOrStraightCalculation;
    }

    public void setHmapPrdctOrderQtyForReverseOrStraightCalculation(LinkedHashMap<String, String> hmapPrdctOrderQtyForReverseOrStraightCalculation) {
        this.hmapPrdctOrderQtyForReverseOrStraightCalculation = hmapPrdctOrderQtyForReverseOrStraightCalculation;
    }

    public String getPrdctOrderQtyForReverseOrStraightCalculation(String prdctId) {
        String prdctQty = "";
        if ((hmapPrdctOrderQtyForReverseOrStraightCalculation != null) && (hmapPrdctOrderQtyForReverseOrStraightCalculation.containsKey(prdctId))) {
            prdctQty = hmapPrdctOrderQtyForReverseOrStraightCalculation.get(prdctId);
        }
        return prdctQty;
    }

    public void setPrdctOrderQtyForReverseOrStraightCalculation(String prdctId, String prdctQty) {
        hmapPrdctOrderQtyForReverseOrStraightCalculation.put(prdctId, prdctQty);
    }

    public void removePrdctOrderQtyForReverseOrStraightCalculation(String prdctId) {
        if ((hmapPrdctOrderQtyForReverseOrStraightCalculation != null)) {
            hmapPrdctOrderQtyForReverseOrStraightCalculation.remove(prdctId);
        }
    }

    public HashMap<String, String> getHmapPrdctFreeQty() {
        return hmapPrdctFreeQty;
    }

    public void setHmapPrdctFreeQty(HashMap<String, String> hmapPrdctFreeQty) {
        this.hmapPrdctFreeQty = hmapPrdctFreeQty;
    }

    public HashMap<String, String> getHmapPrdctFreeQtyFinal() {
        return hmapPrdctFreeQtyFinal;
    }

    public void setHmapPrdctFreeQtyFinal(HashMap<String, String> hmapPrdctFreeQtyFinal) {
        this.hmapPrdctFreeQtyFinal = hmapPrdctFreeQtyFinal;
    }

    public HashMap<String, String> getHmapProductDiscountPercentageGive() {
        return hmapProductDiscountPercentageGive;
    }

    public void setHmapProductDiscountPercentageGive(HashMap<String, String> hmapProductDiscountPercentageGive) {
        this.hmapProductDiscountPercentageGive = hmapProductDiscountPercentageGive;
    }

    public HashMap<String, String> getHmapProductDiscountPercentageGiveFinal() {
        return hmapProductDiscountPercentageGiveFinal;
    }

    public void setHmapProductDiscountPercentageGiveFinal(HashMap<String, String> hmapProductDiscountPercentageGiveFinal) {
        this.hmapProductDiscountPercentageGiveFinal = hmapProductDiscountPercentageGiveFinal;
    }

    public HashMap<String, String> getHmapProductVolumePer() {
        return hmapProductVolumePer;
    }

    public void setHmapProductVolumePer(HashMap<String, String> hmapProductVolumePer) {
        this.hmapProductVolumePer = hmapProductVolumePer;
    }

    public HashMap<String, String> getHmapProductVolumePerFinal() {
        return hmapProductVolumePerFinal;
    }

    public void setHmapProductVolumePerFinal(HashMap<String, String> hmapProductVolumePerFinal) {
        this.hmapProductVolumePerFinal = hmapProductVolumePerFinal;
    }

    public HashMap<String, String> getHmapPrdctIdPrdctDscnt() {
        return hmapPrdctIdPrdctDscnt;
    }

    public void setHmapPrdctIdPrdctDscnt(HashMap<String, String> hmapPrdctIdPrdctDscnt) {
        this.hmapPrdctIdPrdctDscnt = hmapPrdctIdPrdctDscnt;
    }

    public void setDiscountValue(String prdctId, String prdctQty) {
        hmapPrdctIdPrdctDscnt.put(prdctId, prdctQty);
    }

    public void removeDisCountValue(String prdctId) {
        if ((hmapPrdctIdPrdctDscnt != null)) {
            hmapPrdctIdPrdctDscnt.remove(prdctId);
        }
    }

    public String getPrdctDisounctAmountAvailed(String prdctId) {
        String prdctVal = "0.0";
        if ((hmapPrdctIdPrdctDscnt != null) && (hmapPrdctIdPrdctDscnt.containsKey(prdctId))) {
            prdctVal = hmapPrdctIdPrdctDscnt.get(prdctId);
        }
        return prdctVal;
    }

    public void setPrdctRate(String prdctId, String prdctRate) {
        hmapProductRatePrice.put(prdctId, prdctRate);
    }

    public double getPrdctRate(String prdctId) {
        double prdctVal = 0.0;
        if ((hmapProductRatePrice != null) && (hmapProductRatePrice.containsKey(prdctId))) {
            prdctVal = Double.parseDouble(hmapProductRatePrice.get(prdctId));
            prdctVal = Double.parseDouble(new DecimalFormat("##.##").format(prdctVal));
        }
        return prdctVal;
    }


    public EditText getFocusLostEditText() {
        return focusLostEditText;
    }

    public void setFocusLostEditText(EditText focusLostEditText) {
        this.focusLostEditText = focusLostEditText;
    }


    //ProductRate
    public void setPrdctRateToApply(String prdctId, String prdctVal) {
        hmapPrdctRateToApply.put(prdctId, prdctVal);
    }

    public String getPrdctRateToApply(String prdctId) {
        String prdctAppliedRate = "";
        if ((hmapPrdctRateToApply != null) && (hmapPrdctRateToApply.containsKey(prdctId))) {
            prdctAppliedRate = hmapPrdctRateToApply.get(prdctId);
        }
        return prdctAppliedRate;
    }

    public void removePrdctRateToApply(String prdctId) {
        if ((hmapPrdctRateToApply != null)) {
            hmapPrdctRateToApply.remove(prdctId);
        }
    }
    //hmapPrdctRateToApply

    //Product Quantity



    public void setFreeQty(String prdctId, String prdctQty) {
        hmapPrdctFreeQty.put(prdctId, prdctQty);
    }

    public void removeFreeQty(String prdctId) {
        if ((hmapPrdctFreeQty != null)) {
            hmapPrdctFreeQty.remove(prdctId);
        }
    }

    public int gethmapPrdctOrderQtySize() {
        int totalSize = 0;
        if (hmapPrdctOrderQty != null) {
            totalSize = hmapPrdctOrderQty.size();
        }
        return totalSize;
    }

    public LinkedHashMap<String, String> getHmapPrdctOrderQty() {
        return hmapPrdctOrderQty;
    }

    public void setPrdctQty(String prdctId, String prdctQty) {
        hmapPrdctOrderQty.put(prdctId, prdctQty);
    }


    public String getPrdctOrderQty(String prdctId) {
        String prdctQty = "";
        if ((hmapPrdctOrderQty != null) && (hmapPrdctOrderQty.containsKey(prdctId))) {
            prdctQty = hmapPrdctOrderQty.get(prdctId);
        }
        return prdctQty;
    }


    public LinkedHashMap<String, String> getHmapPrdctOrderQtyWithTCOrder() {
        return hmapPrdctQtyTCOrder;
    }

    public String getPrdctOrderQtyWithTCOrder(String prdctId) {
        String prdctQty = "";
        if ((hmapPrdctQtyTCOrder != null) && (hmapPrdctQtyTCOrder.containsKey(prdctId))) {
            prdctQty = hmapPrdctQtyTCOrder.get(prdctId);
        }
        return prdctQty;
    }

    public void setPrdctQtyWithTCOrder(String prdctId, String prdctQty) {
        hmapPrdctQtyTCOrder.put(prdctId, prdctQty);
    }


    public void removePrdctQty(String prdctId) {
        if ((hmapPrdctOrderQty != null)) {
            hmapPrdctOrderQty.remove(prdctId);
        }
    }

    //ProductValue
    public void setPrdctVal(String prdctId, String prdctVal) {
        hmapPrdctOrderVal.put(prdctId, prdctVal);
    }
    public void setPrdctValWithTCOder(String prdctId, String prdctVal) {
        hmapPrdctOrderValWithTCOrder.put(prdctId, prdctVal);
    }
    public double getPrdctOrderValWithTCOrder(String prdctId) {
        double prdctVal = 0.0;
        if ((hmapPrdctOrderValWithTCOrder != null) && (hmapPrdctOrderValWithTCOrder.containsKey(prdctId))) {
            prdctVal = Double.parseDouble(hmapPrdctOrderValWithTCOrder.get(prdctId));
            prdctVal = Double.parseDouble(new DecimalFormat("##.##").format(prdctVal));
        }
        return prdctVal;
    }
    public double getPrdctOrderVal(String prdctId) {
        double prdctVal = 0.0;
        if ((hmapPrdctOrderVal != null) && (hmapPrdctOrderVal.containsKey(prdctId))) {
            prdctVal = Double.parseDouble(hmapPrdctOrderVal.get(prdctId));
            prdctVal = Double.parseDouble(new DecimalFormat("##.##").format(prdctVal));
        }
        return prdctVal;
    }

    public String getPrdctFreeQtyAgainstProduct(String prdctId) {
        String prdctVal = "0";
        if ((hmapPrdctFreeQty != null) && (hmapPrdctFreeQty.containsKey(prdctId))) {
            prdctVal = hmapPrdctFreeQty.get(prdctId);
        }
        return prdctVal;
    }

    public LinkedHashMap<String, String> getHmapPrdctOrderVal() {
        return hmapPrdctOrderVal;
    }
    public LinkedHashMap<String, String> getHmapPrdctOrderValWithTCOrder() {
        return hmapPrdctOrderValWithTCOrder;
    }
    public void removePrdctVal(String prdctId) {
        if ((hmapPrdctOrderVal != null)) {
            hmapPrdctOrderVal.remove(prdctId);
        }
    }

    public EditText getLastEditText() {
        return lastEditText;
    }

    public void setLastEditText(EditText lastEditText) {
        this.lastEditText = lastEditText;
    }


    public LinkedHashMap<String, String> getHmapProductRatePrice() {
        return hmapProductRatePrice;
    }

    public void setHmapProductRatePriceToAppy(LinkedHashMap<String, String> hmapProductRatePrice) {
        this.hmapProductRatePrice = hmapProductRatePrice;
    }

    public LinkedHashMap<String, String> getHmapPrdctRateToApply() {
        return hmapPrdctRateToApply;
    }

    public void fnRequestToNotifyAllChanges(LinkedHashMap<String, String> hmapPrdctRateToApplyOnEntryForm) {
        hmapPrdctRateToApply.clear();
        hmapPrdctRateToApply.putAll(hmapPrdctRateToApplyOnEntryForm);


        //ProductFilledDataModel.class.notifyAll();
    }

    public void setPrdcTaxtAmount(String prdctId,String prdctRate)
    {
        hmapPrdctOrderValTaxAmount.put(prdctId,prdctRate);
    }
    public String getPrdcTaxtAmount(String prdctId)
    {
        String PrdcTaxtAmount="0.0";
        if((hmapPrdctOrderValTaxAmount!=null) && (hmapPrdctOrderValTaxAmount.containsKey(prdctId)))
        {
            PrdcTaxtAmount=hmapPrdctOrderValTaxAmount.get(prdctId);
        }
        return PrdcTaxtAmount;
    }

    public void setPrdcRateBeforeGST(String prdctId,String prdctRate)
    {
        hmapPrdctOrderValBeforeGST.put(prdctId,prdctRate);
    }
    public String getPrdcRateBeforeGST(String prdctId)
    {
        String PrdctOrderValBeforeGST="";
        if((hmapPrdctOrderValBeforeGST!=null) && (hmapPrdctOrderValBeforeGST.containsKey(prdctId)))
        {
            PrdctOrderValBeforeGST=hmapPrdctOrderValBeforeGST.get(prdctId);
        }
        return PrdctOrderValBeforeGST;
    }















    public int getHmapOrderQtyDataToShowSize() {
        int totalSize = 0;
        if (hmapOrderQtyDataToShow != null) {
            totalSize = hmapOrderQtyDataToShow.size();
        }
        return totalSize;
    }

    public LinkedHashMap<String, String> getHmapOrderQtyDataToShow() {
        return hmapOrderQtyDataToShow;
    }

    public void setPrdctQtyToShow(String prdctId, String prdctQty) {
        hmapOrderQtyDataToShow.put(prdctId, prdctQty);
    }


    public String getPrdctOrderQtyToShow(String prdctId) {
        String prdctQty = "";
        if ((hmapOrderQtyDataToShow != null) && (hmapOrderQtyDataToShow.containsKey(prdctId))) {
            prdctQty = hmapOrderQtyDataToShow.get(prdctId);
        }
        return prdctQty;
    }
    public void removePrdctQtyToShow(String prdctId) {
        if ((hmapOrderQtyDataToShow != null)) {
            hmapOrderQtyDataToShow.remove(prdctId);
        }
    }









    public int geHmapflgPicsOrCases() {
        int totalSize = 0;
        if (hmapflgPicsOrCases != null) {
            totalSize = hmapflgPicsOrCases.size();
        }
        return totalSize;
    }

    public LinkedHashMap<String, String> getHmapflgPicsOrCases() {
        return hmapflgPicsOrCases;
    }

    public void setPrdctQtyMappedToPicsOrCases(String prdctId, String prdctQty) {
        hmapflgPicsOrCases.put(prdctId, prdctQty);
    }


    public String getPrdctOrderMappedToPicsOrCases(String prdctId) {
        String prdctQty = "2";
        if ((hmapflgPicsOrCases != null) && (hmapflgPicsOrCases.containsKey(prdctId))) {
            prdctQty = hmapflgPicsOrCases.get(prdctId);
        }
        return prdctQty;
    }
    public void removePrdctQtyMappedToPicsOrCases(String prdctId) {
        if ((hmapflgPicsOrCases != null)) {
            hmapflgPicsOrCases.remove(prdctId);
        }
    }


   /* @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }*/
}