package com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels;

public class TblDistributorScoutingParameterData {

    String PDACode;

    public String getPDACode() {
        return PDACode;
    }

    public void setPDACode(String PDACode) {
        this.PDACode = PDACode;
    }

    @Override
    public String toString() {
        return "TblDistributorScoutingParameterData{" +
                "PDACode='" + PDACode + '\'' +
                '}';
    }
}
