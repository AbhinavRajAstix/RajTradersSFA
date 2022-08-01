
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatedCartonModel {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("storeId")
    @Expose
    private int storeId;
    @SerializedName("PrdId")
    @Expose
    private String PrdId;
    @SerializedName("PrdCategory")
    @Expose
    private String PrdCategory;
    @SerializedName("PrdSubCategory")
    @Expose
    private String PrdSubCategory;
    @SerializedName("cartonCount")
    @Expose
    private int cartonCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getPrdId() {
        return PrdId;
    }

    public void setPrdId(String prdId) {
        PrdId = prdId;
    }

    public String getPrdCategory() {
        return PrdCategory;
    }

    public void setPrdCategory(String prdCategory) {
        PrdCategory = prdCategory;
    }

    public String getPrdSubCategory() {
        return PrdSubCategory;
    }

    public void setPrdSubCategory(String prdSubCategory) {
        PrdSubCategory = prdSubCategory;
    }

    public int getCartonCount() {
        return cartonCount;
    }

    public void setCartonCount(int cartonCount) {
        this.cartonCount = cartonCount;
    }
}
