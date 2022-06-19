package com.game.orlog.model.entity;

import java.util.ArrayList;

import com.game.orlog.model.items.Cup;
import com.game.orlog.model.items.Die;

/**
 * Model of a Player
 * 
 * @author Theault & Titouan
 *
 */
public class Player {
	private String name;
	private byte healthPoint;
	private ArrayList<Divinity> divinities;
	private Cup cup;
	private byte remainingRolls;
	private byte gold;
	
	public static byte MAX_HEALTH_POINT = 15;
	public static byte MAX_ROLLS = 3;
	
	public Player(String name, ArrayList<Divinity> divinities) {
		super();
		this.name = name;
		this.divinities = divinities;
		healthPoint = MAX_HEALTH_POINT;
		cup = new Cup();
		remainingRolls = 0;
		gold = 0;
	}
	public void rollDice() {
		for (Die die : getDice()) {
			if (die != null) {
				die.roll();
			}
		}
	}
	public void resetAllDiceList() {
		ArrayList<Die> tmp = getDiceToKeep();
		setDiceToKeep(getDice());
		setDice(tmp);
	}
	
	public String getName() {
		return name;
	}
	public ArrayList<Die> getDice() {
		return cup.getDice();
	}
	public void setDice(ArrayList<Die> newDice) {
		cup.setDice(newDice);
	}
	public ArrayList<Die> getDiceToKeep() {
		return cup.getDiceToKeep();
	}
	public void setDiceToKeep(ArrayList<Die> DiceToKeep) {
		cup.setDiceToKeep(DiceToKeep);
	}
	public final byte getHealthPoint() {
		return healthPoint;
	}
	public final void deduceHealthPoint(byte healthPoint) {
		if (this.healthPoint - healthPoint < 0) {
			this.healthPoint = 0;
			return;
		}
		this.healthPoint -= healthPoint;
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
	public void resetRemainingThrows() {
		remainingRolls = 0;
	}
	public final byte getGold() {
		return gold;
	}
	public void addGold(byte gold) {
		this.gold += gold;
	}
	public void removeGold(byte gold) {
		this.gold -= gold;
	}
	public void incrementGold() {
		gold++;
	}
}
