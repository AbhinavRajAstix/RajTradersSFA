package com.astix.rajtraderssfasales;

import android.widget.EditText;

import java.util.LinkedHashMap;

public class InRefStockFilledDataModel {

    private LinkedHashMap<String,String> hmapInRefPrdctStock=new LinkedHashMap<String,String>();

    //Product Quantity
    private EditText lastEditText;
    private EditText focusLostEditText;

    public EditText getFocusLostEditText() {
        return focusLostEditText;
    }

    public void setFocusLostEditText(EditText focusLostEditText) {
        this.focusLostEditText = focusLostEditText;
    }
    public void setPrdctStock(String prdctId,String prdctQty)
    {
        hmapInRefPrdctStock.put(prdctId,prdctQty);
    }
    public String getPrdctStock(String prdctId)
    {
        String prdctStk="";
        if((hmapInRefPrdctStock!=null) && (hmapInRefPrdctStock.containsKey(prdctId)))
        {
            prdctStk=hmapInRefPrdctStock.get(prdctId);
        }
        return prdctStk;
    }
    public void removePrdctStk(String prdctId)
    {
        if((hmapInRefPrdctStock!=null) && (hmapInRefPrdctStock.containsKey(prdctId)))
        {
            hmapInRefPrdctStock.remove(prdctId);
        }
    }

    public int gethmapPrdctOrderQtySize()
    {
        int totalSize=0;
        if(hmapInRefPrdctStock!=null)
        {
            totalSize=hmapInRefPrdctStock.size();
        }
        return totalSize;
    }

    public LinkedHashMap<String, String> getHmapPrdctStock() {
        return hmapInRefPrdctStock;
    }
}