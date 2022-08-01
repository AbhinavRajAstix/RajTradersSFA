package com.astix.rajtraderssfasales.SchemeTablesModelsSaving;

    public class TblSchemeStoreMapping {
    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public int getSchemeID() {
        return SchemeID;
    }

    public void setSchemeID(int schemeID) {
        SchemeID = schemeID;
    }

    private String StoreID;
    private int SchemeID;

        private int IsNewStore=0;

        public int getIsNewStore() {
            return IsNewStore;
        }

        public void setIsNewStore(int isNewStore) {
            IsNewStore = isNewStore;
        }

        public int getStoreNodeType() {
            return StoreNodeType;
        }

        public void setStoreNodeType(int storeNodeType) {
            StoreNodeType = storeNodeType;
        }

        private int StoreNodeType;
}
