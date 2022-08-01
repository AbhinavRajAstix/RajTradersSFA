
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblAttendanceRepose {

    @SerializedName("flgTodaysAttendance")
    @Expose
    private int flgStatus;

    public int getFlgStatus() {
        return flgStatus;
    }

    public void setFlgStatus(int flgStatus) {
        this.flgStatus = flgStatus;
    }
}
