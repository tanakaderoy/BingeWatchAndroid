package com.tanaka.binge.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("monetization_type")
    @Expose
    private String monetizationType;
    @SerializedName("provider_id")
    @Expose
    private Integer providerId;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("urls")
    @Expose
    private Urls urls;
    @SerializedName("presentation_type")
    @Expose
    private String presentationType;
    @SerializedName("element_count")
    @Expose
    private Integer elementCount;
    @SerializedName("new_element_count")
    @Expose
    private Integer newElementCount;
    @SerializedName("date_created_provider_id")
    @Expose
    private String dateCreatedProviderId;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("country")
    @Expose
    private String country;

    private String providerName = "";

    private String iconURL = "";

    public void setIconURL(String iconURL) {
        this.iconURL = "https://images.justwatch.com/icon/" + iconURL + "/s100";
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMonetizationType() {
        return monetizationType;
    }

    public void setMonetizationType(String monetizationType) {
        this.monetizationType = monetizationType;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public String getPresentationType() {
        return presentationType;
    }

    public void setPresentationType(String presentationType) {
        this.presentationType = presentationType;
    }

    public Integer getElementCount() {
        return elementCount;
    }

    public void setElementCount(Integer elementCount) {
        this.elementCount = elementCount;
    }

    public Integer getNewElementCount() {
        return newElementCount;
    }

    public void setNewElementCount(Integer newElementCount) {
        this.newElementCount = newElementCount;
    }

    public String getDateCreatedProviderId() {
        return dateCreatedProviderId;
    }

    public void setDateCreatedProviderId(String dateCreatedProviderId) {
        this.dateCreatedProviderId = dateCreatedProviderId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
