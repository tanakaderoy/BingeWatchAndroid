package com.tanaka.binge.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Scoring {

    @SerializedName("provider_type")
    @Expose
    private String providerType;
    @SerializedName("value")
    @Expose
    private Double value;

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

}
