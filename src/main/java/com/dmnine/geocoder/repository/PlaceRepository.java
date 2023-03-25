package com.dmnine.geocoder.repository;

import com.dmnine.geocoder.model.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий геокодера.
 * Работа с базой геокодера.
 */
@Repository
public interface PlaceRepository extends CrudRepository<Place, Integer> {
  Optional<Place> findByAddress(String place);
  Optional<Place> findByLatitudeAndLongitude(Double lat, Double lon);
}
