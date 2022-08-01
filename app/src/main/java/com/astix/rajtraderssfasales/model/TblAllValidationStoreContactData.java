package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TblAllValidationStoreContactData {


    @SerializedName("tblStoreValidationContact")
    List<TblStoreValidationContact> tblStoreValidationContact;

    public List<TblStoreValidationContact> getTblStoreValidationContact() {
        return tblStoreValidationContact;
    }

    public void setTblStoreValidationContact(List<TblStoreValidationContact> tblStoreValidationContact) {
        this.tblStoreValidationContact = tblStoreValidationContact;
    }
}
