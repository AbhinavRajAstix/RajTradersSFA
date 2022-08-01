
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllAttendanceAPIInsertTablesModel {

    @SerializedName("tblAttendanceResponse")
    @Expose
    private List<TblAttendanceRepose> tblAttendanceReposes = null;

    public List<TblAttendanceRepose> getTblAttendanceReposes() {
        return tblAttendanceReposes;
    }

    public void setTblAttendanceReposes(List<TblAttendanceRepose> tblAttendanceReposes) {
        this.tblAttendanceReposes = tblAttendanceReposes;
    }
}
