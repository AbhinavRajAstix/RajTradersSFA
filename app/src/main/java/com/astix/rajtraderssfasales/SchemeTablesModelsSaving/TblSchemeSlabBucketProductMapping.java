package com.astix.rajtraderssfasales.SchemeTablesModelsSaving;

public class TblSchemeSlabBucketProductMapping {
    private int RowID;

    public int getRowID() {
        return RowID;
    }

    public void setRowID(int rowID) {
        RowID = rowID;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    private int ProductID;

    public int getFlgCombine() {
        return flgCombine;
    }

    public void setFlgCombine(int flgCombine) {
        this.flgCombine = flgCombine;
    }

    public int getQtyPerLine() {
        return QtyPerLine;
    }

    public void setQtyPerLine(int qtyPerLine) {
        QtyPerLine = qtyPerLine;
    }

    private int flgCombine;

    private int QtyPerLine;
}
