package com.astix.rajtraderssfasales.SchemeTablesModelsSaving;

public class TblSchemeList {
    private int SchemeID;

    public int getSchemeID() {
        return SchemeID;
    }

    public void setSchemeID(int schemeID) {
        SchemeID = schemeID;
    }

    public String getSchemeName() {
        return SchemeName;
    }

    public void setSchemeName(String schemeName) {
        SchemeName = schemeName;
    }

    public int getSchemeType() {
        return SchemeType;
    }

    public void setSchemeType(int schemeType) {
        SchemeType = schemeType;
    }

    public int getStoreType() {
        return StoreType;
    }

    public void setStoreType(int storeType) {
        StoreType = storeType;
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

    private String SchemeName;
    private int SchemeType;
    private int StoreType;
    private int CombTypeID;
    private int flgDiscountType;
}
