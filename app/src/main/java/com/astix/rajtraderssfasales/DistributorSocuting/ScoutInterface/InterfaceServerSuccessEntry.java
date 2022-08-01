package com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface;


import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblServerEntryStatus;

import java.util.List;

public interface InterfaceServerSuccessEntry {
    public void successRecord(int flgCalledFrom, List<TblServerEntryStatus> tblServerEntryStatusList);
    public void failureRecord(int flgCalledFrom);
}
