package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.SerializedName;

public class TblOrderConfirmFlagValidation {

    @SerializedName("flgMarkStatus")
    int flgMarkStatus;

    public int getFlgMarkStatus() {
        return flgMarkStatus;
    }

    public void setFlgMarkStatus(int flgMarkStatus) {
        this.flgMarkStatus = flgMarkStatus;
    }
}
