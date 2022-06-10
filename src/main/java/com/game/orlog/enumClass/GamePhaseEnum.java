package com.game.orlog.enumClass;

public enum GamePhaseEnum implements GeneralEnum {
	IDLE(""), ROLL(""), DICES_SELECTION(""), DIVINE_FAVOR("faveur_divine"), FIGHT("avant_resolution"), RESOLVE(""),
	END_TURN("apres_resolution"), END_GAME("");

	private String name;

	private GamePhaseEnum(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public GamePhaseEnum next() {
		return GamePhaseEnum.values()[(this.ordinal() + 1) % (GamePhaseEnum.values().length-1)];
	}
}
