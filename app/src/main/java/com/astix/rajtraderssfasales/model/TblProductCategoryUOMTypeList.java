
package com.astix.rajtraderssfasales.model;

import java.util.List;

public class TblProductCategoryUOMTypeList {
    private String StoreID;
    private String CategoryDesc;
    private String OrderID;
    private String TmpInvoiceCodePDA;
    private String CartonID;
    private int PrdID;
    private int CatID;
    private String UOMType;
    private int NoOfCarton;
    private int FreeQty, Qty;
    private int TotalExpectedQty;
    private int TotalActualQty;
    private String TotalDiscountVal;
    private String TotalFreeVal;
    private String TotalLineValBeforeTax;

    public String getTotalLineValBeforeTax() {
        return TotalLineValBeforeTax;
    }

    public void setTotalLineValBeforeTax(String totalLineValBeforeTax) {
        TotalLineValBeforeTax = totalLineValBeforeTax;
    }

    public String getTotalFreeVal() {
        return TotalFreeVal;
    }

    public void setTotalFreeVal(String totalFreeVal) {
        TotalFreeVal = totalFreeVal;
    }

    public String getTotalDiscountVal() {
        return TotalDiscountVal;
    }

    public void setTotalDiscountVal(String totalDiscountVal) {
        TotalDiscountVal = totalDiscountVal;
    }

    private double StdLineVal, NetLineVal;

    private int Sstat;

    private List<TblProductCategoryUOMTypeList> tblProductCategoryUOMTypeListsSubCategor;

    public List<TblProductCategoryUOMTypeList> getTblProductCategoryUOMTypeListsSubCategor() {
        return tblProductCategoryUOMTypeListsSubCategor;
    }

    public void setTblProductCategoryUOMTypeListsSubCategor(List<TblProductCategoryUOMTypeList> tblProductCategoryUOMTypeListsSubCategor) {
        this.tblProductCategoryUOMTypeListsSubCategor = tblProductCategoryUOMTypeListsSubCategor;
    }
    /*
    List<TblProductCategoryUOMTypeList> tblProductCategoryUOMTypeSubList;

    public List<TblProductCategoryUOMTypeList> getTblProductCategoryUOMTypeSubList() {
        return tblProductCategoryUOMTypeSubList;
    }

    public void setTblProductCategoryUOMTypeSubList(List<TblProductCategoryUOMTypeList> tblProductCategoryUOMTypeSubList) {
        this.tblProductCategoryUOMTypeSubList = tblProductCategoryUOMTypeSubList;
    }
*/

    public int getFreeQty() {
        return FreeQty;
    }

    public void setFreeQty(int freeQty) {
        FreeQty = freeQty;
    }

    public double getStdLineVal() {
        return StdLineVal;
    }

    public void setStdLineVal(double stdLineVal) {
        StdLineVal = stdLineVal;
    }

    public double getNetLineVal() {
        return NetLineVal;
    }

    public void setNetLineVal(double netLineVal) {
        NetLineVal = netLineVal;
    }

    public String getCategoryDesc() {
        return CategoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        CategoryDesc = categoryDesc;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getTmpInvoiceCodePDA() {
        return TmpInvoiceCodePDA;
    }

    public void setTmpInvoiceCodePDA(String tmpInvoiceCodePDA) {
        TmpInvoiceCodePDA = tmpInvoiceCodePDA;
    }

    public String getCartonID() {
        return CartonID;
    }

    public void setCartonID(String cartonID) {
        CartonID = cartonID;
    }

    public int getPrdID() {
        return PrdID;
    }

    public void setPrdID(int prdID) {
        PrdID = prdID;
    }

    public int getCatID() {
        return CatID;
    }

    public void setCatID(int catID) {
        CatID = catID;
    }

    public String getUOMType() {
        return UOMType;
    }

    public void setUOMType(String UOMType) {
        this.UOMType = UOMType;
    }

    public int getNoOfCarton() {
        return NoOfCarton;
    }

    public void setNoOfCarton(int noOfCarton) {
        NoOfCarton = noOfCarton;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public int getTotalExpectedQty() {
        return TotalExpectedQty;
    }

    public void setTotalExpectedQty(int totalExpectedQty) {
        TotalExpectedQty = totalExpectedQty;
    }

    public int getTotalActualQty() {
        return TotalActualQty;
    }

    public void setTotalActualQty(int totalActualQty) {
        TotalActualQty = totalActualQty;
    }

    public int getSstat() {
        return Sstat;
    }

    public void setSstat(int sstat) {
        Sstat = sstat;
    }


}
