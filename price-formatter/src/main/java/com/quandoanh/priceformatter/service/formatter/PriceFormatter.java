package com.quandoanh.priceformatter.service.formatter;

import com.quandoanh.priceformatter.service.PriceDisplay;

public interface PriceFormatter {
	PriceDisplay formatPrice(float rawPrice);
}
