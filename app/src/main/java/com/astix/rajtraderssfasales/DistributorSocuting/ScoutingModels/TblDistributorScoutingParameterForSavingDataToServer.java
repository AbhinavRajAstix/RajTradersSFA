package com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels;

public class TblDistributorScoutingParameterForSavingDataToServer {

    TblPotentialDistributor tblPotentialDistributor;

    public TblPotentialDistributor getTblPotentialDistributor() {
        return tblPotentialDistributor;
    }

    public void setTblPotentialDistributor(TblPotentialDistributor tblPotentialDistributor) {
        this.tblPotentialDistributor = tblPotentialDistributor;
    }

    @Override
    public String toString() {
        return "TblDistributorScoutingParameterForSavingDataToServer{" +
                "tblPotentialDistributor=" + tblPotentialDistributor +
                '}';
    }
}
