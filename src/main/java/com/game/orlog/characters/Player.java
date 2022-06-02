package com.game.orlog.characters;

import com.game.orlog.utils.Color;
import com.game.orlog.utils.Cup;
import com.game.orlog.utils.Die;

public class Player extends Character {
	private byte healthPoint;
	private Divinities divinities;
	private Cup cup;
	private Die[] dices;

	private Player(String name, Color color) {
		super(name, color);
		this.healthPoint = 15;
		this.divinities = new Divinities();
		this.dices = new Die[6];
	}
	
	public Player(String name, Color color, byte healthPoint, Cup cup) {
		this(name, color);
		this.healthPoint = healthPoint;
		this.cup = cup;
	}
}
