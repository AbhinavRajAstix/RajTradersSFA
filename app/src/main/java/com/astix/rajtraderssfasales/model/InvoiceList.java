
package com.astix.rajtraderssfasales.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvoiceList {

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getpDAOrderId() {
        return pDAOrderId;
    }

    public void setpDAOrderId(String pDAOrderId) {
        this.pDAOrderId = pDAOrderId;
    }

    @SerializedName("Customer")
    @Expose
    private String customer;

    @Override
    public String toString() {
        return "InvoiceList{" +
                "customer='" + customer + '\'' +
                ", pDAOrderId='" + pDAOrderId + '\'' +
                '}';
    }

    @SerializedName("PDAOrderId")
    @Expose
    private String pDAOrderId;



}
