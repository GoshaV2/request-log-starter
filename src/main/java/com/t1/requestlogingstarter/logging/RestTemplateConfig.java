package com.t1.requestlogingstarter.logging;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {
    private final RestTemplateLogInterceptor restTemplateLogInterceptor;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        ClientHttpRequestFactory factory
                = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        restTemplateBuilder.requestFactory(() -> factory);
        RestTemplate restTemplate = restTemplateBuilder.build();
        configureInterceptors(restTemplate);
        return restTemplate;
    }

    private void configureInterceptors(RestTemplate restTemplate) {
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(restTemplateLogInterceptor);
        restTemplate.setInterceptors(interceptors);
    }
}
