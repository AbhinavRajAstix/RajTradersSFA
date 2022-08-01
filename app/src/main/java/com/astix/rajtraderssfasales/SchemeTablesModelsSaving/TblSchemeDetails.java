package com.astix.rajtraderssfasales.SchemeTablesModelsSaving;

public class TblSchemeDetails {
    private int SchemeDetID;

    public int getSchemeDetID() {
        return SchemeDetID;
    }

    public void setSchemeDetID(int schemeDetID) {
        SchemeDetID = schemeDetID;
    }

    public int getSchemeID() {
        return SchemeID;
    }

    public void setSchemeID(int schemeID) {
        SchemeID = schemeID;
    }

    public int getSlabFrom() {
        return SlabFrom;
    }

    public void setSlabFrom(int slabFrom) {
        SlabFrom = slabFrom;
    }

    public int getSlabTo() {
        return SlabTo;
    }

    public void setSlabTo(int slabTo) {
        SlabTo = slabTo;
    }

    public int getFreeQuantity() {
        return FreeQuantity;
    }

    public void setFreeQuantity(int freeQuantity) {
        FreeQuantity = freeQuantity;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public int getFreeProductID() {
        return FreeProductID;
    }

    public void setFreeProductID(int freeProductID) {
        FreeProductID = freeProductID;
    }

    private int SchemeID;
    private int SlabFrom;
    private int SlabTo;
    private int FreeQuantity;
    private double Discount;
    private int FreeProductID;
}
