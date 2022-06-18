package com.game.orlog.model.enumClass;

public enum GamePhaseEnum implements GeneralEnum {
	IDLE(""), ROLL(""), DICE_SELECTION(""), FAVORS_SELECTION(""),
	RESOLVE(""), DIVINE_FAVOR("faveur_divine"), FIGHT("avant_resolution"),
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
		return GamePhaseEnum.values()[(this.ordinal() + 1) % (GamePhaseEnum.values().length)];
	}
	public GamePhaseEnum previous() {
		return GamePhaseEnum.values()[(this.ordinal() - 1) % (GamePhaseEnum.values().length)];
	}
}
