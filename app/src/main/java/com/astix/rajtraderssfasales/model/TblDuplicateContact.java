package com.astix.rajtraderssfasales.model;

import com.google.gson.annotations.SerializedName;

public class TblDuplicateContact {
    @SerializedName("flgDuplicate")
    private int flgDuplicate;


    @SerializedName("ExistingStoreName")
    private String ExistingStoreName;

    @SerializedName("ExistingPersonname")
    private String ExistingPersonname;

    @SerializedName("ExistingRoutename")
    private String RouteName;

    public String getRouteName() {
        return RouteName;
    }

    public void setRouteName(String routeName) {
        RouteName = routeName;
    }

    public int getFlgDuplicate() {
        return flgDuplicate;
    }

    public void setFlgDuplicate(int flgDuplicate) {
        this.flgDuplicate = flgDuplicate;
    }

    public String getExistingStoreName() {
        return ExistingStoreName;
    }

    public void setExistingStoreName(String existingStoreName) {
        ExistingStoreName = existingStoreName;
    }

    public String getExistingPersonname() {
        return ExistingPersonname;
    }

    public void setExistingPersonname(String existingPersonname) {
        ExistingPersonname = existingPersonname;
    }
}
