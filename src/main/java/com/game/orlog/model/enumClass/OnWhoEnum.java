package com.game.orlog.model.enumClass;

/**
 * Enumeration of possible target.
 * 
 * @author Theault & Titouan
 *
 */
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