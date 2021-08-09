package com.octopusneko.neko.miner.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateConfig.class);

    @Bean
    public CustomRestTemplateCustomizer customRestTemplateCustomizer() {
        return new CustomRestTemplateCustomizer();
    }

    @Bean
    @DependsOn(value = {"customRestTemplateCustomizer"})
    public RestTemplateBuilder restTemplateBuilder(RestConfig restConfig) {
        return new RestTemplateBuilder(customRestTemplateCustomizer())
                .setConnectTimeout(Duration.ofMillis(restConfig.getConnectTimeout()))
                .setReadTimeout(Duration.ofMillis(restConfig.getReadTimeout()));
    }

    @Bean
    @DependsOn(value = "restTemplateBuilder")
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    static class CustomRestTemplateCustomizer implements RestTemplateCustomizer {
        @Override
        public void customize(RestTemplate restTemplate) {
            restTemplate.getInterceptors().add(new CustomClientHttpRequestInterceptor());
        }
    }

    static class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(
                HttpRequest request, byte[] body,
                ClientHttpRequestExecution execution) throws IOException {

            logRequestDetails(request);
            long start = System.nanoTime();
            ClientHttpResponse response = execution.execute(request, body);
            logger.debug("Request spent time: {}", (System.nanoTime() - start) / 1e+9);
            logResponseDetails(response);
            return response;
        }

        private void logRequestDetails(HttpRequest request) {
            logger.debug("Request URI: {}", request.getURI());
            logger.trace("Request Headers: {}", request.getHeaders());
            logger.trace("Request Method: {}", request.getMethod());
        }

        private void logResponseDetails(ClientHttpResponse response) throws IOException {
            logger.trace("Response Headers: {}", response.getHeaders());
            logger.debug("Response Status: {}", response.getStatusCode());
        }
    }
}