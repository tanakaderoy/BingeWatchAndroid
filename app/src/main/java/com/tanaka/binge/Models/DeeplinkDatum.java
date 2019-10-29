package com.tanaka.binge.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeeplinkDatum {

@SerializedName("scheme")
@Expose
private String scheme;
@SerializedName("packages")
@Expose
private List<String> packages = null;
@SerializedName("platforms")
@Expose
private List<String> platforms = null;
@SerializedName("path_template")
@Expose
private String pathTemplate;

public String getScheme() {
return scheme;
}

public void setScheme(String scheme) {
this.scheme = scheme;
}

public List<String> getPackages() {
return packages;
}

public void setPackages(List<String> packages) {
this.packages = packages;
}

public List<String> getPlatforms() {
return platforms;
}

public void setPlatforms(List<String> platforms) {
this.platforms = platforms;
}

public String getPathTemplate() {
return pathTemplate;
}

public void setPathTemplate(String pathTemplate) {
this.pathTemplate = pathTemplate;
}

}
