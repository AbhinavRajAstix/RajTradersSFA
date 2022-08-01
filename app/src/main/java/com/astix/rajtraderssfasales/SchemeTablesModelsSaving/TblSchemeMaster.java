package com.astix.rajtraderssfasales.SchemeTablesModelsSaving;

import com.google.gson.annotations.SerializedName;

public class TblSchemeMaster {

    @SerializedName("SchemeName")
    private String schemeName;


    @SerializedName("SchemeDescription")
    private String schemeDescription;

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getSchemeDescription() {
        return schemeDescription;
    }

    public void setSchemeDescription(String schemeDescription) {
        this.schemeDescription = schemeDescription;
    }
}
