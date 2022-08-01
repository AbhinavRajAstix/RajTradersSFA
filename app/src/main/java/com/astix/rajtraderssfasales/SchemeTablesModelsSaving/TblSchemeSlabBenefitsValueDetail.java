package com.astix.rajtraderssfasales.SchemeTablesModelsSaving;

public class TblSchemeSlabBenefitsValueDetail {
    private int RowID;

    public int getRowID() {
        return RowID;
    }

    public void setRowID(int rowID) {
        RowID = rowID;
    }

    public double getBenValue() {
        return BenValue;
    }

    public void setBenValue(double benValue) {
        BenValue = benValue;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    private double BenValue;
    private String Remarks;
    private int Type;
}
