package com.game.orlog.model.enumClass;

public enum AffectEnum implements GeneralEnum {
	HEALTH_TOKEN_BADLY_WRITTEN("jetons_vie"), HEALTH_TOKEN("jetons_santé"), POWER_TOKEN("jetons_pouvoir"),
	HELMET("casques"), SHIELD("boucliers"), AXE("haches"), DIE("dés"), DIVINE_FAVOR("faveur_divine"), ARROW("flèches");

	private String name;

	private AffectEnum(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
};
