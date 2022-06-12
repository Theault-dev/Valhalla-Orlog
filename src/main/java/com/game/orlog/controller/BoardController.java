package com.game.orlog.controller;

import com.game.orlog.model.Board;
import com.game.orlog.model.entity.Player;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class BoardController {
	private Board board;
	private Player currentTurnSPlayer;
	
	public final void setCurrentTurnSPlayer(Player currentTurnSPlayer) {
		this.currentTurnSPlayer = currentTurnSPlayer;
	}
	@FXML
	public void initialize() {
		System.out.println("BoardController is init");
	}

	public void update() {
		//TODO
	}
	
	public boolean onDieClick() {
		//TODO
		return false;
	}

}
