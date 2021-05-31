package com.pranav.cowinpoller.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.val;

@Service
public class RestService {

  @Autowired
  @Qualifier("restTemplate")
  RestTemplate restTemplate;

  public String makePostRequest(String url, String jsonString) {
    val headers = composeHeaders();
    HttpEntity<String> request = new HttpEntity<String>(jsonString, headers);
    return restTemplate.postForObject(url, request, String.class);
  }

  public ResponseEntity<String> makeGetRequest(String url) {
    val headers = composeHeaders();
    HttpEntity<String> entity = new HttpEntity<>(headers);
    return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
  }

  private HttpHeaders composeHeaders() {
    List<MediaType> mediaType = new ArrayList<>();
    mediaType.add(MediaType.APPLICATION_JSON);
    val headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(mediaType);
    headers.add(HttpHeaders.USER_AGENT,
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
    return headers;
  }
}
