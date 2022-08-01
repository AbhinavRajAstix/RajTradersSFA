package com.astix.rajtraderssfasales.SchemeTablesModelsSaving;

public class TblAlrtVal {
    private String StoreId;

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

    public String getSpinnerVal() {
        return SpinnerVal;
    }

    public void setSpinnerVal(String spinnerVal) {
        SpinnerVal = spinnerVal;
    }

    public String getSpinnerPosition() {
        return SpinnerPosition;
    }

    public void setSpinnerPosition(String spinnerPosition) {
        SpinnerPosition = spinnerPosition;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public int getSchSlabId() {
        return schSlabId;
    }

    public void setSchSlabId(int schSlabId) {
        this.schSlabId = schSlabId;
    }

    public String getSchmAlrtId() {
        return schmAlrtId;
    }

    public void setSchmAlrtId(String schmAlrtId) {
        this.schmAlrtId = schmAlrtId;
    }

    public String getOrderIDPDA() {
        return OrderIDPDA;
    }

    public void setOrderIDPDA(String orderIDPDA) {
        OrderIDPDA = orderIDPDA;
    }

    private int ProductID;
    private  String SpinnerVal;
    private  String SpinnerPosition;
    private String Product;
    private  int schSlabId;
    private String schmAlrtId;
    private String OrderIDPDA;
}
