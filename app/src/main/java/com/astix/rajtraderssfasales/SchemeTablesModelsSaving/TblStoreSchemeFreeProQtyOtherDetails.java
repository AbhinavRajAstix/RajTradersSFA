package com.astix.rajtraderssfasales.SchemeTablesModelsSaving;

public class TblStoreSchemeFreeProQtyOtherDetails {
    private String  StoreID;
    private  int StoreType;

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public int getStoreType() {
        return StoreType;
    }

    public void setStoreType(int storeType) {
        StoreType = storeType;
    }

    public int getSchemeID() {
        return SchemeID;
    }

    public void setSchemeID(int schemeID) {
        SchemeID = schemeID;
    }

    public int getSchemeType() {
        return SchemeType;
    }

    public void setSchemeType(int schemeType) {
        SchemeType = schemeType;
    }

    public int getCombTypeID() {
        return CombTypeID;
    }

    public void setCombTypeID(int combTypeID) {
        CombTypeID = combTypeID;
    }

    public int getFlgDiscountType() {
        return flgDiscountType;
    }

    public void setFlgDiscountType(int flgDiscountType) {
        this.flgDiscountType = flgDiscountType;
    }

    public int getFreeProductID() {
        return FreeProductID;
    }

    public void setFreeProductID(int freeProductID) {
        FreeProductID = freeProductID;
    }

    public int getFreeProductQty() {
        return FreeProductQty;
    }

    public void setFreeProductQty(int freeProductQty) {
        FreeProductQty = freeProductQty;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    private int SchemeID;
    private  int SchemeType;
    private int CombTypeID;
    private int flgDiscountType;
    private int FreeProductID;
    private int FreeProductQty;
    private double Discount;
}
