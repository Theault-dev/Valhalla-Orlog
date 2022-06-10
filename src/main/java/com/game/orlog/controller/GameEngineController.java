package com.game.orlog.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class GameEngineController {
	private BoardController boardController;
	@FXML
	private Button button;

	public GameEngineController() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean handleEvent(ActionEvent e) {
		System.out.println("Hello there..");
		return false;
	}
}
