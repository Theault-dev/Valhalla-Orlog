package com.game.orlog.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.game.orlog.ValhallaOrlogApplication;
import com.game.orlog.utils.LocalisationSystem;
import com.game.orlog.utils.PopupWindow;
import com.game.orlog.utils.LocalisationSystem.Language;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class ValhallaOrlogController {
	static ArrayList<Node> nodes = new ArrayList<Node>();
	@FXML
	private Button settings;
	@FXML
	private Button rulesButton;
	@FXML
	private GridPane view;
	@FXML
	private HomePageController viewController;

	@FXML
	public void initialize() throws IOException {
		nodes = new ArrayList<Node>();
		LocalisationSystem.addAllDescendents(view, nodes);

		setupButtonActions();
	}
	
	private void setupButtonActions() {
		for (Node node : nodes) {
			if (node.getId() != null) {
				if (node.getId().equals("newGame")) {
					((Button) node).setOnAction(event -> {
						try {
							loadNewGame(event);
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				} else if (node.getId().equals("loadGame")) {
					((Button) node).setOnAction(event -> {
						System.out.println("load game");
						switch (LocalisationSystem.language) {
						case ENGLISH:
							LocalisationSystem.language = Language.FRENCH;
							break;
						case FRENCH:
						default:
							LocalisationSystem.language = Language.ENGLISH;
							break;
						}
						LocalisationSystem.changeUILanguage();
						//TODO
					});
				} else if (node.getId().equals("quit")) {
					((Button) node).setOnAction(event -> {
						System.exit(0);
					});
				}
			}
		};

		settings.setOnMousePressed(event -> {
			PopupWindow.showPopupMessage("Settings.fxml", ValhallaOrlogApplication.getStage());
		});
		
		rulesButton.setOnMousePressed(event -> {
			PopupWindow.showPopupMessage("Rules.fxml", ValhallaOrlogApplication.getStage());
		});
	}

	public void loadNewGame(ActionEvent event) throws IOException {
		GridPane newView = FXMLLoader.load(ValhallaOrlogApplication.class.getResource("GameBoard.fxml"));
		view.getColumnConstraints().clear();
		view.getRowConstraints().clear();
		view.getChildren().setAll(newView);

		nodes = new ArrayList<Node>();
		LocalisationSystem.addAllDescendents(view, nodes);
		for (Node node : nodes) {
			if (node.getId() != null) {
				node.setOnMousePressed(e -> {
					System.out.println(node.getId());
				});
//				System.out.print(node.getId() + "\t");
//				System.out.println(node.localToScreen(node.getBoundsInLocal()));
			}
		};
	}
}
