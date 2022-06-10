package com.game.orlog.entity;

import java.util.ArrayList;

import com.game.orlog.items.Cup;
import com.game.orlog.items.Die;

public class Player {
	private byte healthPoint;
	private ArrayList<Divinity> divinities;
	private Cup cup;
	private byte remainingRolls;
	private byte gold;
	
	public static byte MAX_HEALTH_POINT = 15;
	public static byte MAX_ROLLS = 3;
	
	public Player(ArrayList<Divinity> divinities) {
		super();
		this.divinities = divinities;
		healthPoint = MAX_HEALTH_POINT;
		cup = new Cup();
		remainingRolls = MAX_ROLLS;
		gold = 0;
	}
	
	public ArrayList<Die> getDices() {
		return cup.getDices();
	}
	public void setDices(ArrayList<Die> newDices) {
		cup.setDices(newDices);
	}
	public ArrayList<Die> getDicesToKeep() {
		return cup.getDicesToKeep();
	}
	public void setDicesToKeep(ArrayList<Die> dicesToKeep) {
		cup.setDicesToKeep(dicesToKeep);
	}
	public final byte getHealthPoint() {
		return healthPoint;
	}
	public final ArrayList<Divinity> getDivinities() {
		return divinities;
	}
	public final Cup getCup() {
		return cup;
	}
	public final byte getRemainingRolls() {
		return remainingRolls;
	}
	public void incrementRemainingThrows() {
		remainingRolls++;
	}
	public final byte getGold() {
		return gold;
	}
}
