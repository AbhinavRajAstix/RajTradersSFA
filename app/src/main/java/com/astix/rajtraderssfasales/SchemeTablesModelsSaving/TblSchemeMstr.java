package com.astix.rajtraderssfasales.SchemeTablesModelsSaving;

public class TblSchemeMstr {
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

    public int getSchemeApplicationID() {
        return SchemeApplicationID;
    }

    public void setSchemeApplicationID(int schemeApplicationID) {
        SchemeApplicationID = schemeApplicationID;
    }

    public int getSchemeAppliedRule() {
        return SchemeAppliedRule;
    }

    public void setSchemeAppliedRule(int schemeAppliedRule) {
        SchemeAppliedRule = schemeAppliedRule;
    }

    private int SchemeID;
private String SchemeName;
private int SchemeApplicationID;
private int SchemeAppliedRule;

    public int getSchemeCalculationTypeID() {
        return SchemeCalculationTypeID;
    }

    public void setSchemeCalculationTypeID(int schemeCalculationTypeID) {
        SchemeCalculationTypeID = schemeCalculationTypeID;
    }

    private int SchemeCalculationTypeID;

    public int getSchemeTypeId() {
        return SchemeTypeId;
    }

    public void setSchemeTypeId(int schemeTypeId) {
        SchemeTypeId = schemeTypeId;
    }

    private int SchemeTypeId;
}
