package com.dmnine.geocoder.client;

import com.dmnine.geocoder.dto.NominatimPlace;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name="nominatim", url="https://nominatim.openstreetmap.org")
public interface NominatimClient {
  @RequestMapping(method = RequestMethod.GET, value = "/search", consumes = "application/json")
  List<NominatimPlace> search(@RequestParam(value = "q") String query,
                                   @RequestParam(value = "format") String format);

  @RequestMapping(method = RequestMethod.GET, value = "/reverse", consumes = "application/json")
  NominatimPlace reverse(@RequestParam(value = "lon") String lon,
                                   @RequestParam(value = "lat") String lat);
}
