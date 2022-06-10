package com.game.orlog.enumClass;

public enum OfferingEnum implements GeneralEnum {
	NONE(""), HEALTH_TOKEN("jetons_santé");

	private String name;

	private OfferingEnum(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}