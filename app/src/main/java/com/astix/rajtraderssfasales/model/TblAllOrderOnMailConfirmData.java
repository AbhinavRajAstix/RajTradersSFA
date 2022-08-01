package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TblAllOrderOnMailConfirmData {


    @SerializedName("tblOrderConfirmFlagValidation")
    List<TblOrderConfirmFlagValidation> tblOrderConfirmFlagValidation;

    public List<TblOrderConfirmFlagValidation> getTblOrderConfirmFlagValidation() {
        return tblOrderConfirmFlagValidation;
    }

    public void setTblOrderConfirmFlagValidation(List<TblOrderConfirmFlagValidation> tblOrderConfirmFlagValidation) {
        this.tblOrderConfirmFlagValidation = tblOrderConfirmFlagValidation;
    }
}
