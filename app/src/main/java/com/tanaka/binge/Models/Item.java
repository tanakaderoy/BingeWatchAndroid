package com.tanaka.binge.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {

    @SerializedName("jw_entity_id")
    @Expose
    private String jwEntityId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("full_path")
    @Expose
    private String fullPath;
    @SerializedName("full_paths")
    @Expose
    private FullPaths fullPaths;
    @SerializedName("poster")
    @Expose
    private String poster;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("original_release_year")
    @Expose
    private Integer originalReleaseYear;
    @SerializedName("tmdb_popularity")
    @Expose
    private Double tmdbPopularity;
    @SerializedName("object_type")
    @Expose
    private String objectType;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("offers")
    @Expose
    private List<Offer> offers = null;
    @SerializedName("scoring")
    @Expose
    private List<Scoring> scoring = null;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("age_certification")
    @Expose
    private String ageCertification;
    @SerializedName("max_season_number")
    @Expose
    private Integer maxSeasonNumber;

    public String getJwEntityId() {
        return jwEntityId;
    }

    public void setJwEntityId(String jwEntityId) {
        this.jwEntityId = jwEntityId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public FullPaths getFullPaths() {
        return fullPaths;
    }

    public void setFullPaths(FullPaths fullPaths) {
        this.fullPaths = fullPaths;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Integer getOriginalReleaseYear() {
        return originalReleaseYear;
    }

    public void setOriginalReleaseYear(Integer originalReleaseYear) {
        this.originalReleaseYear = originalReleaseYear;
    }

    public Double getTmdbPopularity() {
        return tmdbPopularity;
    }

    public void setTmdbPopularity(Double tmdbPopularity) {
        this.tmdbPopularity = tmdbPopularity;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public List<Scoring> getScoring() {
        return scoring;
    }

    public void setScoring(List<Scoring> scoring) {
        this.scoring = scoring;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getAgeCertification() {
        return ageCertification;
    }

    public void setAgeCertification(String ageCertification) {
        this.ageCertification = ageCertification;
    }

    public Integer getMaxSeasonNumber() {
        return maxSeasonNumber;
    }

    public void setMaxSeasonNumber(Integer maxSeasonNumber) {
        this.maxSeasonNumber = maxSeasonNumber;
    }

}
