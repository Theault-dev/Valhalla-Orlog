package com.game.orlog.model.items;

import java.util.Arrays;

/**
 * A Die with different faces
 * 
 * @author Theault & Titouan
 *
 */
public class Die {
	private Face[] faces;
	private Face visibleFace;
	
	/**
	 * The number of faces of the die
	 */
	public final static byte numberOfFaces = 6;

	public Die(Face[] faces) {
		this.faces = faces;
		roll();
	}

	/**
	 * @return The visible face of the die.
	 */
	public Face getVisibleFace() {
		return visibleFace;
	}

	/**
	 * Roll the die to have a new visible face among the available faces.
	 */
	public void roll() {
		visibleFace = faces[(int) (Math.random() * (numberOfFaces))];
	}

	@Override
	public String toString() {
		return "Die [faces=" + Arrays.toString(faces) + "]";
	}
}
