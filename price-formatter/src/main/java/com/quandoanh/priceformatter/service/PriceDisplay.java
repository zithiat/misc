package com.quandoanh.priceformatter.service;

import java.util.Objects;

public class PriceDisplay {
    private String bigFigure;
    private String dealingPrice;
    private String fractionalPips;

    public PriceDisplay() {
    }

    public PriceDisplay(String bigFigure, String dealingPrice, String fractionalPips) {
        this.bigFigure = bigFigure;
        this.dealingPrice = dealingPrice;
        this.fractionalPips = fractionalPips;
    }

    public String getBigFigure() {
        return bigFigure;
    }

    public void setBigFigure(String bigFigure) {
        this.bigFigure = bigFigure;
    }

    public String getDealingPrice() {
        return dealingPrice;
    }

    public void setDealingPrice(String dealingPrice) {
        this.dealingPrice = dealingPrice;
    }

    public String getFractionalPips() {
        return fractionalPips;
    }

    public void setFractionalPips(String fractionalPips) {
        this.fractionalPips = fractionalPips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceDisplay that = (PriceDisplay) o;
        return Objects.equals(bigFigure, that.bigFigure) &&
                Objects.equals(dealingPrice, that.dealingPrice) &&
                Objects.equals(fractionalPips, that.fractionalPips);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bigFigure, dealingPrice, fractionalPips);
    }

    @Override
    public String toString() {
        return "PriceDisplay{" +
                "bigFigure='" + bigFigure + '\'' +
                ", dealingPrice='" + dealingPrice + '\'' +
                ", fractionalPips='" + fractionalPips + '\'' +
                '}';
    }
}
