package com.game.orlog.controller;

import com.game.orlog.ValhallaOrlogApplication;
import com.game.orlog.utils.LocalisationSystem;
import com.game.orlog.utils.PopupWindow;
import com.game.orlog.utils.LocalisationSystem.Language;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;

public class SettingsController extends PopupController{
	@FXML
	private ComboBox<String> settings_languages_list;
	
	@FXML
	public void initialize() {
		settings_languages_list.getItems().add("English");
		settings_languages_list.getItems().add("Français");
		settings_languages_list.setValue("Anglais");
		
		switch (LocalisationSystem.language) {
		case FRENCH: {
			settings_languages_list.getSelectionModel().select(1);
			break;
		}
		case ENGLISH:
		default:
			settings_languages_list.getSelectionModel().select(0);
			break;
		}
		super.initialize();
		
		settings_languages_list.setOnAction((event) -> {
		    int selectedIndex = settings_languages_list.getSelectionModel().getSelectedIndex();
		    
			switch (selectedIndex) {
			case 1:
				LocalisationSystem.language = Language.FRENCH;
				break;
			case 0:
			default:
				LocalisationSystem.language = Language.ENGLISH;
				break;
			}
			LocalisationSystem.changeUILanguage();
			LocalisationSystem.changeViewLanguage(view);
		});
	}
}
