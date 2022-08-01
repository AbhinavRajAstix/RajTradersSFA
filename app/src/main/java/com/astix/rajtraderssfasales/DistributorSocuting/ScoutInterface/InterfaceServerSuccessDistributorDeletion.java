package com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface;


import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributor;

public interface InterfaceServerSuccessDistributorDeletion {
    public void successRecordDelete(TblPotentialDistributor tblPotentialDistributorForDelete);
    public void failureRecordDelete();
}
