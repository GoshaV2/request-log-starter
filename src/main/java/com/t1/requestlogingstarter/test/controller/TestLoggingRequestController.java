package com.t1.requestlogingstarter.test.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RequestMapping("/test")
@RestController
@Slf4j
@RequiredArgsConstructor
public class TestLoggingRequestController {
    private final RestTemplate restTemplate;

    @GetMapping
    public String testSendRequestForPost() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{\"test\":\"test\"}", headers);

        restTemplate.postForObject("http://localhost:8080/test", entity, Object.class);
        return "response";
    }

    @PostMapping
    public Object testPost(@RequestBody Object object) {
        return object;
    }
}

