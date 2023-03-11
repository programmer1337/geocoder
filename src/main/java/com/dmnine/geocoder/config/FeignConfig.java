package com.dmnine.geocoder.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.dmnine.geocoder")
public class FeignConfig {
}
