package com.astix.rajtraderssfasales.SignUpModule;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TblAllValidationData {

    @SerializedName("tblUserValidation")
    List<TblUserValidation> tblUserValidations;

    public List<TblUserValidation> getTblUserValidations() {
        return tblUserValidations;
    }

    public void setTblUserValidations(List<TblUserValidation> tblUserValidations) {
        this.tblUserValidations = tblUserValidations;
    }



}
