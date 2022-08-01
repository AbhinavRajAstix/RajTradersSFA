
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblProductSegementMap {

    @SerializedName("ProductId")
    @Expose
    private int productId;
    @SerializedName("BusinessSegmentId")
    @Expose
    private int businessSegmentId;
    @SerializedName("SegmentNodeId")
    @Expose
    private int segmentNodeId;
    @SerializedName("ProductMRP")
    @Expose
    private double productMRP;
    @SerializedName("ProductRLP")
    @Expose
    private double productRLP;
    @SerializedName("ProductTaxAmount")
    @Expose
    private double productTaxAmount;
    @SerializedName("RetMarginPer")
    @Expose
    private double retMarginPer;
    @SerializedName("Tax")
    @Expose
    private double tax;
    @SerializedName("StandardRate")
    @Expose
    private double standardRate;
    @SerializedName("StandardRateBeforeTax")
    @Expose
    private double standardRateBeforeTax;
    @SerializedName("StandardTax")
    @Expose
    private double standardTax;
    @SerializedName("flgPriceAva")
    @Expose
    private int flgPriceAva;
    @SerializedName("flgPrdBulkPriceapplicable")
    @Expose
    private int flgPrdBulkPriceapplicable;
    @SerializedName("Cutoffvalue")
    @Expose
    private int cutoffvalue;
    @SerializedName("StandardRateWholeSale")
    @Expose
    private double standardRateWholeSale;
    @SerializedName("StandardRateBeforeTaxWholeSale")
    @Expose
    private double standardRateBeforeTaxWholeSale;
    @SerializedName("StandardTaxWholeSale")
    @Expose
    private double standardTaxWholeSale;
    @SerializedName("HSNCode")
    @Expose
    private String hSNCode;


    @SerializedName("RegionID")
    @Expose
    private int RegionID;

    @SerializedName("UOMID")
    @Expose
    private int UOMID;

    public int getUOMID() {
        return UOMID;
    }

    public void setUOMID(int UOMID) {
        this.UOMID = UOMID;
    }

    public int getRegionID() {
        return RegionID;
    }

    public void setRegionID(int regionID) {
        RegionID = regionID;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getBusinessSegmentId() {
        return businessSegmentId;
    }

    public void setBusinessSegmentId(int businessSegmentId) {
        this.businessSegmentId = businessSegmentId;
    }

    public int getSegmentNodeId() {
        return segmentNodeId;
    }

    public void setSegmentNodeId(int segmentNodeId) {
        this.segmentNodeId = segmentNodeId;
    }

    public double getProductMRP() {
        return productMRP;
    }

    public void setProductMRP(double productMRP) {
        this.productMRP = productMRP;
    }

    public double getProductRLP() {
        return productRLP;
    }

    public void setProductRLP(double productRLP) {
        this.productRLP = productRLP;
    }

    public double getProductTaxAmount() {
        return productTaxAmount;
    }

    public void setProductTaxAmount(double productTaxAmount) {
        this.productTaxAmount = productTaxAmount;
    }

    public double getRetMarginPer() {
        return retMarginPer;
    }

    public void setRetMarginPer(double retMarginPer) {
        this.retMarginPer = retMarginPer;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getStandardRate() {
        return standardRate;
    }

    public void setStandardRate(double standardRate) {
        this.standardRate = standardRate;
    }

    public double getStandardRateBeforeTax() {
        return standardRateBeforeTax;
    }

    public void setStandardRateBeforeTax(double standardRateBeforeTax) {
        this.standardRateBeforeTax = standardRateBeforeTax;
    }

    public double getStandardTax() {
        return standardTax;
    }

    public void setStandardTax(double standardTax) {
        this.standardTax = standardTax;
    }

    public int getFlgPriceAva() {
        return flgPriceAva;
    }

    public void setFlgPriceAva(int flgPriceAva) {
        this.flgPriceAva = flgPriceAva;
    }

    public int getFlgPrdBulkPriceapplicable() {
        return flgPrdBulkPriceapplicable;
    }

    public void setFlgPrdBulkPriceapplicable(int flgPrdBulkPriceapplicable) {
        this.flgPrdBulkPriceapplicable = flgPrdBulkPriceapplicable;
    }

    public int getCutoffvalue() {
        return cutoffvalue;
    }

    public void setCutoffvalue(int cutoffvalue) {
        this.cutoffvalue = cutoffvalue;
    }

    public double getStandardRateWholeSale() {
        return standardRateWholeSale;
    }

    public void setStandardRateWholeSale(double standardRateWholeSale) {
        this.standardRateWholeSale = standardRateWholeSale;
    }

    public double getStandardRateBeforeTaxWholeSale() {
        return standardRateBeforeTaxWholeSale;
    }

    public void setStandardRateBeforeTaxWholeSale(double standardRateBeforeTaxWholeSale) {
        this.standardRateBeforeTaxWholeSale = standardRateBeforeTaxWholeSale;
    }

    public double getStandardTaxWholeSale() {
        return standardTaxWholeSale;
    }

    public void setStandardTaxWholeSale(double standardTaxWholeSale) {
        this.standardTaxWholeSale = standardTaxWholeSale;
    }

    public String getHSNCode() {
        return hSNCode;
    }

    public void setHSNCode(String hSNCode) {
        this.hSNCode = hSNCode;
    }

}
