package com.game.orlog.model.items;

import com.game.orlog.model.enumClass.ActionEnum;

/**
 * A face of a die.
 * 
 * @author Theault & Titouan
 *
 */
public class Face {
	private boolean isSpecial;
	private ActionEnum face;

	public Face(boolean isSpecial, ActionEnum face) {
		super();
		this.isSpecial = isSpecial;
		this.face = face;
	}

	public boolean getIsSpecial() {
		return isSpecial;
	}

	public ActionEnum getFace() {
		return face;
	}

	@Override
	public String toString() {
		return "Face [isSpecial=" + isSpecial + ", face=" + face + "]";
	}
}
