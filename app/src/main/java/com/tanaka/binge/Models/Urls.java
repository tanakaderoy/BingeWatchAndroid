package com.tanaka.binge.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Urls {

    @SerializedName("standard_web")
    @Expose
    private String standardWeb;
    @SerializedName("deeplink_android_tv")
    @Expose
    private String deeplinkAndroidTv;
    @SerializedName("deeplink_fire_tv")
    @Expose
    private String deeplinkFireTv;
    @SerializedName("deeplink_tvos")
    @Expose
    private String deeplinkTvos;

    public String getStandardWeb() {
        return standardWeb;
    }

    public void setStandardWeb(String standardWeb) {
        this.standardWeb = standardWeb;
    }

    public String getDeeplinkAndroidTv() {
        return deeplinkAndroidTv;
    }

    public void setDeeplinkAndroidTv(String deeplinkAndroidTv) {
        this.deeplinkAndroidTv = deeplinkAndroidTv;
    }

    public String getDeeplinkFireTv() {
        return deeplinkFireTv;
    }

    public void setDeeplinkFireTv(String deeplinkFireTv) {
        this.deeplinkFireTv = deeplinkFireTv;
    }

    public String getDeeplinkTvos() {
        return deeplinkTvos;
    }

    public void setDeeplinkTvos(String deeplinkTvos) {
        this.deeplinkTvos = deeplinkTvos;
    }

}
