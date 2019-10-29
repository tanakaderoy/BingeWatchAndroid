package com.tanaka.binge.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JWProvider {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("technical_name")
@Expose
private String technicalName;
@SerializedName("short_name")
@Expose
private String shortName;
@SerializedName("clear_name")
@Expose
private String clearName;
@SerializedName("priority")
@Expose
private Integer priority;
@SerializedName("display_priority")
@Expose
private Integer displayPriority;
@SerializedName("monetization_types")
@Expose
private List<String> monetizationTypes = null;
@SerializedName("icon_url")
@Expose
private String iconUrl;
@SerializedName("slug")
@Expose
private String slug;
@SerializedName("data")
@Expose
private Data data;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getTechnicalName() {
return technicalName;
}

public void setTechnicalName(String technicalName) {
this.technicalName = technicalName;
}

public String getShortName() {
return shortName;
}

public void setShortName(String shortName) {
this.shortName = shortName;
}

public String getClearName() {
return clearName;
}

public void setClearName(String clearName) {
this.clearName = clearName;
}

public Integer getPriority() {
return priority;
}

public void setPriority(Integer priority) {
this.priority = priority;
}

public Integer getDisplayPriority() {
return displayPriority;
}

public void setDisplayPriority(Integer displayPriority) {
this.displayPriority = displayPriority;
}

public List<String> getMonetizationTypes() {
return monetizationTypes;
}

public void setMonetizationTypes(List<String> monetizationTypes) {
this.monetizationTypes = monetizationTypes;
}

public String getIconUrl() {
return iconUrl;
}

public void setIconUrl(String iconUrl) {
this.iconUrl = iconUrl;
}

public String getSlug() {
return slug;
}

public void setSlug(String slug) {
this.slug = slug;
}

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

}
