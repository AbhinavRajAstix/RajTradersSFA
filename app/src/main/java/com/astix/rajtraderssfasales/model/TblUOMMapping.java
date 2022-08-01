
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblUOMMapping {
    @SerializedName("RowID")
    @Expose
    private int RowID;
    @SerializedName("NodeID")
    @Expose
    private int nodeID;
    @SerializedName("NodeType")
    @Expose
    private int nodeType;
    @SerializedName("BaseUOMID")
    @Expose
    private int baseUOMID;
    @SerializedName("PackUOMID")
    @Expose
    private int packUOMID;
    @SerializedName("RelConversionUnits")
    @Expose
    private double relConversionUnits;
    @SerializedName("flgVanLoading")
    @Expose
    private int flgVanLoading;

    public int getNodeID() {
        return nodeID;
    }

    public void setNodeID(int nodeID) {
        this.nodeID = nodeID;
    }

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public int getBaseUOMID() {
        return baseUOMID;
    }

    public void setBaseUOMID(int baseUOMID) {
        this.baseUOMID = baseUOMID;
    }

    public int getPackUOMID() {
        return packUOMID;
    }

    public void setPackUOMID(int packUOMID) {
        this.packUOMID = packUOMID;
    }

    public double getRelConversionUnits() {
        return relConversionUnits;
    }

    public void setRelConversionUnits(double relConversionUnits) {
        this.relConversionUnits = relConversionUnits;
    }

    public int getFlgVanLoading() {
        return flgVanLoading;
    }

    public void setFlgVanLoading(int flgVanLoading) {
        this.flgVanLoading = flgVanLoading;
    }

    public int getRowID() {
        return RowID;
    }

    public void setRowID(int rowID) {
        RowID = rowID;
    }

    @SerializedName("flgRetailUnit")
    @Expose
    private int flgRetailUnit;

    public int getFlgRetailUnit() {
        return flgRetailUnit;
    }

    public void setFlgRetailUnit(int flgRetailUnit) {
        this.flgRetailUnit = flgRetailUnit;
    }

    @SerializedName("SNo")
    @Expose
    private int SNo;

    public int getSNo() {
        return SNo;
    }

    public void setSNo(int SNo) {
        this.SNo = SNo;
    }

    private String UOMName;

    public String getUOMName() {
        return UOMName;
    }

    public void setUOMName(String UOMName) {
        this.UOMName = UOMName;
    }

    private int flgIsDefaultUOM;

    public int getFlgIsDefaultUOM() {
        return flgIsDefaultUOM;
    }

    public void setFlgIsDefaultUOM(int flgIsDefaultUOM) {
        this.flgIsDefaultUOM = flgIsDefaultUOM;
    }
}
