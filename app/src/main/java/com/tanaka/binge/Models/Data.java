package com.tanaka.binge.Models;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("deeplink_data")
@Expose
private List<DeeplinkDatum> deeplinkData = null;
@SerializedName("packages")
@Expose
private Packages packages;

public List<DeeplinkDatum> getDeeplinkData() {
return deeplinkData;
}

public void setDeeplinkData(List<DeeplinkDatum> deeplinkData) {
this.deeplinkData = deeplinkData;
}

public Packages getPackages() {
return packages;
}

public void setPackages(Packages packages) {
this.packages = packages;
}

}

