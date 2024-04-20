package com.dmnine.geocoder.service;

import com.dmnine.geocoder.client.NominatimClient;
import com.dmnine.geocoder.model.Place;
import com.dmnine.geocoder.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис для работы с базой данных геокодера.
 * При запросе осуществляется попытка найти в базе запрошенный объект.
 * При нахождении объекта в базе по адресу или по координатам он возвращается.
 * Если такого экземпляра не будет в базе - запрос к Nominatim и кэширование объекта.
 */

@Service
public class PlaceService {
  private final PlaceRepository placeRepository;
  private final NominatimClient nominatimClient;


  @Autowired
  public PlaceService(final PlaceRepository placeRepository,
                      final NominatimClient nominatimClient) {
    this.nominatimClient = nominatimClient;
    this.placeRepository = placeRepository;
  }

  public Optional<Place> search(final String query) {
    return placeRepository.findByQuery(query)
      .or(() ->
        nominatimClient.search(query)
          .map(p -> placeRepository.save(Place.of(p, query)))
      );
  }

  public Optional<Place> reverse(final Double lat, final Double lon) {
    return placeRepository.findByLatitudeAndLongitude(lat, lon)
      .or(() -> nominatimClient.reverse(lat, lon)
          .map(p -> placeRepository.save(Place.like(p, "", lat, lon)))
      );
  }

/*  public Optional<Place> reverse(final Double lat, final Double lon) {
    return placeRepository.findByQuery(lat+"&"+lon)
      .or(() -> nominatimClient.reverse(lat, lon)
        .map(p -> placeRepository.save(Place.of(p, lat+"&"+lon)))
      );
  }*/
}
