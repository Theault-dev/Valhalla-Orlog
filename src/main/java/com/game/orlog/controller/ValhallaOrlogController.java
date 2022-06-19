package com.game.orlog.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.game.orlog.ValhallaOrlogApplication;
import com.game.orlog.model.entity.Divinity;
import com.game.orlog.model.entity.Player;
import com.game.orlog.utils.CSVLoader;
import com.game.orlog.utils.LocalisationSystem;
import com.game.orlog.utils.PopupWindow;
import com.game.orlog.utils.Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/**
 * Controller of the Valhalla Application.
 * 
 * @author Theault & Titouan
 *
 */
public class ValhallaOrlogController {
	private Scene scene;
	
	static ArrayList<Node> nodes = new ArrayList<Node>();
	@FXML
	private Button settings;
	@FXML
	private Button rulesButton;
	@FXML
	private GridPane view;
	
	private GameplayController gameplayController;

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
							startNewGame(event);
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				} else if (node.getId().equals("loadGame")) {
					((Button) node).setOnAction(event -> {
						System.out.println("load game");
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
			PopupWindow.showPopupMessage("Settings.fxml");
		});
		
		rulesButton.setOnMousePressed(event -> {
			PopupWindow.showPopupMessage("Rules.fxml");
		});
	}

	public void startNewGame(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(ValhallaOrlogApplication.class.getResource("GameBoard.fxml"));
		//TODO
		view = loader.load();
		scene = ValhallaOrlogApplication.getStage().getScene();
		//replace the included view by the new view
		((GridPane)scene.getRoot()).getChildren().set(0, view);
		ValhallaOrlogApplication.getStage().setScene(scene);
		scene.getRoot().getStylesheets().add(
				CSVLoader.class
				.getResource("/com/game/orlog/css/styles.css")
				.toExternalForm());

		//load the controller. Must be done AFTER the setScene
		//TODO instanciate players
		ArrayList<Divinity> meDiv = new ArrayList<>();
		meDiv.add(Utils.getDivinityByName("baldr"));
		meDiv.add(Utils.getDivinityByName("frigg"));
		meDiv.add(Utils.getDivinityByName("heimdal"));
		ArrayList<Divinity> opponentDiv = new ArrayList<>();
		opponentDiv.add(Utils.getDivinityByName("bragi"));
		opponentDiv.add(Utils.getDivinityByName("brunehilde"));
		opponentDiv.add(Utils.getDivinityByName("freyja"));
		Player me = new Player("SAINT Bernard de La Villardière", meDiv);
		Player opponent = new Player("Titouan le gueu des prairies", opponentDiv);
		gameplayController = new GameplayController(me, opponent, view);
		loader.setController(gameplayController);
	}
}
