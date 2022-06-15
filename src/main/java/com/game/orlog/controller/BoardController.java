package com.game.orlog.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.game.orlog.ValhallaOrlogApplication;
import com.game.orlog.model.Board;
import com.game.orlog.model.entity.Player;
import com.game.orlog.model.items.Die;
import com.game.orlog.utils.LocalisationSystem;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class BoardController {
	private Board board;

	private Player bottomPlayer;
	private Player topPlayer;
	private Map<BorderPane, Boolean> mapDice;

	private Player currentTurnSPlayer;

	private ArrayList<Node> elementsWithId;

	private ImageView coinFlip;
	private ImageView token_top_player;
	private ImageView token_bottom_player;
	private BorderPane healthTopPlayer;
	private BorderPane healthBottomPlayer;
	
	private Button validateSelectionButton;

	public BoardController(Player bottomPlayer, Player topPlayer, GridPane view) {
		this.bottomPlayer = bottomPlayer;
		this.topPlayer = topPlayer;
		mapDice = new HashMap<BorderPane, Boolean>();

		board = new Board(topPlayer, bottomPlayer);

		populateNode();

		token_top_player.setVisible(false);
		token_bottom_player.setVisible(false);
	}

	private void populateNode() {
		elementsWithId = new ArrayList<>();
		LocalisationSystem.addAllDescendents(ValhallaOrlogApplication.getRoot(), elementsWithId);
		for (Node node : elementsWithId) {
			if (node.getId() == null) {
				continue;
			}
			if (node.getId().equals("coinFlip")) {
				this.coinFlip = (ImageView) node;
			} else if (node.getId().equals("token_top_player")) {
				token_top_player = (ImageView) node;
			} else if (node.getId().equals("token_bottom_player")) {
				token_bottom_player = (ImageView) node;
			} else if (node.getId().contains("die")) {
				if (node.getId().contains("bottom")) {
					mapDice.put((BorderPane) node, false);
					//TODO delete
					setImageOnDie(bottomPlayer,
							bottomPlayer.getDice().get(node.getId().charAt(3)-48),
							(byte) (node.getId().charAt(3)-48));
				}
			} else if (node.getId().contains("name")) {
				if (node.getId().contains("bottom")) {
					((Labeled) node).setText(bottomPlayer.getName());
				} else {
					((Labeled) node).setText(topPlayer.getName());
				}
			} else if (node.getId().equals("validateSelectionButton")) {
				validateSelectionButton = (Button) node;
			} else if (node.getId().equals("healthTopPlayer")) {
				healthTopPlayer = (BorderPane) node;
			} else if (node.getId().equals("healthBottomPlayer")) {
				healthBottomPlayer = (BorderPane) node;
			} else {
				//				System.out.print(node.getId() + "\t");
				//				System.out.println(node.localToScreen(node.getBoundsInLocal()));
			}
		};
	}
	
	/**
	 * Set The image for the health of a player.
	 * 
	 * @param player The player which needs to have the health's images changed.
	 * @param healthPoint the new number of health point.
	 */
	public void setLifeImageForPlayer(Player player, byte healthPoint) {
		String path = "img/lifePoints/" + healthPoint + ".png";
		URL url = ValhallaOrlogApplication.class.getResource(path);
		if (player.equals(topPlayer)) {
			((ImageView) healthTopPlayer.getCenter()).setImage(new Image(url.toExternalForm()));
		} else {
			((ImageView) healthBottomPlayer.getCenter()).setImage(new Image(url.toExternalForm()));
		}
	}
	
	public Map<BorderPane, Boolean> getMapDice() {
		return mapDice;
	}
	public Button getValidateSelectionButton() {
		return validateSelectionButton;
	}

	/**
	 * Set The image for the dice of a player.
	 * 
	 * @param player The player which needs to have the die's images changed
	 * @param die The die that need to be changed.
	 * @param dieNumber The index of the die in the list.
	 */
	public void setImageOnDie(Player player, Die die, byte dieNumber) {
		for (Node node : mapDice.keySet()) {
			if (node.getId().charAt(3)-48 == dieNumber) {
				String path = "img/common/" + die.getVisibleFace().getFace().name()
						.toLowerCase();
				if (die.getVisibleFace().getIsSpecial()) {
					path = path.concat("Spe");
				}
				path = path.concat(".jpg");
				URL url = ValhallaOrlogApplication.class.getResource(path);
				((ImageView)((BorderPane) node).getCenter()).setImage(new Image(url.toExternalForm()));
				break;
			}
		}
	}

	public void onDieClick(Node nodeClicked) {
		for (Node node : mapDice.keySet()) {
			if (!nodeClicked.equals(node)) {
				continue;
			}
			PseudoClass imageViewBorder = PseudoClass.getPseudoClass("border");
			mapDice.replace((BorderPane) node, !mapDice.get(node));
			node.pseudoClassStateChanged(imageViewBorder, mapDice.get(node));
		}
	}

	public void resetHighlitedDice() {
		for (BorderPane node : mapDice.keySet()) {
			PseudoClass imageViewBorder = PseudoClass.getPseudoClass("border");
			mapDice.replace(node, false);
			node.pseudoClassStateChanged(imageViewBorder, false);
		}
	}

	/**
	 * Flip a coin and decide whose player start
	 * @return The player who start
	 */
	protected Player flipTheCoinAnimation() {
		RotateTransition rotator = createRotator(coinFlip);
		TranslateTransition translateCoin = new TranslateTransition();
		ScaleTransition scaleCoin = new ScaleTransition();
		boolean isTopPlayerSelected = new Random().nextBoolean();
		if (isTopPlayerSelected) {
			currentTurnSPlayer = board.getP1();
		} else {
			currentTurnSPlayer = board.getP2();
		}

		rotator.play();

		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					double x = 0;
					double y = 0;
					if (isTopPlayerSelected) {
						Thread.sleep(1500);
						rotator.stop();
						coinFlip.setRotate(0);

						x = token_top_player.localToScene(token_top_player.getBoundsInLocal()).getCenterX()
								- coinFlip.localToScene(coinFlip.getBoundsInLocal()).getCenterX();
						y = token_top_player.localToScene(token_top_player.getBoundsInLocal()).getCenterY()
								- coinFlip.localToScene(coinFlip.getBoundsInLocal()).getCenterY();

						scaleCoin.setToX(token_top_player.getScaleX());
						scaleCoin.setToY(token_top_player.getScaleY());
					} else {
						Thread.sleep(950);
						rotator.stop();
						coinFlip.setRotate(180);

						x = token_bottom_player.localToScene(token_bottom_player.getBoundsInLocal()).getCenterX()
								- coinFlip.localToScene(coinFlip.getBoundsInLocal()).getCenterX();
						y = token_bottom_player.localToScene(token_bottom_player.getBoundsInLocal()).getCenterY()
								- coinFlip.localToScene(coinFlip.getBoundsInLocal()).getCenterY();

						scaleCoin.setToX(token_bottom_player.getScaleX());
						scaleCoin.setToY(token_bottom_player.getScaleY());
					}

					translateCoin.setByX(x);
					translateCoin.setByY(y);
					translateCoin.setNode(coinFlip);
					scaleCoin.setNode(coinFlip);

					Thread.sleep(1000);
					scaleCoin.play();
					translateCoin.play();

					translateCoin.setOnFinished(event -> {
						coinFlip.setRotate(0);
					});
				} catch (InterruptedException e) {}
			}
		});
		th.start();

		return currentTurnSPlayer;
	}

	/**
	 * Create and return a new RotateTransition object with hard coded
	 * parameters
	 * 
	 * @param item The item to animate
	 * @return The object
	 */
	private RotateTransition createRotator(Node item) {
		RotateTransition rotator = new RotateTransition(Duration.millis(100), item);
		rotator.setAxis(Rotate.X_AXIS);
		rotator.setFromAngle(0);
		rotator.setToAngle(360);
		rotator.setInterpolator(Interpolator.LINEAR);
		rotator.setCycleCount(1000000);

		return rotator;
	}

	/**
	 * Set the currentPlayer to the one in parameter and change the token
	 * location to him with an animation transition.
	 * 
	 * @param currentTurnSPlayer The new player
	 */
	public final void setCurrentTurnSPlayer(Player currentTurnSPlayer) {
		this.currentTurnSPlayer = currentTurnSPlayer;

		TranslateTransition translatePos = new TranslateTransition();
		double x = 0;
		double y = 0;

		if (currentTurnSPlayer.equals(bottomPlayer)) {
			x = token_bottom_player.localToScene(token_bottom_player.getBoundsInLocal()).getCenterX()
					- coinFlip.localToScene(coinFlip.getBoundsInLocal()).getCenterX();
			y = token_bottom_player.localToScene(token_bottom_player.getBoundsInLocal()).getCenterY()
					- coinFlip.localToScene(coinFlip.getBoundsInLocal()).getCenterY();
		} else {
			x = token_top_player.localToScene(token_top_player.getBoundsInLocal()).getCenterX()
					- coinFlip.localToScene(coinFlip.getBoundsInLocal()).getCenterX();
			y = token_top_player.localToScene(token_top_player.getBoundsInLocal()).getCenterY()
					- coinFlip.localToScene(coinFlip.getBoundsInLocal()).getCenterY();
		}

		coinFlip.setVisible(true);
		translatePos.setNode(coinFlip);
		translatePos.setByX(x);
		translatePos.setByY(y);
		translatePos.play();
	}

	public void update() {
		//TODO
	}
}
