
package com.astix.rajtraderssfasales.model;



public class TblAppliedOrderDiscount {

    private String StoreID;
    private String InvoiceNumber;
    private int PrdID;
    private int UOMID;
    private int RelConversionUnits;
    private double TotalDisc;
    private double DiscountperUOM;


    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public int getPrdID() {
        return PrdID;
    }

    public void setPrdID(int prdID) {
        PrdID = prdID;
    }

    public int getUOMID() {
        return UOMID;
    }

    public void setUOMID(int UOMID) {
        this.UOMID = UOMID;
    }

    public int getRelConversionUnits() {
        return RelConversionUnits;
    }

    public void setRelConversionUnits(int relConversionUnits) {
        RelConversionUnits = relConversionUnits;
    }

    public double getTotalDisc() {
        return TotalDisc;
    }

    public void setTotalDisc(double totalDisc) {
        TotalDisc = totalDisc;
    }

    public double getDiscountperUOM() {
        return DiscountperUOM;
    }

    public void setDiscountperUOM(double discountperUOM) {
        DiscountperUOM = discountperUOM;
    }
}
