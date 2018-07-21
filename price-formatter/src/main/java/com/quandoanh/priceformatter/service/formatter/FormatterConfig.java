package com.quandoanh.priceformatter.service.formatter;

public class FormatterConfig {
	private int dpl;
	private int fpl;
	private int scale;
	private int multiplicator;
	private String name;

	public int getDpl() {
		return dpl;
	}

	public void setDpl(int dpl) {
		this.dpl = dpl;
	}

	public int getFpl() {
		return fpl;
	}

	public void setFpl(int fpl) {
		this.fpl = fpl;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getMultiplicator() {
		return multiplicator;
	}

	public void setMultiplicator(int multiplicator) {
		this.multiplicator = multiplicator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "FormatterConfig{" + "dpl=" + dpl + ", fpl=" + fpl + ", scale=" + scale + ", multiplicator="
				+ multiplicator + ", name='" + name + '\'' + '}';
	}
}
