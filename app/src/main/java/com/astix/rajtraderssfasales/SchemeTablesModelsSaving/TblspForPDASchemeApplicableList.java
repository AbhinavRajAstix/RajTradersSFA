package com.astix.rajtraderssfasales.SchemeTablesModelsSaving;

public class TblspForPDASchemeApplicableList {
    private int SchemeID;
    private int StoreType;

    public int getSchemeID() {
        return SchemeID;
    }

    public void setSchemeID(int schemeID) {
        SchemeID = schemeID;
    }

    public int getStoreType() {
        return StoreType;
    }

    public void setStoreType(int storeType) {
        StoreType = storeType;
    }

    public String getSchemeDesc() {
        return SchemeDesc;
    }

    public void setSchemeDesc(String schemeDesc) {
        SchemeDesc = schemeDesc;
    }

    public int getFlgSpecialScheme() {
        return flgSpecialScheme;
    }

    public void setFlgSpecialScheme(int flgSpecialScheme) {
        this.flgSpecialScheme = flgSpecialScheme;
    }

    private String SchemeDesc;
    private int flgSpecialScheme;
}
