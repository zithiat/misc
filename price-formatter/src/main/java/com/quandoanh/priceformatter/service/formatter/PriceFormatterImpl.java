package com.quandoanh.priceformatter.service.formatter;

import com.quandoanh.priceformatter.service.PriceDisplay;

public class PriceFormatterImpl implements PriceFormatter {

	private final int dpl;
	private final int fpl;
	private final int scale;
	private final int multiplicator;

	public PriceFormatterImpl(FormatterConfig config) {
		this.dpl = config.getDpl();
		this.fpl = config.getFpl();
		this.scale = config.getScale();
		this.multiplicator = config.getMultiplicator();
	}

	/**
	 * Based on the samples, the logic will be:
	 * - The raw price will be multiplied with a multiplicator.
	 * - The multiplicator is one of the following options:
	 * 		+ Decimal: 		rawPrice * 1
	 * 		+ Percentage:	rawPrice * 100
	 * 		+ Per Mile:		rawPrice * 1000
	 * 		+ Basis point:	rawPrice * 10000
	 * 		This multiplicator will be defined via configuration of each example
	 * System.out.println() is only to print the value for manual validations.
	 */
	@Override
	public PriceDisplay formatPrice(float rawPrice) {
		final PriceDisplay priceDisplay = new PriceDisplay();
		final float displayPrice = rawPrice * multiplicator;
//		System.out.println("displayPrice:" + displayPrice);
		
		final String displayPriceStr = String.format("%." + scale + "f", displayPrice);
//		System.out.println("displayPriceStr:" + displayPriceStr);
		
		final String fp = displayPriceStr.substring(displayPriceStr.length() - fpl);
//		System.out.println("fp:" + fp);

		// Remember to move one more to left for the .
		String dp = displayPriceStr.substring(displayPriceStr.length() - (fpl + dpl), displayPriceStr.length() - fpl);
		String bf = displayPriceStr.substring(0, displayPriceStr.length() - (fpl + dpl));
		if (dp.contains(".")) {
			dp = displayPriceStr.substring(displayPriceStr.length() - (fpl + dpl + 1), displayPriceStr.length() - fpl);
			bf = displayPriceStr.substring(0, displayPriceStr.length() - (fpl + dpl + 1));
		}
//		System.out.println("dp:" + dp);
//		System.out.println("bf:" + bf);
		
		priceDisplay.setBigFigure(bf);
		priceDisplay.setDealingPrice(dp);
		priceDisplay.setFractionalPips(fp);
		
		// To display the trailing zeros being truncated
		String result = "BF:\t" + priceDisplay.getBigFigure() + "\nDP:\t" + priceDisplay.getDealingPrice() + "\nFP:\t" + priceDisplay.getFractionalPips().replaceAll("0+$", "");
		System.out.println("Level up display with the trailing zeros truncation");
		System.out.println(result);
		
		return priceDisplay;
	}
	
}
