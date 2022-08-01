package com.astix.rajtraderssfasales.SchemeTablesModelsSaving;

public class TblSchemeSlabDetail {
private int SchemeID;
private int SchemeSlabID;

    public int getSchemeID() {
        return SchemeID;
    }

    public void setSchemeID(int schemeID) {
        SchemeID = schemeID;
    }

    public int getSchemeSlabID() {
        return SchemeSlabID;
    }

    public void setSchemeSlabID(int schemeSlabID) {
        SchemeSlabID = schemeSlabID;
    }

    public String getSchemeSlabDesc() {
        return SchemeSlabDesc;
    }

    public void setSchemeSlabDesc(String schemeSlabDesc) {
        SchemeSlabDesc = schemeSlabDesc;
    }

    public String getBenifitDescr() {
        return BenifitDescr;
    }

    public void setBenifitDescr(String benifitDescr) {
        BenifitDescr = benifitDescr;
    }

    private String SchemeSlabDesc;
private String BenifitDescr;
}
