package com.tanaka.binge;

/**
 * Created by Tanaka Mazi on 2019-10-28.
 * Copyright (c), 2019 All rights reserved.
 */
public enum ImageSize {
    w92("w92"),
    w154("w154"),
    w185("w185"),
    w342("w342"),
    w500("w500"),
    w780("w780"),
    original("original");

    public final String size;

    ImageSize(String size) {
        this.size = size;
    }
}
