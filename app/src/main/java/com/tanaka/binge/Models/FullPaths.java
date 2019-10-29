package com.tanaka.binge.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tanaka Mazi on 2019-10-20.
 * Copyright (c) 2019 All rights reserved.
 */




public class FullPaths {

    @SerializedName("SHOW_DETAIL_OVERVIEW")
    @Expose
    private String sHOWDETAILOVERVIEW;

    public String getSHOWDETAILOVERVIEW() {
        return sHOWDETAILOVERVIEW;
    }

    public void setSHOWDETAILOVERVIEW(String sHOWDETAILOVERVIEW) {
        this.sHOWDETAILOVERVIEW = sHOWDETAILOVERVIEW;
    }

}
