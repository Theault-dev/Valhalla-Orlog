package com.game.orlog.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * Controller of the HomePage.FXML
 * 
 * @author Theault & Titouan
 *
 */
public class HomePageController {
	@FXML
	private Button newGame;
	@FXML
	private Button loadGame;
	@FXML
	private Button quit;
	@FXML
	private Button settings;
	@FXML
	private Label gameTitle;
	@FXML
	private Label gameOrigin;
	@FXML
	private Parent view;

	public void handlerOnClickButtonEvent(ActionEvent e) {
		System.out.println("Stop clicking on this button !!");
		Object element = e.getSource();
		if (element instanceof Button) {
			System.out.println( "ValhallaOrlogController : " + ((Button)element).getId() );
		}
	}
	public void HandlerClickMouseEvent(MouseEvent e) {
		System.out.println("You used your mouse ?");
	}
	public int getValue() {
		return 3;
	}
	@FXML
	public void initialize() {
		System.out.println("ValhallaOrlogController is init");
	}
}
