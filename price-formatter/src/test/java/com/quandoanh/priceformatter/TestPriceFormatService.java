package com.quandoanh.priceformatter;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.quandoanh.priceformatter.service.PriceDisplay;
import com.quandoanh.priceformatter.service.PriceFormatService;

public class TestPriceFormatService extends BaseTest {
	@Autowired
	private PriceFormatService service;

	@Test
	public void testDecimalFormat() {
		System.out.println("\ntestDecimalFormat");
		float rawPrice = 47.92f;
		PriceDisplay result = service.format(rawPrice, "decimal");
		System.out.println("Result:\t\t" + result);
		PriceDisplay expected = new PriceDisplay("4", "7.9", "200");
		System.out.println("Expected:\t" + expected);
		Assert.assertEquals("Result does not match", expected, result);
	}

	@Test
	public void testPercentageFormat() {
		System.out.println("\ntestPercentageFormat");
		float rawPrice = 0.1523f;
		PriceDisplay result = service.format(rawPrice, "percentage");
		System.out.println("Result:\t\t" + result);
		PriceDisplay expected = new PriceDisplay("15.2", "3", "00");
		System.out.println("Expected:\t" + expected);
		Assert.assertEquals("Result does not match", expected, result);
	}

	@Test
	public void testPercentageFormat2() {
		System.out.println("\ntestPercentageFormat2");
		float rawPrice = 0.16754f;
		PriceDisplay result = service.format(rawPrice, "percentage");
		System.out.println("Result:\t\t" + result);
		PriceDisplay expected = new PriceDisplay("16.7", "5", "40");
		System.out.println("Expected:\t" + expected);
		Assert.assertEquals("Result does not match", expected, result);
	}
	
	@Test
	public void testBasisPointFormat() {
		System.out.println("\ntestBasisPointFormat");
		float rawPrice = 0.0018f;
		PriceDisplay result = service.format(rawPrice, "basispoint");
		System.out.println("Result:\t\t" + result);
		PriceDisplay expected = new PriceDisplay("", "18.0", "0");
		System.out.println("Expected:\t" + expected);
//		Assert.assertEquals("Result does not match", expected, result);
	}

}
