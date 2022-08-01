package com.astix.rajtraderssfasales.model;

//@Entity(tableName = "tblTmpInvoiceHeader")
public class TblInvoiceHeaderServer {

    private String StoreVisitCode;
    private String TmpInvoiceCodePDA;
    private String StoreID;
    private String InvoiceDate;
    private double TotalBeforeTaxDis;
    private double TaxAmt;
    private double TotalDis;
    private double InvoiceVal;
    private int FreeTotal;
    private int Sstat;
    private double InvAfterDis;
    private double AddDis;
    private int NoCoupon;
    private double TotalCoupunAmount;
    private String TransDate;
    private String FlgInvoiceType;
    private int flgWholeSellApplicable;
    private int flgProcessedInvoice;
    private int CycleID;
    private int flgDrctslsIndrctSls;
    private String RouteNodeID;
    private String RouteNodeType;
    private String TeleCallingID;

    public String getTeleCallingID() {
        return TeleCallingID;
    }

    public void setTeleCallingID(String teleCallingID) {
        TeleCallingID = teleCallingID;
    }

    public int getFlgWholeSellApplicable() {
        return flgWholeSellApplicable;
    }

    public void setFlgWholeSellApplicable(int flgWholeSellApplicable) {
        this.flgWholeSellApplicable = flgWholeSellApplicable;
    }



    public String getStoreVisitCode() {
        return this.StoreVisitCode;
    }

    public void setStoreVisitCode(String value) {
        this.StoreVisitCode = value;
    }

    public String getTmpInvoiceCodePDA() {
        return this.TmpInvoiceCodePDA;
    }

    public void setTmpInvoiceCodePDA(String value) {
        this.TmpInvoiceCodePDA = value;
    }

    public String getStoreID() {
        return this.StoreID;
    }

    public void setStoreID(String value) {
        this.StoreID = value;
    }

    public String getInvoiceDate() {
        return this.InvoiceDate;
    }

    public void setInvoiceDate(String value) {
        this.InvoiceDate = value;
    }

    public double getTotalBeforeTaxDis() {
        return this.TotalBeforeTaxDis;
    }

    public void setTotalBeforeTaxDis(double value) {
        this.TotalBeforeTaxDis = value;
    }

    public double getTaxAmt() {
        return this.TaxAmt;
    }

    public void setTaxAmt(double value) {
        this.TaxAmt = value;
    }

    public double getTotalDis() {
        return this.TotalDis;
    }

    public void setTotalDis(double value) {
        this.TotalDis = value;
    }

    public double getInvoiceVal() {
        return this.InvoiceVal;
    }

    public void setInvoiceVal(double value) {
        this.InvoiceVal = value;
    }

    public int getFreeTotal() {
        return this.FreeTotal;
    }

    public void setFreeTotal(int value) {
        this.FreeTotal = value;
    }

    public int getSstat() {
        return this.Sstat;
    }

    public void setSstat(int value) {
        this.Sstat = value;
    }

    public double getInvAfterDis() {
        return this.InvAfterDis;
    }

    public void setInvAfterDis(double value) {
        this.InvAfterDis = value;
    }

    public double getAddDis() {
        return this.AddDis;
    }

    public void setAddDis(double value) {
        this.AddDis = value;
    }

    public int getNoCoupon() {
        return this.NoCoupon;
    }

    public void setNoCoupon(int value) {
        this.NoCoupon = value;
    }

    public double getTotalCoupunAmount() {
        return this.TotalCoupunAmount;
    }

    public void setTotalCoupunAmount(double value) {
        this.TotalCoupunAmount = value;
    }

    public String getTransDate() {
        return this.TransDate;
    }

    public void setTransDate(String value) {
        this.TransDate = value;
    }

    public String getFlgInvoiceType() {
        return this.FlgInvoiceType;
    }

    public void setFlgInvoiceType(String value) {
        this.FlgInvoiceType = value;
    }

    public String getRouteNodeID() {
        return this.RouteNodeID;
    }

    public void setRouteNodeID(String value) {
        this.RouteNodeID = value;
    }

    public String getRouteNodetype() {
        return this.RouteNodeType;
    }

    public void setRouteNodetype(String value) {
        this.RouteNodeType = value;
    }

    public int getFlgProcessedInvoice() {
        return flgProcessedInvoice;
    }

    public void setFlgProcessedInvoice(int flgProcessedInvoice) {
        this.flgProcessedInvoice = flgProcessedInvoice;
    }

    public int getCycleID() {
        return CycleID;
    }

    public void setCycleID(int cycleID) {
        CycleID = cycleID;
    }

    public int getFlgDrctslsIndrctSls() {
        return flgDrctslsIndrctSls;
    }

    public void setFlgDrctslsIndrctSls(int flgDrctslsIndrctSls) {
        this.flgDrctslsIndrctSls = flgDrctslsIndrctSls;
    }

    public String getRouteNodeType() {
        return RouteNodeType;
    }

    public void setRouteNodeType(String routeNodeType) {
        RouteNodeType = routeNodeType;
    }
}