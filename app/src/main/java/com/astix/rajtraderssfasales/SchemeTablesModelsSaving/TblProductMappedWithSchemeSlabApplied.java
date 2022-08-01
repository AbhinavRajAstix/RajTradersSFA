package com.astix.rajtraderssfasales.SchemeTablesModelsSaving;

public class TblProductMappedWithSchemeSlabApplied {
    private String StoreId;
    private int ProductID;


    private  int flgInCarton;

    public int getFlgInCarton() {
        return flgInCarton;
    }

    public void setFlgInCarton(int flgInCarton) {
        this.flgInCarton = flgInCarton;
    }

    public String getTmpInvoiceCodePDA() {
        return TmpInvoiceCodePDA;
    }

    public void setTmpInvoiceCodePDA(String tmpInvoiceCodePDA) {
        TmpInvoiceCodePDA = tmpInvoiceCodePDA;
    }

    private String TmpInvoiceCodePDA;
    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public int getSchSlabId() {
        return schSlabId;
    }

    public void setSchSlabId(int schSlabId) {
        this.schSlabId = schSlabId;
    }

    public int getSchmIdMapped() {
        return schmIdMapped;
    }

    public void setSchmIdMapped(int schmIdMapped) {
        this.schmIdMapped = schmIdMapped;
    }

    public int getSstat() {
        return Sstat;
    }

    public void setSstat(int sstat) {
        Sstat = sstat;
    }

    public String getOrderIDPDA() {
        return OrderIDPDA;
    }

    public void setOrderIDPDA(String orderIDPDA) {
        OrderIDPDA = orderIDPDA;
    }

    private int schSlabId;
    private int schmIdMapped;
    private int Sstat;
    private String OrderIDPDA;
}
