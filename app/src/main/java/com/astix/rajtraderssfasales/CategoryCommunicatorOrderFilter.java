package com.astix.rajtraderssfasales;

import android.app.Dialog;

import com.astix.rajtraderssfasales.model.TblOnlyCategoryMasterForRetriving;
import com.astix.rajtraderssfasales.model.TblProductTypeMasterForRetriving;

import java.util.ArrayList;

public interface CategoryCommunicatorOrderFilter {

	public void selectedOption(String selectedCategory, String selectedText, Dialog dialog, int filterType, int parentID, int parentPosition, Boolean isParentSelected, Boolean isChildSelected, TblProductTypeMasterForRetriving tblProductTypeMasterForRetrivingGlobal, ArrayList<TblOnlyCategoryMasterForRetriving> tblOnlyCategoryMasterForRetrivingGlobal, int crntChildID);


}
