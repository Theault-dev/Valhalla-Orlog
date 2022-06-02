package com.game.orlog.characters;

import com.game.orlog.utils.Color;

public abstract class Character {
	private String name;
	private Color color;
	
	public Character(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	public final String getName() {
		return name;
	}
	public final Color getColor() {
		return color;
	}
}
