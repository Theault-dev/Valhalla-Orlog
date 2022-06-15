package com.game.orlog.model.items;

import java.util.ArrayList;

import com.game.orlog.utils.Utils;

/**
 * A cup with dice.
 * 
 * @author Theault & Titouan
 *
 */
public class Cup {
	private ArrayList<Die> dice;
	private ArrayList<Die> diceToKeep;
	public final static byte numberOfdice = 6;

	/**
	 * Create a cup with hardcoded dice.
	 */
	public Cup() {
		dice = new ArrayList<Die>();
		diceToKeep = new ArrayList<Die>();
		for (int i = 0; i < numberOfdice; i++) {
			diceToKeep.add(null);
		}

		dice.add(new Die(
				new Face[] { Utils.HAND_SPE, Utils.HELMET, Utils.ARROW_SPE, Utils.SHIELD, Utils.AXE, Utils.AXE }));
		dice.add(new Die(
				new Face[] { Utils.HELMET, Utils.HAND_SPE, Utils.ARROW, Utils.SHIELD_SPE, Utils.AXE, Utils.AXE }));
		dice.add(new Die(
				new Face[] { Utils.SHIELD, Utils.HELMET_SPE, Utils.HAND, Utils.ARROW_SPE, Utils.AXE, Utils.AXE }));
		dice.add(new Die(
				new Face[] { Utils.AXE, Utils.HELMET_SPE, Utils.HAND_SPE, Utils.SHIELD, Utils.AXE, Utils.ARROW }));
		dice.add(new Die(
				new Face[] { Utils.ARROW_SPE, Utils.HELMET, Utils.HAND, Utils.SHIELD_SPE, Utils.AXE, Utils.AXE }));
		dice.add(new Die(
				new Face[] { Utils.HELMET_SPE, Utils.ARROW, Utils.HAND, Utils.SHIELD_SPE, Utils.AXE, Utils.AXE }));
	}

	public final ArrayList<Die> getDice() {
		return dice;
	}

	public final void setDice(ArrayList<Die> dice) {
		this.dice = dice;
	}

	public final ArrayList<Die> getDiceToKeep() {
		return diceToKeep;
	}

	public final void setDiceToKeep(ArrayList<Die> diceToKeep) {
		this.diceToKeep = diceToKeep;
	}

	@Override
	public String toString() {
		return "Cup [dice=" + dice + ", diceToKeep=" + diceToKeep + "]";
	}

}
