package com.game.orlog.enumClass;

public enum OnWhoEnum implements GeneralEnum {
	NONE(""), PLAYER("joueur"), OPPONENT("adversaire"), CHOOSE("au_choix");

	private String name;

	private OnWhoEnum(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}