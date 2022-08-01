
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblUOMMaster {

    @SerializedName("BUOMID")
    @Expose
    private int bUOMID;
    @SerializedName("BUOMName")
    @Expose
    private String bUOMName;
    @SerializedName("flgRetailUnit")
    @Expose
    private int flgRetailUnit;



    private double RelConversionUnit;


    private double ProductMRP;

    private double ProductRLP;

    private double ProductTaxAmount;

    private double RetMarginPer;

    private double VatTax;

    private double StandardRate;

    private double StandardRateBeforeTax;

    private double StandardTax;

    public double getProductMRP() {
        return ProductMRP;
    }

    public void setProductMRP(double productMRP) {
        ProductMRP = productMRP;
    }

    public double getProductRLP() {
        return ProductRLP;
    }

    public void setProductRLP(double productRLP) {
        ProductRLP = productRLP;
    }

    public double getProductTaxAmount() {
        return ProductTaxAmount;
    }

    public void setProductTaxAmount(double productTaxAmount) {
        ProductTaxAmount = productTaxAmount;
    }

    public double getRetMarginPer() {
        return RetMarginPer;
    }

    public void setRetMarginPer(double retMarginPer) {
        RetMarginPer = retMarginPer;
    }

    public double getVatTax() {
        return VatTax;
    }

    public void setVatTax(double vatTax) {
        VatTax = vatTax;
    }

    public double getStandardRate() {
        return StandardRate;
    }

    public void setStandardRate(double standardRate) {
        StandardRate = standardRate;
    }

    public double getStandardRateBeforeTax() {
        return StandardRateBeforeTax;
    }

    public void setStandardRateBeforeTax(double standardRateBeforeTax) {
        StandardRateBeforeTax = standardRateBeforeTax;
    }

    public double getStandardTax() {
        return StandardTax;
    }

    public void setStandardTax(double standardTax) {
        StandardTax = standardTax;
    }

    public double getRelConversionUnit() {
        return RelConversionUnit;
    }

    public void setRelConversionUnit(double relConversionUnit) {
        RelConversionUnit = relConversionUnit;
    }


    private int flgSelected;

    private double DiscountPerUOMTC;

    public double getDiscountPerUOMTC() {
        return DiscountPerUOMTC;
    }

    public void setDiscountPerUOMTC(double discountPerUOMTC) {
        DiscountPerUOMTC = discountPerUOMTC;
    }

    public int getFlgSelected() {
        return flgSelected;
    }

    public void setFlgSelected(int flgSelected) {
        this.flgSelected = flgSelected;
    }

    public int getBUOMID() {
        return bUOMID;
    }

    public void setBUOMID(int bUOMID) {
        this.bUOMID = bUOMID;
    }

    public String getBUOMName() {
        return bUOMName;
    }

    public void setBUOMName(String bUOMName) {
        this.bUOMName = bUOMName;
    }

    public int getFlgRetailUnit() {
        return flgRetailUnit;
    }

    public void setFlgRetailUnit(int flgRetailUnit) {
        this.flgRetailUnit = flgRetailUnit;
    }

}
