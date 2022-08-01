
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblCategoryMaster {

    @SerializedName("NODEID")
    @Expose
    private int nODEID;
    @SerializedName("CATEGORY")
    @Expose
    private String cATEGORY;

    @SerializedName("ProductTypeNodeID")
    @Expose
    private int ProductTypeNodeID;

    @SerializedName("ProductType")
    @Expose
    private String ProductType;

    @SerializedName("PrdClassOrdr")
    @Expose
    private int PrdClassOrdr;

    @SerializedName("PrdTypeOrdr")
    @Expose
    private int PrdTypeOrdr;

    public Integer getNODEID() {
        return nODEID;
    }

    public void setNODEID(Integer nODEID) {
        this.nODEID = nODEID;
    }

    public String getCATEGORY() {
        return cATEGORY;
    }

    public void setCATEGORY(String cATEGORY) {
        this.cATEGORY = cATEGORY;
    }

    public int getProductTypeNodeID() {
        return ProductTypeNodeID;
    }

    public void setProductTypeNodeID(int productTypeNodeID) {
        ProductTypeNodeID = productTypeNodeID;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public int getPrdClassOrdr() {
        return PrdClassOrdr;
    }

    public void setPrdClassOrdr(int prdClassOrdr) {
        PrdClassOrdr = prdClassOrdr;
    }

    public int getPrdTypeOrdr() {
        return PrdTypeOrdr;
    }

    public void setPrdTypeOrdr(int prdTypeOrdr) {
        PrdTypeOrdr = prdTypeOrdr;
    }
}
