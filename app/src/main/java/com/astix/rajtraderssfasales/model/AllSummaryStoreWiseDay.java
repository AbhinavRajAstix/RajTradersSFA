
package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllSummaryStoreWiseDay {


    public List<TblStoreWiseDaySummary> getTblStoreWiseDaySummary() {
        return tblStoreWiseDaySummary;
    }

    public void seTblSKUWiseDaySummary(List<TblStoreWiseDaySummary> tblStoreWiseDaySummary) {
        this.tblStoreWiseDaySummary = tblStoreWiseDaySummary;
    }

    @SerializedName("tblStoreWiseDaySummary")
    @Expose
    private List<TblStoreWiseDaySummary> tblStoreWiseDaySummary = null;





}
