package com.game.orlog.controller;

import java.util.ArrayList;
import com.game.orlog.ValhallaOrlogApplication;
import com.game.orlog.model.entity.Player;
import com.game.orlog.model.enumClass.ActionEnum;
import com.game.orlog.model.enumClass.EndGamePossibilitiesEnum;
import com.game.orlog.model.enumClass.GamePhaseEnum;
import com.game.orlog.model.items.Die;
import com.game.orlog.utils.LocalisationSystem;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class GameplayController {
	private final static byte SPEED = 1;
	private byte turnNumber;
	private GamePhaseEnum gamePhase;
	private Player me;
	private Player opponent;
	private EndGamePossibilitiesEnum endGame;
	/**
	 * The player with the token
	 */
	private Player firstPlayer;
	/**
	 * The player who can interact
	 */
	private Player currentPlayer;
	
	private Thread threadDoATurn;
	private boolean isDoATurnOver;

	private BoardController boardController;

	private boolean isSelectionDone;
	private Object playerStopEvent = new Object();


	public GameplayController(Player me, Player opponent, GridPane view) {
		turnNumber = 0;
		gamePhase = GamePhaseEnum.IDLE;
		this.me = me;
		this.opponent = opponent;
		endGame = EndGamePossibilitiesEnum.NO;

		boardController = new BoardController(me, opponent, view);

		firstPlayer = boardController.flipTheCoinAnimation();
		currentPlayer = firstPlayer;
		isSelectionDone = true;

		ArrayList<Node> nodes = new ArrayList<Node>();
		LocalisationSystem.addAllDescendents(
				ValhallaOrlogApplication.getRoot(), nodes);

		for (Node node : nodes) {
			if (node.getId() == null) {
				continue;
			}
			if (!node.getId().contains("die")) {
				continue;
			}
			if (!node.getId().contains("Bottom")) {
				continue;
			}
			node.getStyleClass().add("image-view-wrapper");
			node.setOnMouseClicked(event -> {					    
				if (gamePhase != GamePhaseEnum.DICE_SELECTION) {
					return;
				}
				if (isSelectionDone) {
					return;
				}
				for(Die die : me.getDice()) {
					if (die == null) {
						continue;
					}
					if (me.getDice().indexOf(die)
							!= node.getId().charAt(3)-48) {
						continue;
					}
					boardController.onDieClick(node);
				}
			});
		};

		boardController.getValidateSelectionButton().setVisible(false);

		boardController.getValidateSelectionButton().setOnAction(event -> {
			synchronized(playerStopEvent) {
				isSelectionDone = true;
				playerStopEvent.notifyAll();
			}
		});
		
		isDoATurnOver = true;
		gameLoop();
	}

	
	public void gameLoop() {
		Thread gameThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				while(gamePhase != GamePhaseEnum.END_GAME) {
					if (gamePhase == GamePhaseEnum.IDLE) {
						gamePhase = gamePhase.next();
						turnNumber++;
						if (threadDoATurn != null
								&& !isDoATurnOver) {
							threadDoATurn = null;
						}
						if (isDoATurnOver) {
							threadDoATurn = createThreadDoATurn();
							threadDoATurn.start();
						}
					}
				}
				//TODO need a popup window with new FXML
				System.out.println(endGame);
			}
		});
		gameThread.start();
	}
	
	public Thread createThreadDoATurn() {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				isDoATurnOver = false;
				doATurn();
				isDoATurnOver = true;
			}
		});
	}

	public void doATurn() {
		while(gamePhase != GamePhaseEnum.IDLE) {
			try {
				processToNextGamePhase();
				//to slow down the game
				Thread.sleep(SPEED*1000);
			} catch (InterruptedException e) {}
		}
	}

	public void processToNextGamePhase() throws InterruptedException {
		switch (gamePhase) {
		case IDLE:
			break;
		case ROLL:
			if (currentPlayer.equals(me)) {
				if (currentPlayer.getRemainingRolls() == Player.MAX_ROLLS) {
					currentPlayer = nextPlayer(currentPlayer);
					if (currentPlayer.getRemainingRolls() == Player.MAX_ROLLS) {
						gamePhase = gamePhase.next().next();
					} else {
						//TODO while(gamePhase == opponentGamePhase){
						// wait for proxy message
						// opponent.getDice()
						// }
						gamePhase = gamePhase.next();
					}
				} else {
					rollDice(currentPlayer);
				}
				gamePhase = gamePhase.next();
			} else {
				// TODO get infos from proxy in loop
				// Player.getDice()
				// getGamePhase()
				//TODO while(gamePhase == opponentGamePhase){
				// wait for proxy message
				// opponent = proxy.opponent
//				byte index = 0;
//				for (Die opponenDie : opponent.getDice()) {
//					boardController.setImageOnDie(opponent, opponenDie, index);
//					index++;
//				}
				// }
				//TODO deleter after proxy is implemented
				currentPlayer.incrementRemainingThrows();
				gamePhase = gamePhase.next();
			}
			break;
		case DICE_SELECTION:
			if (currentPlayer.equals(me)) {
				if (me.getRemainingRolls() == Player.MAX_ROLLS) {
					selectAllTheRemainingDiceToKeep(me.getDice());
					currentPlayer = nextPlayer(currentPlayer);
					gamePhase.previous();
					break;
				}
				boardController.getValidateSelectionButton().setVisible(true);
				selectDiceTokeep(me.getDice());
				gamePhase = gamePhase.previous();
				boardController.getValidateSelectionButton().setVisible(false);
				currentPlayer = nextPlayer(currentPlayer);
			} else {
				//TODO get infos from proxy in loop
				// Player.getDiceToKeep()
				gamePhase = gamePhase.previous();
				currentPlayer = nextPlayer(currentPlayer);
			}
			break;
		case FAVORS_SELECTION:
			//TODO
			gamePhase = gamePhase.next();
			break;
		case RESOLVE: //steal golds & place dice in center
			boardController.moveSelectedDiceToMiddle();
			updatePlayerGoldWithGoldenDice(me);
			//TODO test. Replace by proxy call
			opponent.addGold((byte) 12);
			
			boardController.refreshGold();
			
			Thread.sleep(1000);

			stealGold();
			boardController.refreshGold();
			gamePhase = gamePhase.next();
			break;
		case DIVINE_FAVOR:
			//TODO
			gamePhase = gamePhase.next();
			break;
		case FIGHT:
			//TODO need dice from opponent proxy
			fight();
			gamePhase = gamePhase.next();
			break;
		case END_TURN:
			me.resetRemainingThrows();
			me.resetAllDiceList();
			boardController.setLifeImageForPlayer(me, me.getHealthPoint());
			
			opponent.resetRemainingThrows();
			//TODO uncomment when real player is implemented (PROXY)
//			opponent.resetAllDiceList();
			boardController.setLifeImageForPlayer(opponent,
												  opponent.getHealthPoint());
			
			currentPlayer = firstPlayer = nextPlayer(firstPlayer);
			boardController.setCurrentTurnSPlayer(currentPlayer);
			
			boardController.resetHighlightedDice();
			boardController.hideMiddleDice();
		case END_GAME:
			if (me.getHealthPoint() == 0 && opponent.getHealthPoint() == 0) {
				endGame = EndGamePossibilitiesEnum.DRAW;
				gamePhase = gamePhase.next();
				break;
			}
			if (me.getHealthPoint() == 0) {
				endGame = EndGamePossibilitiesEnum.OPPONENT_WON;
				gamePhase = gamePhase.next();
				break;
			}
			if (opponent.getHealthPoint() == 0) {
				endGame = EndGamePossibilitiesEnum.ME_WON;
				gamePhase = gamePhase.next();
				break;
			}
		default:
			gamePhase = GamePhaseEnum.IDLE;
			break;
		}
	}

	/**
	 * Calculates health point for each player after
	 * the dice's battle.
	 */
	private void fight() {
		byte meAxe = 0;
		byte meHelmet = 0;
		byte meArrow = 0;
		byte meShield = 0;
		byte opponentAxe = 0;
		byte opponentHelmet = 0;
		byte opponentArrow = 0;
		byte opponentShield = 0;
		byte meDamage = 0;
		byte opponentDamage = 0;
		
		for (Die die : me.getDiceToKeep()) {
			if (die == null) {
				continue;
			}
			switch (die.getVisibleFace().getFace()){
			case AXE :
				meAxe++;
				break;
			case ARROW :
				meArrow++;
				break;
			case SHIELD :
				meShield++;
				break;
			case HELMET :
				meHelmet++;
				break;
			default:
				break;
			}
		}
		for (Die die : opponent.getDiceToKeep()) {
			if (die == null) {
				continue;
			}
			switch (die.getVisibleFace().getFace()){
			case AXE :
				opponentAxe++;
				break;
			case ARROW :
				opponentArrow++;
				break;
			case SHIELD :
				opponentShield++;
				break;
			case HELMET :
				opponentHelmet++;
				break;
			default:
				break;
			}
		}
		if (opponentArrow - meShield > 0) {
			meDamage += opponentArrow - meShield;
		}
		if (opponentAxe - meHelmet > 0) {
			meDamage += opponentAxe - meHelmet;
		}
		if (meAxe - opponentHelmet > 0) {
			opponentDamage += meAxe - opponentHelmet;
		}
		if (meArrow - opponentShield > 0) {
			opponentDamage += meArrow - opponentShield;
		}
		me.deduceHealthPoint(meDamage);
		opponent.deduceHealthPoint(opponentDamage);
	}

	/**
	 * Calculates and proceed to the stealing for each player.
	 * Starts with the player with the token.
	 * In case a player don't have enough gold to be stolen,
	 * the maximum gold that he possess will be stolen.
	 * In case the amount is 0, no gold will be stolen.
	 */
	private void stealGold() {
		byte goldToSteal = 0;
		byte maxGoldToSteal = 0;
		
		Player j1 = firstPlayer;
		Player j2 = null;
		if (firstPlayer.equals(me)) {
			j2 = opponent;
		} else {
			j2 = me;
		}
		
		maxGoldToSteal = j2.getGold();
		for (Die die : j1.getDiceToKeep()) {
			if (die == null) continue;
			if (die.getVisibleFace().getFace() == ActionEnum.HAND) {
				goldToSteal++;
			}
		}
		if (goldToSteal > maxGoldToSteal) {
			goldToSteal = maxGoldToSteal;
		}
		j1.addGold(goldToSteal);
		j2.removeGold(goldToSteal);
		/**************************************/
		goldToSteal = 0;
		maxGoldToSteal = j1.getGold();
		for (Die die : j2.getDiceToKeep()) {
			if (die == null) continue;
			if (die.getVisibleFace().getFace() == ActionEnum.HAND) {
				goldToSteal++;
			}
		}
		if (goldToSteal > maxGoldToSteal) {
			goldToSteal = maxGoldToSteal;
		}
		j2.addGold(goldToSteal);
		j1.removeGold(goldToSteal);
	}
	
	private void updatePlayerGoldWithGoldenDice(Player player) {
		for(Die die : player.getDiceToKeep()) {
			if (die.getVisibleFace().getIsSpecial()) {
				player.incrementGold();
			}
		}
	}
	
	private void selectAllTheRemainingDiceToKeep(ArrayList<Die> dice) {
		for (byte index = 0; index < me.getDice().size(); index++) {
			if (me.getDice().get(index) == null) {
				continue;
			}
			for(BorderPane key : boardController.getmapBottomDice().keySet()) {
				if (index != key.getId().charAt(3)-48) {
					continue;
				}
				me.getDiceToKeep().set(index, me.getDice().get(index));
				me.getDice().set(index, null);
				boardController.onDieClick(key);
				break;
			}
		}
	}

	private void selectDiceTokeep(ArrayList<Die> dice) {
		//TODO
		isSelectionDone = false;
		synchronized (playerStopEvent) {
			while(!isSelectionDone) {
				try {
					playerStopEvent.wait();
				} catch (InterruptedException x) {}
			}
		}

		for (byte index = 0; index < me.getDice().size(); index++) {
			if (me.getDice().get(index) == null) {
				continue;
			}
			for(BorderPane key : boardController.getmapBottomDice().keySet()) {
				if (index != key.getId().charAt(3)-48) {
					continue;
				}
				boolean isSelected = (boolean) boardController.getmapBottomDice().get(key);
				if (isSelected) {
					me.getDiceToKeep().set(index, me.getDice().get(index));
					me.getDice().set(index, null);
				}
			}
		}
	}

	/**
	 * Roll the dice of a player and update the view
	 * 
	 * @param player The current player
	 */
	private void rollDice(Player player) {
		player.rollDice();
		player.incrementRemainingThrows();
		for (byte i = 0; i < player.getDice().size(); i++) {
			if (player.getDice().get(i) != null) {
				boardController.setImageOnDie(player, player.getDice().get(i), i);
			}
		}
	}

	public GamePhaseEnum getGamePhase() {
		return gamePhase;
	}

	private Player nextPlayer(Player oldPlayer) {
		if (oldPlayer.equals(me)) {
			return opponent;
		} else {
			return me;
		}
	}
}