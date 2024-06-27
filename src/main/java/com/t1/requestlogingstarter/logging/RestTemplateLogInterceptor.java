package com.t1.requestlogingstarter.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestTemplateLogInterceptor implements ClientHttpRequestInterceptor {
    @Value("${logging.internal.request:false}")
    private boolean hasLoggingExternalRequest;
    @Value("${logging.internal.response:false}")
    private boolean hasLoggingExternalResponse;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (hasLoggingExternalRequest) {
            log.info("Request url: {} headers: {}  body: {}", request.getURI(), request.getHeaders().toSingleValueMap(), new String(body, StandardCharsets.UTF_8));
        }
        ClientHttpResponse response = execution.execute(request, body);
        if (hasLoggingExternalResponse) {
            InputStreamReader isr = new InputStreamReader(
                    response.getBody(), StandardCharsets.UTF_8);
            String bodyString = new BufferedReader(isr).lines()
                    .collect(Collectors.joining("\n"));
            log.info("Response of request url: {} headers: {} body: {} ", request.getURI(), response.getHeaders(), bodyString);
        }
        return response;
    }
}
