package com.dmnine.geocoder.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class NominatimPlace {
  @JsonProperty("display_name")
  String displayName;
  @JsonProperty("type")
  String type;
  @JsonProperty("lat")
  Double lat;
  @JsonProperty("lot")
  Double lon;

  public Double getLat() {
    return lat;
  }

  public void setLat(Double lat) {
    this.lat = lat;
  }

  public Double getLon() {
    return lon;
  }

  public void setLon(Double lon) {
    this.lon = lon;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NominatimPlace place = (NominatimPlace) o;
    return Objects.equals(displayName, place.displayName) && Objects.equals(type, place.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(displayName, type);
  }

  @Override
  public String toString() {
    return "Place{" +
      ", display_name='" + displayName + '\'' +
      ", type='" + type + '\'' +
      '}';
  }
}
