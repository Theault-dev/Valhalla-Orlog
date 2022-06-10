package com.game.orlog.items;

import java.util.ArrayList;

import com.game.orlog.utils.Utils;

public class Cup {
	private ArrayList<Die> dices;
	private ArrayList<Die> dicesToKeep;
	public final static byte numberOfDices = 6;

	public Cup() {
		dices = new ArrayList<Die>();
		dicesToKeep = new ArrayList<Die>();

		dices.add(new Die(
				new Face[] { Utils.HAND_SPE, Utils.HELMET, Utils.ARROW_SPE, Utils.SHIELD, Utils.AXE, Utils.AXE }));
		dices.add(new Die(
				new Face[] { Utils.HELMET, Utils.HAND_SPE, Utils.ARROW, Utils.SHIELD_SPE, Utils.AXE, Utils.AXE }));
		dices.add(new Die(
				new Face[] { Utils.SHIELD, Utils.HELMET_SPE, Utils.HAND, Utils.ARROW_SPE, Utils.AXE, Utils.AXE }));
		dices.add(new Die(
				new Face[] { Utils.AXE, Utils.HELMET_SPE, Utils.HAND_SPE, Utils.SHIELD, Utils.AXE, Utils.ARROW }));
		dices.add(new Die(
				new Face[] { Utils.ARROW_SPE, Utils.HELMET, Utils.HAND, Utils.SHIELD_SPE, Utils.AXE, Utils.AXE }));
		dices.add(new Die(
				new Face[] { Utils.HELMET_SPE, Utils.ARROW, Utils.HAND, Utils.SHIELD_SPE, Utils.AXE, Utils.AXE }));
	}

	public final ArrayList<Die> getDices() {
		return dices;
	}

	public final void setDices(ArrayList<Die> dices) {
		this.dices = dices;
	}

	public final ArrayList<Die> getDicesToKeep() {
		return dicesToKeep;
	}

	public final void setDicesToKeep(ArrayList<Die> dicesToKeep) {
		this.dicesToKeep = dicesToKeep;
	}

	@Override
	public String toString() {
		return "Cup [dices=" + dices + ", dicesToKeep=" + dicesToKeep + "]";
	}

}
