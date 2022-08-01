package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblInvoiceDetailsServer {
   
    private int InvoiceNumber;
    private String TmpInvoiceCodePDA;
    private String StoreID;
    private String CatID;
    private String ProdID;

    private double ProductPrice;

    private double TaxRate;

    private int flgRuleTaxVal;

    private int OrderQty;

    private int UOMId;

    private String UOMName;

    private double LineValBfrTxAftrDscnt;

    private double LineValAftrTxAftrDscnt;

    private int FreeQty;

    private double DisVal;

    private int Sstat;

    private int SampleQuantity;

    private String ContactNum;

    private String ProductShortName;

    private double TaxValue;

    private String OrderIDPDA;

    private int flgIsQuoteRateApplied;

    private String ServingDBRId;

    private int flgWholeSellApplicable;

    private int ProductExtraOrder;

    private int flgDrctslsIndrctSls;

    private int DeliveryQtyDroppedReasonID;


    private String DeliveryQtyDroppedComments;



    private int flgTeleCallerProduct = 1;


    private int flgUserEditedProduct = 0;

    private int OriginalTeleCallerProductQty = 0;

    private int UOMConverstionUnitQty;

    private double Discperpcs;

    public double getDiscperpcs() {
        return Discperpcs;
    }

    public void setDiscperpcs(double discperpcs) {
        Discperpcs = discperpcs;
    }

    public int getUOMConverstionUnitQty() {
        return UOMConverstionUnitQty;
    }

    public void setUOMConverstionUnitQty(int UOMConverstionUnitQty) {
        this.UOMConverstionUnitQty = UOMConverstionUnitQty;
    }

    private int SuggestedQty;

    private double OrderVal;

    private double ProductMRP;

    public double getProductMRP() {
        return ProductMRP;
    }

    public void setProductMRP(double productMRP) {
        ProductMRP = productMRP;
    }

    private String TeleCallingID;

    private String CartonID;

    public String getCartonID() {
        return CartonID;
    }

    public void setCartonID(String cartonID) {
        CartonID = cartonID;
    }

    public String getTeleCallingID() {
        return TeleCallingID;
    }

    public void setTeleCallingID(String teleCallingID) {
        TeleCallingID = teleCallingID;
    }

    public double getOrderVal() {
        return OrderVal;
    }

    public void setOrderVal(double orderVal) {
        OrderVal = orderVal;
    }

    public int getSuggestedQty() {
        return SuggestedQty;
    }

    public void setSuggestedQty(int suggestedQty) {
        SuggestedQty = suggestedQty;
    }

    public String getDeliveryQtyDroppedComments() {
        return DeliveryQtyDroppedComments;
    }

    public void setDeliveryQtyDroppedComments(String deliveryQtyDroppedComments) {
        DeliveryQtyDroppedComments = deliveryQtyDroppedComments;
    }

    public int getDeliveryQtyDroppedReasonID() {
        return DeliveryQtyDroppedReasonID;
    }

    public void setDeliveryQtyDroppedReasonID(int deliveryQtyDroppedReason) {
        DeliveryQtyDroppedReasonID = deliveryQtyDroppedReason;
    }

    public int getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getTmpInvoiceCodePDA() {
        return TmpInvoiceCodePDA;
    }

    public void setTmpInvoiceCodePDA(String tmpInvoiceCodePDA) {
        TmpInvoiceCodePDA = tmpInvoiceCodePDA;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getCatID() {
        return CatID;
    }

    public void setCatID(String catID) {
        CatID = catID;
    }

    public String getProdID() {
        return ProdID;
    }

    public void setProdID(String prodID) {
        ProdID = prodID;
    }

    public double getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(double productPrice) {
        ProductPrice = productPrice;
    }

    public double getTaxRate() {
        return TaxRate;
    }

    public void setTaxRate(double taxRate) {
        TaxRate = taxRate;
    }

    public int getFlgRuleTaxVal() {
        return flgRuleTaxVal;
    }

    public void setFlgRuleTaxVal(int flgRuleTaxVal) {
        this.flgRuleTaxVal = flgRuleTaxVal;
    }

    public int getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(int orderQty) {
        OrderQty = orderQty;
    }

    public int getUOMId() {
        return UOMId;
    }

    public void setUOMId(int UOMId) {
        this.UOMId = UOMId;
    }

    public double getLineValBfrTxAftrDscnt() {
        return LineValBfrTxAftrDscnt;
    }

    public void setLineValBfrTxAftrDscnt(double lineValBfrTxAftrDscnt) {
        LineValBfrTxAftrDscnt = lineValBfrTxAftrDscnt;
    }

    public double getLineValAftrTxAftrDscnt() {
        return LineValAftrTxAftrDscnt;
    }

    public void setLineValAftrTxAftrDscnt(double lineValAftrTxAftrDscnt) {
        LineValAftrTxAftrDscnt = lineValAftrTxAftrDscnt;
    }

    public int getFreeQty() {
        return FreeQty;
    }

    public void setFreeQty(int freeQty) {
        FreeQty = freeQty;
    }

    public double getDisVal() {
        return DisVal;
    }

    public void setDisVal(double disVal) {
        DisVal = disVal;
    }

    public int getSstat() {
        return Sstat;
    }

    public void setSstat(int sstat) {
        Sstat = sstat;
    }

    public int getSampleQuantity() {
        return SampleQuantity;
    }

    public void setSampleQuantity(int sampleQuantity) {
        SampleQuantity = sampleQuantity;
    }

    public String getContactNum() {
        return ContactNum;
    }

    public void setContactNum(String contactNum) {
        ContactNum = contactNum;
    }

    public String getProductShortName() {
        return ProductShortName;
    }

    public void setProductShortName(String productShortName) {
        ProductShortName = productShortName;
    }

    public double getTaxValue() {
        return TaxValue;
    }

    public void setTaxValue(double taxValue) {
        TaxValue = taxValue;
    }

    public String getOrderIDPDA() {
        return OrderIDPDA;
    }

    public void setOrderIDPDA(String orderIDPDA) {
        OrderIDPDA = orderIDPDA;
    }

    public int getFlgIsQuoteRateApplied() {
        return flgIsQuoteRateApplied;
    }

    public void setFlgIsQuoteRateApplied(int flgIsQuoteRateApplied) {
        this.flgIsQuoteRateApplied = flgIsQuoteRateApplied;
    }

    public String getServingDBRId() {
        return ServingDBRId;
    }

    public void setServingDBRId(String servingDBRId) {
        ServingDBRId = servingDBRId;
    }

    public int getFlgWholeSellApplicable() {
        return flgWholeSellApplicable;
    }

    public void setFlgWholeSellApplicable(int flgWholeSellApplicable) {
        this.flgWholeSellApplicable = flgWholeSellApplicable;
    }

    public int getProductExtraOrder() {
        return ProductExtraOrder;
    }

    public void setProductExtraOrder(int productExtraOrder) {
        ProductExtraOrder = productExtraOrder;
    }

    public int getFlgDrctslsIndrctSls() {
        return flgDrctslsIndrctSls;
    }

    public void setFlgDrctslsIndrctSls(int flgDrctslsIndrctSls) {
        this.flgDrctslsIndrctSls = flgDrctslsIndrctSls;
    }

    public int getFlgTeleCallerProduct() {
        return flgTeleCallerProduct;
    }

    public void setFlgTeleCallerProduct(int flgTeleCallerProduct) {
        this.flgTeleCallerProduct = flgTeleCallerProduct;
    }

    public int getFlgUserEditedProduct() {
        return flgUserEditedProduct;
    }

    public void setFlgUserEditedProduct(int flgUserEditedProduct) {
        this.flgUserEditedProduct = flgUserEditedProduct;
    }

    public int getOriginalTeleCallerProductQty() {
        return OriginalTeleCallerProductQty;
    }

    public void setOriginalTeleCallerProductQty(int originalTeleCallerProductQty) {
        OriginalTeleCallerProductQty = originalTeleCallerProductQty;
    }

    public String getUOMName() {
        return UOMName;
    }

    public void setUOMName(String UOMName) {
        this.UOMName = UOMName;
    }
}