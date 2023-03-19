package com.dmnine.geocoder.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Jpa config.
 * Работа с репозиторием.
 */

@EnableJpaRepositories(basePackages = "com.dmnine.geocoder")
public class JpaConfig {
}
