package com.game.orlog.controller;

import java.util.ArrayList;

import com.game.orlog.ValhallaOrlogApplication;
import com.game.orlog.model.entity.Player;
import com.game.orlog.model.enumClass.GamePhaseEnum;
import com.game.orlog.model.items.Die;
import com.game.orlog.utils.LocalisationSystem;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GameplayController {
	private byte turnNumber;
	private GamePhaseEnum gamePhase;
	private Player me;
	private Player opponent;
	private Player currentPlayer;

	private BoardController boardController;

	public GameplayController(Player me, Player opponent, GridPane view) {
		turnNumber = 0;
		gamePhase = GamePhaseEnum.IDLE;
		this.me = me;
		this.opponent = opponent;

		boardController = new BoardController(me, opponent, view);

		currentPlayer = boardController.flipTheCoinAnimation();

		ArrayList<Node> nodes = new ArrayList<Node>();
		LocalisationSystem.addAllDescendents(
				ValhallaOrlogApplication.getRoot(), nodes);
		
		for (Node node : nodes) {
			if (node.getId() == null) {
				continue;
			}
			if (node.getId().equals("coinFlip")) {
				((ImageView)node).setOnMousePressed(event -> {
					nextPlayer();
					boardController.setCurrentTurnSPlayer(currentPlayer);
				});
			} else if (node.getId().contains("die")) {
				if (node.getId().contains("bottom")) {
					node.getStyleClass().add("image-view-wrapper");
				    node.setOnMouseClicked(event -> {
					    boardController.onDieClick(node);
				    	if (gamePhase == GamePhaseEnum.DICE_SELECTION) {
						    boardController.onDieClick(node);
				    	}
				    });
				}
			}
		};
	}

	public void doATurn() {
		//TODO
	}

	public void changeToNextGamePhase() {
		switch (gamePhase) {
		case IDLE:
			//TODO
			break;
		case ROLL:
			if (currentPlayer.getRemainingRolls() == Player.MAX_ROLLS) {
				nextPlayer();
				if (currentPlayer.getRemainingRolls() == Player.MAX_ROLLS) {
					// skip the roll and dice selection phase
					gamePhase = gamePhase.next().next();
				}
			} else {
				currentPlayer.setDice(rollDice(currentPlayer.getDice()));
				gamePhase = gamePhase.next();
			}
			break;
		case DICE_SELECTION:
			currentPlayer.setDiceToKeep(
					selectDiceTokeep(currentPlayer.getDice()));
			gamePhase = gamePhase.previous();
			nextPlayer();
			break;
		case FAVORS_SELECTION:
			//TODO
			break;
		case RESOLVE:
			//TODO
			break;
		case FIGHT:
			//TODO
			break;
		case DIVINE_FAVOR:
			//TODO
			break;
		case END_TURN:
			nextPlayer();
			boardController.setCurrentTurnSPlayer(currentPlayer);
		case END_GAME:
			//TODO
			break;
		default:
			gamePhase = gamePhase.next();
			break;
		}
	}
	private ArrayList<Die> selectDiceTokeep(ArrayList<Die> dice) {
		ArrayList<Die> diceToKeep = new ArrayList<>();
		boolean isSelectionDone = false;

		while (!isSelectionDone) {

		}

		return diceToKeep;
	}

	private ArrayList<Die> rollDice(ArrayList<Die> dice) {
		for (Die die : dice) {
			die.roll();
		}
		return dice;
	}

	public GamePhaseEnum getGamePhase() {
		return gamePhase;
	}

	private void nextPlayer() {
		if (currentPlayer.equals(me)) {
			currentPlayer = opponent;
		} else {
			currentPlayer = me;
		}

		//		//TODO it's a test
		//		Thread th = new Thread() {
		//			public void run() {
		//				try {
		//					while(true) {
		//					sleep(4000);
		//						if (currentPlayer.equals(me)) {
		//							currentPlayer = opponent;
		//						} else {
		//							currentPlayer = me;
		//						}
		//						System.out.println("change");
		//						boardController.setCurrentTurnSPlayer(currentPlayer);
		//					}
		//				} catch (InterruptedException e) {
		//					e.printStackTrace();
		//				}
		//			}
		//		};
		//		th.start();
	}
}