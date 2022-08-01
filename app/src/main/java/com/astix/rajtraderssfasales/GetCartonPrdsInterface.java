package com.astix.rajtraderssfasales;

import com.astix.rajtraderssfasales.model.TblStoreProductMappingForDisplay;

import java.util.List;

public interface GetCartonPrdsInterface {
    public void getAllCartonPrds(List<TblStoreProductMappingForDisplay> tblStoreProductMappingForDisplaysCarton,ProductCartonFilledDataModel productCartonFilledDataModel);
}
