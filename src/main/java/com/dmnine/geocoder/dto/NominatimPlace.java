package com.dmnine.geocoder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Адрес.
 *
 * @param displayName название улицы
 * @param type тип постройки
 * @param lat широта
 * @param lon долголта
 */

public record NominatimPlace(
  @JsonProperty("display_name") String displayName,
  @JsonProperty("type") String type,
  @JsonProperty("lat") Double lat,
  @JsonProperty("lon") Double lon
) {
  public NominatimPlace() {
    this("", "", 0.0, 0.0);
  }
}
