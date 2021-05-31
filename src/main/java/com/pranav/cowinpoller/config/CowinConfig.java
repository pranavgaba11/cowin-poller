package com.pranav.cowinpoller.config;

import java.util.Scanner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CowinConfig {

  @Bean(name = "restTemplate")
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }
  
  @Bean
  public Scanner scanner() {
    return new Scanner(System.in);
  }

}
