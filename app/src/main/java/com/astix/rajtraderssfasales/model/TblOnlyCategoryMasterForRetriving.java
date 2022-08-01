
package com.astix.rajtraderssfasales.model;

public class TblOnlyCategoryMasterForRetriving {


    private int CategoryNodeID;

    private String Category;
    private int parentID;

    private Boolean isSelected;

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public int getCategoryNodeID() {
        return CategoryNodeID;
    }

    public void setCategoryNodeID(int categoryNodeID) {
        CategoryNodeID = categoryNodeID;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }
}
