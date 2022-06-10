package com.game.orlog.items;

import java.util.Arrays;

public class Die {
	private Face[] faces;
	private Face visibleFace;
	
	public final static byte numberOfFaces = 6;

	public Die(Face[] faces) {
		this.faces = faces;
		roll();
	}

	public Face getVisibleFace() {
		return visibleFace;
	}

	public void roll() {
		visibleFace = faces[(int) (Math.random() * (numberOfFaces))];
	}

	@Override
	public String toString() {
		return "Die [faces=" + Arrays.toString(faces) + "]";
	}
}
