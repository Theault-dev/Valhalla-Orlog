package com.game.orlog.controller;

import com.game.orlog.utils.LocalisationSystem;

import javafx.fxml.FXML;
import javafx.scene.Parent;

/**
 * Super controller for a popup window.
 * Can be inherited by a popup class.
 * 
 * @author Theault & Titouan
 *
 */
public class PopupController {
	@FXML
	protected Parent view;

	/**
	 * Initialize the view by calling the method to change
	 * the language from a view.
	 * 
	 * @see LocalisationSystem
	 */
	@FXML
	public void initialize() {
		LocalisationSystem.changeViewLanguage(view);
	}
}
