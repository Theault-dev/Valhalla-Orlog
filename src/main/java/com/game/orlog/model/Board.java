package com.game.orlog.model;

import com.game.orlog.model.entity.Player;

/**
 * Model of a board.
 * 
 * @author Theault & Titouan
 *
 */
public class Board {
	private Player p1;
	private Player p2;
	private boolean isP1Turn;
	
	public Board(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	public final Player getP1() {
		return p1;
	}
	public final Player getP2() {
		return p2;
	}
	public final boolean isP1Turn() {
		return isP1Turn;
	}
	public void setIsP1Turn(boolean isP1Turn) {
		this.isP1Turn = isP1Turn;
	}
}
