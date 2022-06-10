package com.game.orlog.controller;

import com.game.orlog.model.Board;
import com.game.orlog.model.entity.Player;

public class BoardController {
	private Board board;
	private Player currentTurnSPlayer;

//	public BoardController(Board board) {
//		this.board = board;
//	}
	
	public final void setCurrentTurnSPlayer(Player currentTurnSPlayer) {
		this.currentTurnSPlayer = currentTurnSPlayer;
	}

	public void update() {
		//TODO
	}
	
	public boolean onDieClick() {
		//TODO
		return false;
	}

}
