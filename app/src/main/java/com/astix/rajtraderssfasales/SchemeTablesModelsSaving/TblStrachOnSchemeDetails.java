package com.astix.rajtraderssfasales.SchemeTablesModelsSaving;

public class TblStrachOnSchemeDetails {
    public int getSchID() {
        return SchID;
    }

    public void setSchID(int schID) {
        SchID = schID;
    }

    public int getCardStrachID() {
        return cardStrachID;
    }

    public void setCardStrachID(int cardStrachID) {
        this.cardStrachID = cardStrachID;
    }

    public int getSlab() {
        return Slab;
    }

    public void setSlab(int slab) {
        Slab = slab;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public int getProductValueOrSlabBased() {
        return ProductValueOrSlabBased;
    }

    public void setProductValueOrSlabBased(int productValueOrSlabBased) {
        ProductValueOrSlabBased = productValueOrSlabBased;
    }

    private int SchID;
    private int cardStrachID;
    private int Slab;
    private int Qty;
    private int ProductValueOrSlabBased;
}
