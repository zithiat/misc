package com.quandoanh.priceformatter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quandoanh.priceformatter.service.formatter.PriceFormatter;

import java.util.Map;

@Service
public class PriceFormatService {
    @Autowired
    private Map<String, PriceFormatter> formatterMap;

    public PriceDisplay format(float rawPrice, String type) {
        final PriceFormatter formatter = formatterMap.get(type.toUpperCase());
        if (null == formatter) {
            throw new RuntimeException("Formatter not found");
        }
        return formatter.formatPrice(rawPrice);
    }
}
