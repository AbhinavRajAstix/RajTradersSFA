package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPrdMstrTransactionUOMConfig {

    @SerializedName("NodeID")
    @Expose
    private int NodeID;
    @SerializedName("NodeType")
    @Expose
    private int NodeType;
    @SerializedName("BUOMID")
    @Expose
    private int BUOMID;
    @SerializedName("flgDistOrder")
    @Expose
    private int flgDistOrder;
    @SerializedName("flgDistInv")
    @Expose
    private int flgDistInv;
    @SerializedName("flgStoreCheck")
    @Expose
    private int flgStoreCheck;
    @SerializedName("flgRetailUnit")
    @Expose
    private int flgRetailUnit;
    @SerializedName("flgTransactionData")
    @Expose
    private int flgTransactionData;

    public int getNodeID() {
        return NodeID;
    }

    public void setNodeID(int nodeID) {
        NodeID = nodeID;
    }

    public int getNodeType() {
        return NodeType;
    }

    public void setNodeType(int nodeType) {
        NodeType = nodeType;
    }

    public int getBUOMID() {
        return BUOMID;
    }

    public void setBUOMID(int BUOMID) {
        this.BUOMID = BUOMID;
    }

    public int getFlgDistOrder() {
        return flgDistOrder;
    }

    public void setFlgDistOrder(int flgDistOrder) {
        this.flgDistOrder = flgDistOrder;
    }

    public int getFlgDistInv() {
        return flgDistInv;
    }

    public void setFlgDistInv(int flgDistInv) {
        this.flgDistInv = flgDistInv;
    }

    public int getFlgStoreCheck() {
        return flgStoreCheck;
    }

    public void setFlgStoreCheck(int flgStoreCheck) {
        this.flgStoreCheck = flgStoreCheck;
    }

    public int getFlgRetailUnit() {
        return flgRetailUnit;
    }

    public void setFlgRetailUnit(int flgRetailUnit) {
        this.flgRetailUnit = flgRetailUnit;
    }

    public int getFlgTransactionData() {
        return flgTransactionData;
    }

    public void setFlgTransactionData(int flgTransactionData) {
        this.flgTransactionData = flgTransactionData;
    }
}
