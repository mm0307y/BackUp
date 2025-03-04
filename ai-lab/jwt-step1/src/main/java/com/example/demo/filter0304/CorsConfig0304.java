package com.example.demo.filter0304;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig0304 {
  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.addAllowedHeader("*"); // 모든 header에 응답을 허용
    config.addAllowedOrigin("http://localhost:3000/");// ip에 응답을 허용한다.
    config.addAllowedMethod("*");// 모든 POST, GET, PUT, DELETE Rest API 허락할께
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}
