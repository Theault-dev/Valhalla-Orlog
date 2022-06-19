package com.game.orlog.model.enumClass;

/**
 * Enumeration of possible offering type
 * 
 * @author Theault & Titouan
 *
 */
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