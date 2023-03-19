package com.dmnine.geocoder;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Инициализация работы приложения.
 */
@SpringBootApplication
@SuppressWarnings({"PMD", "checkstyle:hideutilityclassconstructor"})
public class GeocoderApplication {
  public static void main(final String[] args) {
    SpringApplication.run(GeocoderApplication.class, args);
  }
}
