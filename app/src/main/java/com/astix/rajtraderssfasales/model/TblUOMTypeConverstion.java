
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblUOMTypeConverstion {

    @SerializedName("UOMType")
    @Expose
    private String UOMType;
    @SerializedName("RelConversionUnits")
    @Expose
    private double RelConversionUnits;

    public String getUOMType() {
        return UOMType;
    }

    public void setUOMType(String UOMType) {
        this.UOMType = UOMType;
    }

    public double getRelConversionUnits() {
        return RelConversionUnits;
    }

    public void setRelConversionUnits(double relConversionUnits) {
        RelConversionUnits = relConversionUnits;
    }
}
