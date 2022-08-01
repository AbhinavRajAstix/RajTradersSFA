package com.astix.rajtraderssfasales;

import com.astix.rajtraderssfasales.model.TblDuplicateContact;
import com.astix.rajtraderssfasales.model.TblPDARouteChangeApproval;

public interface InterfaceRetrofitNewStoreNumberValidation {
    public void successNewStoreNumberValidation(TblDuplicateContact tblPDARouteChangeApproval,String Number);
    public void failureNewStoreNumberValidation();
}
