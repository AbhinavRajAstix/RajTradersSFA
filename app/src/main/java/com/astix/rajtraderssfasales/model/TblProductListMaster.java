package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblProductListMaster {

    @SerializedName("CatID")
    @Expose
    private int catID;
    @SerializedName("ProductID")
    @Expose
    private int productID;
    @SerializedName("ProductShortName")
    @Expose
    private String productShortName;
    @SerializedName("DisplayUnit")
    @Expose
    private String displayUnit;
    @SerializedName("CalculateKilo")
    @Expose
    private int calculateKilo;
    @SerializedName("KGLiter")
    @Expose
    private int kGLiter;
    @SerializedName("ProductType")
    @Expose
    private String productType;
    @SerializedName("SearchField")
    @Expose
    private String searchField;
    @SerializedName("ManufacturerID")
    @Expose
    private int manufacturerID;
    @SerializedName("PrdOrdr")
    @Expose
    private int prdOrdr;
    @SerializedName("RptUnitName")
    @Expose
    private String rptUnitName;
    @SerializedName("PerbaseUnit")
    @Expose
    private int perbaseUnit;
    @SerializedName("HSNCode")
    @Expose
    private String hSNCode;
    @SerializedName("ImageURL")
    @Expose
    private String ImageURL;

    @SerializedName("RptUnitID")
    @Expose
    private int RptUnitID;

    @SerializedName("IsRefrigeration")
    private int IsRefrigeration;
    public int getCatID() {
        return catID;
    }

    @SerializedName("ProductTypeID")
    @Expose
    private int ProductTypeNodeID;


    @SerializedName("flgActive")
    @Expose
    private int flgActive;


    @SerializedName("UOMType")
    @Expose
    private String UOMType;


    @SerializedName("PcsInBox")
    @Expose
    private double PcsInBox;

    @SerializedName("UOMValue")
    @Expose
    private int UOMValue;

    public int getUOMValue() {
        return UOMValue;
    }

    public void setUOMValue(int UOMValue) {
        this.UOMValue = UOMValue;
    }

    public int getFlgActive() {
        return flgActive;
    }

    public void setFlgActive(int flgActive) {
        this.flgActive = flgActive;
    }

    public String getUOMType() {
        return UOMType;
    }

    public void setUOMType(String UOMType) {
        this.UOMType = UOMType;
    }

    public double getPcsInBox() {
        return PcsInBox;
    }

    public void setPcsInBox(double pcsInBox) {
        PcsInBox = pcsInBox;
    }

    public void setCatID(int catID) {
        this.catID = catID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductShortName() {
        return productShortName;
    }

    public void setProductShortName(String productShortName) {
        this.productShortName = productShortName;
    }

    public String getDisplayUnit() {
        return displayUnit;
    }

    public void setDisplayUnit(String displayUnit) {
        this.displayUnit = displayUnit;
    }

    public int getCalculateKilo() {
        return calculateKilo;
    }

    public void setCalculateKilo(int calculateKilo) {
        this.calculateKilo = calculateKilo;
    }

    public int getkGLiter() {
        return kGLiter;
    }

    public void setkGLiter(int kGLiter) {
        this.kGLiter = kGLiter;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public int getPrdOrdr() {
        return prdOrdr;
    }

    public void setPrdOrdr(int prdOrdr) {
        this.prdOrdr = prdOrdr;
    }

    public String getRptUnitName() {
        return rptUnitName;
    }

    public void setRptUnitName(String rptUnitName) {
        this.rptUnitName = rptUnitName;
    }

    public int getPerbaseUnit() {
        return perbaseUnit;
    }

    public void setPerbaseUnit(int perbaseUnit) {
        this.perbaseUnit = perbaseUnit;
    }

    public String gethSNCode() {
        return hSNCode;
    }

    public void sethSNCode(String hSNCode) {
        this.hSNCode = hSNCode;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public int getRptUnitID() {
        return RptUnitID;
    }

    public void setRptUnitID(int rptUnitID) {
        RptUnitID = rptUnitID;
    }

    public int getIsRefrigeration() {
        return IsRefrigeration;
    }

    public void setIsRefrigeration(int isRefrigeration) {
        IsRefrigeration = isRefrigeration;
    }

    public int getProductTypeNodeID() {
        return ProductTypeNodeID;
    }

    public void setProductTypeNodeID(int productTypeNodeID) {
        ProductTypeNodeID = productTypeNodeID;
    }
}
