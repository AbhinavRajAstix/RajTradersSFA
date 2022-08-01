package com.astix.rajtraderssfasales.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TblStoreProductMappingForDisplay {
    @SerializedName("StoreID")
    private String StoreID;

    @SerializedName("CatID")
    private int CatID;
    @SerializedName("PrdNodeID")
    private int PrdNodeID;

    @SerializedName("PrdNodeType")
    private int PrdNodeType;

    @SerializedName("flgReplenishmentSKU")
    private int flgReplenishmentSKU;


    @SerializedName("flgFocusbrand")
    private int flgFocusbrand;

    @SerializedName("SuggestedQty")
    private int SuggestedQty;

    @SerializedName("OrderID")
    private String OrderID;


    @SerializedName("flgTeleCallerProduct")
    private int flgTeleCallerProduct = 0;


    @SerializedName("TeleCallerProductQty")
    private int TeleCallerProductQty = 0;

    public int getTeleCallerProductQty() {
        return TeleCallerProductQty;
    }

    public void setTeleCallerProductQty(int teleCallerProductQty) {
        TeleCallerProductQty = teleCallerProductQty;
    }

    @SerializedName("flgUserEditedProduct")
    private int flgUserEditedProduct = 0;


    @SerializedName("PrdName")
    private String PrdName;

    @SerializedName("PrdPrice")
    private String PrdPrice;

    @SerializedName("DisplayUnit")
    private String DisplayUnit;


    @SerializedName("SearchField")
    private String SearchField;

    @SerializedName("flgVisible")
    private int flgVisible;


    @SerializedName("flgShowCategoryHeader")
    private int flgShowCategoryHeader;

    @SerializedName("CategoryDesc")
    private String CategoryDesc;


    @SerializedName("flgMakeRowVisible")
    private int flgMakeRowVisible;


    @SerializedName("flgProductWithScheme")
    private int flgProductWithScheme;

    @SerializedName("flgMakeFrameVisible")
    private int flgMakeFrameVisible;

    @SerializedName("ColorCode")
    private String ColorCode;

    private int flgSuggestedQty;

    private String SKUImageURL;


    private double NetRate;
        private double NetValue;
    private double DiscountPerUOM;
    private double NetMarginPercentage;

    public double getNetRate() {
        return NetRate;
    }

    public void setNetRate(double netRate) {
        NetRate = netRate;
    }

    public double getNetValue() {
        return NetValue;
    }

    public void setNetValue(double netValue) {
        NetValue = netValue;
    }

    public double getDiscountPerUOM() {
        return DiscountPerUOM;
    }

    public void setDiscountPerUOM(double discountPerUOM) {
        DiscountPerUOM = discountPerUOM;
    }


    public double getNetMarginPercentage() {
        return NetMarginPercentage;
    }

    public void setNetMarginPercentage(double netMarginPercentage) {
        NetMarginPercentage = netMarginPercentage;
    }

    List<TblUOMMaster>tblUOMMasterList;

    public List<TblUOMMaster> getTblUOMMasterList() {
        return tblUOMMasterList;
    }

    public void setTblUOMMasterList(List<TblUOMMaster> tblUOMMasterList) {
        this.tblUOMMasterList = tblUOMMasterList;
    }

    public int getFlgMakeRowVisible() {
        return flgMakeRowVisible;
    }

    public void setFlgMakeRowVisible(int flgMakeRowVisible) {
        this.flgMakeRowVisible = flgMakeRowVisible;
    }

    public int getFlgMakeFrameVisible() {
        return flgMakeFrameVisible;
    }

    public void setFlgMakeFrameVisible(int flgMakeFrameVisible) {
        this.flgMakeFrameVisible = flgMakeFrameVisible;
    }

    private List<TblStoreProductMappingForDisplay> mChildTblStoreProductMappings;



    private int NoOfCartons;

    private int UOMIDTC;

    public int getUOMIDTC() {
        return UOMIDTC;
    }

    public void setUOMIDTC(int UOMIDTC) {
        this.UOMIDTC = UOMIDTC;
    }

    List<TblProductCategoryUOMTypeList> tblProductCategoryUOMTypeLists;


    List<TblProductCategoryUOMTypeList> tblProductCategoryUOMTypeListsOverAllSummary;


    public List<TblProductCategoryUOMTypeList> getTblProductCategoryUOMTypeListsOverAllSummary() {
        return tblProductCategoryUOMTypeListsOverAllSummary;
    }

    public void setTblProductCategoryUOMTypeListsOverAllSummary(List<TblProductCategoryUOMTypeList> tblProductCategoryUOMTypeListsOverAllSummary) {
        this.tblProductCategoryUOMTypeListsOverAllSummary = tblProductCategoryUOMTypeListsOverAllSummary;
    }

    private String UOMType;

    public String getUOMType() {
        return UOMType;
    }

    public void setUOMType(String UOMType) {
        this.UOMType = UOMType;
    }

    private String CartonID;

    public String getCartonID() {
        return CartonID;
    }

    public void setCartonID(String cartonID) {
        CartonID = cartonID;
    }

    public int getNoOfCartons() {
        return NoOfCartons;
    }

    public void setNoOfCartons(int noOfCartons) {
        NoOfCartons = noOfCartons;
    }

    public List<TblProductCategoryUOMTypeList> getTblProductCategoryUOMTypeLists() {
        return tblProductCategoryUOMTypeLists;
    }

    public void setTblProductCategoryUOMTypeLists(List<TblProductCategoryUOMTypeList> tblProductCategoryUOMTypeLists) {
        this.tblProductCategoryUOMTypeLists = tblProductCategoryUOMTypeLists;
    }

    public List<TblStoreProductMappingForDisplay> getmChildTblStoreProductMappings() {
        return mChildTblStoreProductMappings;
    }

    public void setmChildTblStoreProductMappings(List<TblStoreProductMappingForDisplay> mChildTblStoreProductMappings) {
        this.mChildTblStoreProductMappings = mChildTblStoreProductMappings;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public int getCatID() {
        return CatID;
    }

    public void setCatID(int catID) {
        CatID = catID;
    }

    public int getPrdNodeID() {
        return PrdNodeID;
    }

    public void setPrdNodeID(int prdNodeID) {
        PrdNodeID = prdNodeID;
    }

    public int getPrdNodeType() {
        return PrdNodeType;
    }

    public void setPrdNodeType(int prdNodeType) {
        PrdNodeType = prdNodeType;
    }

    public int getFlgFocusbrand() {
        return flgFocusbrand;
    }

    public void setFlgFocusbrand(int flgFocusbrand) {
        this.flgFocusbrand = flgFocusbrand;
    }

    public int getSuggestedQty() {
        return SuggestedQty;
    }

    public void setSuggestedQty(int suggestedQty) {
        SuggestedQty = suggestedQty;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
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

    public String getPrdName() {
        return PrdName;
    }

    public void setPrdName(String prdName) {
        PrdName = prdName;
    }

    public String getPrdPrice() {
        return PrdPrice;
    }

    public void setPrdPrice(String prdPrice) {
        PrdPrice = prdPrice;
    }

    public String getDisplayUnit() {
        return DisplayUnit;
    }

    public void setDisplayUnit(String displayUnit) {
        DisplayUnit = displayUnit;
    }

    public String getSearchField() {
        return SearchField;
    }

    public void setSearchField(String searchField) {
        SearchField = searchField;
    }

    public int getFlgVisible() {
        return flgVisible;
    }

    public void setFlgVisible(int flgVisible) {
        this.flgVisible = flgVisible;
    }

    public int getFlgShowCategoryHeader() {
        return flgShowCategoryHeader;
    }

    public void setFlgShowCategoryHeader(int flgShowCategoryHeader) {
        this.flgShowCategoryHeader = flgShowCategoryHeader;
    }

    public String getCategoryDesc() {
        return CategoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        CategoryDesc = categoryDesc;
    }

    public String getColorCode() {
        return ColorCode;
    }

    public void setColorCode(String colorCode) {
        ColorCode = colorCode;
    }

    public int getFlgSuggestedQty() {
        return flgSuggestedQty;
    }

    public void setFlgSuggestedQty(int flgSuggestedQty) {
        this.flgSuggestedQty = flgSuggestedQty;
    }

    public String getSKUImageURL() {
        return SKUImageURL;
    }

    public void setSKUImageURL(String SKUImageURL) {
        this.SKUImageURL = SKUImageURL;
    }

    public int getFlgProductWithScheme() {
        return flgProductWithScheme;
    }

    public void setFlgProductWithScheme(int flgProductWithScheme) {
        this.flgProductWithScheme = flgProductWithScheme;
    }

    public int getFlgReplenishmentSKU() {
        return flgReplenishmentSKU;
    }

    public void setFlgReplenishmentSKU(int flgReplenishmentSKU) {
        this.flgReplenishmentSKU = flgReplenishmentSKU;
    }
}
