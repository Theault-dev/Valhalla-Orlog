package com.game.orlog.model.enumClass;

public enum GamePhaseEnum implements GeneralEnum {
	IDLE(""), ROLL(""), DICE_SELECTION(""), FAVORS_SELECTION(""),
	RESOLVE(""), FIGHT("avant_resolution"), DIVINE_FAVOR("faveur_divine"),
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
