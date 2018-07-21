package com.quandoanh.priceformatter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.quandoanh.priceformatter.service.formatter.AvailableFormatterConfig;
import com.quandoanh.priceformatter.service.formatter.FormatterConfig;
import com.quandoanh.priceformatter.service.formatter.PriceFormatter;
import com.quandoanh.priceformatter.service.formatter.PriceFormatterImpl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class AppConfig {
    @Autowired
    private AvailableFormatterConfig availableFormatterConfig;

    @Bean
    public Map<String, PriceFormatter> decimalFormatter() {
        List<FormatterConfig> formatterConfigs = availableFormatterConfig.getConfigs();
        final Map<String, PriceFormatter> priceFormatterMap = new HashMap<>();
        for (FormatterConfig config : formatterConfigs) {
            priceFormatterMap.put(config.getName().toUpperCase(),new PriceFormatterImpl(config));
            System.out.println(config);
        }
        return Collections.unmodifiableMap(priceFormatterMap);
    }
}
