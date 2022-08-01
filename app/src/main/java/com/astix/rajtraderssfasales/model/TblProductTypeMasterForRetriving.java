
package com.astix.rajtraderssfasales.model;

import java.util.ArrayList;

public class TblProductTypeMasterForRetriving {


    private int ProductTypeNodeID;

    private String ProductType;

    private ArrayList<TblOnlyCategoryMasterForRetriving> tblOnlyCategoryMasterForRetrivingList;


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

    public ArrayList<TblOnlyCategoryMasterForRetriving> getTblOnlyCategoryMasterForRetrivingList() {
        return tblOnlyCategoryMasterForRetrivingList;
    }

    public void setTblOnlyCategoryMasterForRetrivingList(ArrayList<TblOnlyCategoryMasterForRetriving> tblOnlyCategoryMasterForRetrivingList) {
        this.tblOnlyCategoryMasterForRetrivingList = tblOnlyCategoryMasterForRetrivingList;
    }

    boolean isExpanded;
    int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
    boolean isChildExpanded;

    public boolean isChildExpanded() {
        return isChildExpanded;
    }

    public void setChildExpanded(boolean childExpanded) {
        isChildExpanded = childExpanded;
    }
    int parentPsition;

    public int getParentPsition() {
        return parentPsition;
    }

    public void setParentPsition(int parentPsition) {
        this.parentPsition = parentPsition;
    }
}
