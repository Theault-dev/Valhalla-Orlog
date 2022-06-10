package com.game.orlog.model.enumClass;

public enum OnWhatEnum implements GeneralEnum {
	NONE(""), AXE_DAMAGE("dégâts_hache"), BLOCKING("blocage"), AXE("haches"), POWER_TOKEN("jetons_pouvoir"),
	ARROW("flèches"), TAKEN_DAMAGE("dégâts_subis"), HAND("main");

	private String name;

	private OnWhatEnum(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}
