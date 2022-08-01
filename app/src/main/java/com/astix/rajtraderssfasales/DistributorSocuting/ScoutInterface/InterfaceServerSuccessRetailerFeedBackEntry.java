package com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface;


import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblServerEntryStatus;

import java.util.List;

public interface InterfaceServerSuccessRetailerFeedBackEntry {
    public void successRetailerAddressFeebackRecord(int flgCalledFrom, List<TblServerEntryStatus> tblServerEntryStatusList);
    public void failureRetailerAddressFeebackRecord(int flgCalledFrom);
}
