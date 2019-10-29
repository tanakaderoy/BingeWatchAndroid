package com.tanaka.binge.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Packages {

@SerializedName("android_tv")
@Expose
private String androidTv;
@SerializedName("fire_tv")
@Expose
private String fireTv;
@SerializedName("tvos")
@Expose
private String tvos;

public String getAndroidTv() {
return androidTv;
}

public void setAndroidTv(String androidTv) {
this.androidTv = androidTv;
}

public String getFireTv() {
return fireTv;
}

public void setFireTv(String fireTv) {
this.fireTv = fireTv;
}

public String getTvos() {
return tvos;
}

public void setTvos(String tvos) {
this.tvos = tvos;
}

}
