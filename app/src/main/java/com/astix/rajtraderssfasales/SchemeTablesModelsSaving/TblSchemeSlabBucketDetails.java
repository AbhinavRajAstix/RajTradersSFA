package com.astix.rajtraderssfasales.SchemeTablesModelsSaving;

public class TblSchemeSlabBucketDetails {
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

    private int RowID;
    private int SchemeID;

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

    public int getSlabSubBucketType() {
        return SlabSubBucketType;
    }

    public void setSlabSubBucketType(int slabSubBucketType) {
        SlabSubBucketType = slabSubBucketType;
    }

    public String getSlabSubBucketValue() {
        return SlabSubBucketValue;
    }

    public void setSlabSubBucketValue(String slabSubBucketValue) {
        SlabSubBucketValue = slabSubBucketValue;
    }

    public String getSubBucketValType() {
        return SubBucketValType;
    }

    public void setSubBucketValType(String subBucketValType) {
        SubBucketValType = subBucketValType;
    }

    private int SchemeSlabID;
    private int BucketID;
    private int SubBucketID;
    private int SlabSubBucketType;
    private String SlabSubBucketValue;
    private String SubBucketValType;
}
