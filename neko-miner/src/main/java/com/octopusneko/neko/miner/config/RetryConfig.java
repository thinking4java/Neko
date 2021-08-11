package com.octopusneko.neko.miner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpStatusCodeException;

@Configuration
public class RetryConfig {

    @Value("${retry.maxAttempts}")
    private int maxAttempts;

    private final SimpleRetryPolicy simpleRetryPolicy;
    private final NeverRetryPolicy neverRetryPolicy;

    public RetryConfig() {
        simpleRetryPolicy = new SimpleRetryPolicy(maxAttempts);
        neverRetryPolicy = new NeverRetryPolicy();
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        ExceptionClassifierRetryPolicy policy = new ExceptionClassifierRetryPolicy();
        policy.setExceptionClassifier(configureStatusCodeBasedRetryPolicy());
        retryTemplate.setRetryPolicy(policy);
        return retryTemplate;
    }

    private Classifier<Throwable, RetryPolicy> configureStatusCodeBasedRetryPolicy() {
        return throwable -> {
            if (throwable instanceof HttpStatusCodeException) {
                HttpStatusCodeException exception = (HttpStatusCodeException) throwable;
                return getRetryPolicyForStatus(exception.getStatusCode());
            }
            return simpleRetryPolicy;
        };
    }

    private RetryPolicy getRetryPolicyForStatus(HttpStatus httpStatus) {
        switch (httpStatus) {
            case BAD_GATEWAY:
            case SERVICE_UNAVAILABLE:
            case INTERNAL_SERVER_ERROR:
            case GATEWAY_TIMEOUT:
                return simpleRetryPolicy;
            default:
                return neverRetryPolicy;
        }
    }
}
