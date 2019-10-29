package com.tanaka.binge.Models;

/**
 * Created by Tanaka Mazi on 2019-10-20.
 * Copyright (c) 2019 All rights reserved.
 */


import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JWBody {


    public JWBody(String query, String contentType){
this.query = query;
//this.contentTypes.clear();
        List<String> contents = new ArrayList<>();
        contents.add(contentType);
this.contentTypes = contents;
    }

    @SerializedName("content_types")
    @Expose
    private List<String> contentTypes = null;
    @SerializedName("presentation_types")
    @Expose
    private Object presentationTypes;
    @SerializedName("providers")
    @Expose
    private Object providers;
    @SerializedName("genres")
    @Expose
    private Object genres;
    @SerializedName("languages")
    @Expose
    private Object languages;
    @SerializedName("release_year_from")
    @Expose
    private Object releaseYearFrom;
    @SerializedName("release_year_until")
    @Expose
    private Object releaseYearUntil;
    @SerializedName("monetization_types")
    @Expose
    private Object monetizationTypes;
    @SerializedName("min_price")
    @Expose
    private Object minPrice;
    @SerializedName("max_price")
    @Expose
    private Object maxPrice;
    @SerializedName("scoring_filter_types")
    @Expose
    private Object scoringFilterTypes;
    @SerializedName("cinema_release")
    @Expose
    private Object cinemaRelease;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("page_size")
    @Expose
    private Object pageSize;

    public List<String> getContentTypes() {
        return contentTypes;
    }

    public void setContentTypes(List<String> contentTypes) {
        this.contentTypes = contentTypes;
    }

    public Object getPresentationTypes() {
        return presentationTypes;
    }

    public void setPresentationTypes(Object presentationTypes) {
        this.presentationTypes = presentationTypes;
    }

    public Object getProviders() {
        return providers;
    }

    public void setProviders(Object providers) {
        this.providers = providers;
    }

    public Object getGenres() {
        return genres;
    }

    public void setGenres(Object genres) {
        this.genres = genres;
    }

    public Object getLanguages() {
        return languages;
    }

    public void setLanguages(Object languages) {
        this.languages = languages;
    }

    public Object getReleaseYearFrom() {
        return releaseYearFrom;
    }

    public void setReleaseYearFrom(Object releaseYearFrom) {
        this.releaseYearFrom = releaseYearFrom;
    }

    public Object getReleaseYearUntil() {
        return releaseYearUntil;
    }

    public void setReleaseYearUntil(Object releaseYearUntil) {
        this.releaseYearUntil = releaseYearUntil;
    }

    public Object getMonetizationTypes() {
        return monetizationTypes;
    }

    public void setMonetizationTypes(Object monetizationTypes) {
        this.monetizationTypes = monetizationTypes;
    }

    public Object getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Object minPrice) {
        this.minPrice = minPrice;
    }

    public Object getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Object maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Object getScoringFilterTypes() {
        return scoringFilterTypes;
    }

    public void setScoringFilterTypes(Object scoringFilterTypes) {
        this.scoringFilterTypes = scoringFilterTypes;
    }

    public Object getCinemaRelease() {
        return cinemaRelease;
    }

    public void setCinemaRelease(Object cinemaRelease) {
        this.cinemaRelease = cinemaRelease;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Object getPageSize() {
        return pageSize;
    }

    public void setPageSize(Object pageSize) {
        this.pageSize = pageSize;
    }

}
