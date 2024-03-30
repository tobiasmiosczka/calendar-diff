package com.github.tobiasmiosczka.calendardiff.service;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("services.fetch-service")
public class FetchServiceConfig {

    private int fetchSize;

    public int getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
    }
}
