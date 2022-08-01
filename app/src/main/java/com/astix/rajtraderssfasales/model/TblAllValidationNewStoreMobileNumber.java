package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TblAllValidationNewStoreMobileNumber {


    @SerializedName("tblDuplicateContact")
    List<TblDuplicateContact> tblDuplicateContacts;

    public List<TblDuplicateContact> getTblDuplicateContacts() {
        return tblDuplicateContacts;
    }

    public void setTblDuplicateContacts(List<TblDuplicateContact> tblDuplicateContacts) {
        this.tblDuplicateContacts = tblDuplicateContacts;
    }
}
