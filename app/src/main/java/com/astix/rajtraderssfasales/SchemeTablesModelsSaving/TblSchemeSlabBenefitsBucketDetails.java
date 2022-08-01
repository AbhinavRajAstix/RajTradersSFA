package com.astix.rajtraderssfasales.SchemeTablesModelsSaving;

public class TblSchemeSlabBenefitsBucketDetails {
    private int RowID;

    public int getRowID() {
        return RowID;
    }

    public void setRowID(int rowID) {
        RowID = rowID;
    }

    public int getSchemeID() {
        return SchemeID;
    }

    public void setSchemeID(int schemeID) {
        SchemeID = schemeID;
    }

    public int getSchemeSlabID() {
        return SchemeSlabID;
    }

    public void setSchemeSlabID(int schemeSlabID) {
        SchemeSlabID = schemeSlabID;
    }

    public int getBucketID() {
        return BucketID;
    }

    public void setBucketID(int bucketID) {
        BucketID = bucketID;
    }

    public int getSubBucketID() {
        return SubBucketID;
    }

    public void setSubBucketID(int subBucketID) {
        SubBucketID = subBucketID;
    }

    public int getBenSubBucketType() {
        return BenSubBucketType;
    }

    public void setBenSubBucketType(int benSubBucketType) {
        BenSubBucketType = benSubBucketType;
    }

    public String getBenDiscApplied() {
        return BenDiscApplied;
    }

    public void setBenDiscApplied(String benDiscApplied) {
        BenDiscApplied = benDiscApplied;
    }

    public String getCouponCode() {
        return CouponCode;
    }

    public void setCouponCode(String couponCode) {
        CouponCode = couponCode;
    }

    public String getBenSubBucketValue() {
        return BenSubBucketValue;
    }

    public void setBenSubBucketValue(String benSubBucketValue) {
        BenSubBucketValue = benSubBucketValue;
    }

    public double getPer() {
        return Per;
    }

    public void setPer(double per) {
        Per = per;
    }

    public double getUOM() {
        return UOM;
    }

    public void setUOM(double UOM) {
        this.UOM = UOM;
    }

    public int getProRata() {
        return ProRata;
    }

    public void setProRata(int proRata) {
        ProRata = proRata;
    }

    public int getIsDiscountOnTotalAmount() {
        return IsDiscountOnTotalAmount;
    }

    public void setIsDiscountOnTotalAmount(int isDiscountOnTotalAmount) {
        IsDiscountOnTotalAmount = isDiscountOnTotalAmount;
    }

    private int SchemeID;
    private int SchemeSlabID;
    private int BucketID;
    private int SubBucketID;
    private int BenSubBucketType;
    private String BenDiscApplied;
    private String CouponCode;
    private String BenSubBucketValue;
    private double Per;
    private double UOM;
    private int ProRata;
    private int IsDiscountOnTotalAmount;
}
