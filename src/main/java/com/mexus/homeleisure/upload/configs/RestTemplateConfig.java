package com.mexus.homeleisure.upload.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
  @Bean
  public RestTemplate restTemplate() {

    RestTemplate restTemplate = new RestTemplate();
    ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory())
        .setReadTimeout(1000*1000);

    ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory())
        .setConnectTimeout(1000 * 1000);

    return restTemplate;
  }
}
