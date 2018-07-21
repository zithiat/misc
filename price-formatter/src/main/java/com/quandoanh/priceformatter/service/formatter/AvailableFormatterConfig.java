package com.quandoanh.priceformatter.service.formatter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = "formatter")
@Configuration
public class AvailableFormatterConfig {

    private List<FormatterConfig> configs;

    public List<FormatterConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<FormatterConfig> configs) {
        this.configs = configs;
    }

}
