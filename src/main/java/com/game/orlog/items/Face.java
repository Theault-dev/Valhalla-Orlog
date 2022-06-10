package com.game.orlog.items;

import com.game.orlog.enumClass.ActionEnum;

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
