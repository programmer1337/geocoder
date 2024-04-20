package com.dmnine.geocoder.model;

import com.dmnine.geocoder.dto.NominatimPlace;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Сущность место.
 * Содержит информацию об объекте.
 */

@Entity
public class Place {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String address;
  private Double latitude;
  private Double longitude;
  private String query;

  public String getQuery() {
    return query;
  }

  public void setQuery(final String query) {
    this.query = query;
  }

  public Integer getId() {
    return id;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(final String address) {
    this.address = address;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(final Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(final Double longitude) {
    this.longitude = longitude;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Place place = (Place) o;
    return Objects.equals(id, place.id) &&
      Objects.equals(address, place.address) &&
      Objects.equals(latitude, place.latitude) &&
      Objects.equals(longitude, place.longitude) &&
      Objects.equals(query, place.query);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, address, latitude, longitude, query);
  }

  @Override
  public String toString() {
    return "Place{" +
      "id=" + id +
      ", address='" + address + '\'' +
      ", latitude=" + latitude +
      ", longitude=" + longitude +
      ", query=" + query +
      '}';
  }

  public static Place of(final NominatimPlace place, final String query) {
    final Place result = new Place();
    result.setAddress(place.displayName());
    result.setLatitude(place.lat());
    result.setLongitude(place.lon());
    result.setQuery(query);

    return result;
  }

  public static Place like(final NominatimPlace place, final String query, final Double lat, final Double lon) {
    final Place result = new Place();
    result.setAddress(place.displayName());
    result.setLatitude(lat);
    result.setLongitude(lon);
    result.setQuery(query);

    return result;
  }
}


