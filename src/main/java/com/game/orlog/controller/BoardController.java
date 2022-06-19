package com.game.orlog.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.game.orlog.ValhallaOrlogApplication;
import com.game.orlog.model.Board;
import com.game.orlog.model.entity.Divinity;
import com.game.orlog.model.entity.Player;
import com.game.orlog.model.items.Die;
import com.game.orlog.utils.LocalisationSystem;
import com.game.orlog.utils.PopupWindow;

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
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * Class controlling the Board with all the interactions
 * 
 * @author Theault & Titouan
 *
 */
public class BoardController {
	private Board board;

	private Player bottomPlayer;
	private Player topPlayer;
	private Player currentTurnSPlayer;

	private ArrayList<Node> elementsWithId;

	private ImageView coinFlip;

	private ImageView tokenTopPlayer;
	private BorderPane healthTopPlayer;
	private Text goldTopPlayer;
	private ImageView tokenBottomPlayer;
	private BorderPane healthBottomPlayer;
	private Text goldBottomPlayer;
	//TODO add also the topPlayer dice
	private Map<BorderPane, Boolean> mapBottomDice;
	private Map<BorderPane, Boolean> mapTopDice;
	private BorderPane[] bottomDivinities;
	private BorderPane[] topDivinities;

	private Map<String, Byte> selectedGodAttackRank = new HashMap<>();

	private Button validateSelectionButton;

	public BoardController(Player bottomPlayer, Player topPlayer, GridPane view) {
		this.bottomPlayer = bottomPlayer;
		this.topPlayer = topPlayer;
		mapBottomDice = new HashMap<BorderPane, Boolean>();
		mapTopDice = new HashMap<BorderPane, Boolean>();
		bottomDivinities = new BorderPane[3];
		topDivinities = new BorderPane[3];

		board = new Board(topPlayer, bottomPlayer);

		populateNode();

		setImagesOnBottomPlayerGod();
		//TODO uncomment when proxy updated TopPlayer
		setImagesOnTopPlayerGod();

		tokenTopPlayer.setVisible(false);
		tokenBottomPlayer.setVisible(false);
		hideMiddleDice();
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
			} else if (node.getId().equals("tokenTopPlayer")) {
				tokenTopPlayer = (ImageView) node;
			} else if (node.getId().equals("tokenBottomPlayer")) {
				tokenBottomPlayer = (ImageView) node;
			} else if (node.getId().contains("die")) {
				if (node.getId().contains("Bottom")) {
					mapBottomDice.put((BorderPane) node, false);
				} else {
					mapTopDice.put((BorderPane) node, false);
				}
			} else if (node.getId().contains("name")) {
				if (node.getId().contains("Bottom")) {
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
			} else if (node.getId().contains("god")) {
				if (node.getId().contains("BottomPlayer")) {
					byte index = (byte) (node.getId().charAt(3)-48);
					bottomDivinities[index] = (BorderPane) node;
				}
				if (node.getId().contains("TopPlayer")) {
					byte index = (byte) (node.getId().charAt(3)-48);
					topDivinities[index] = (BorderPane) node;
				}
				((BorderPane)node).setOnMouseClicked(event -> {
					onGodClicked((BorderPane) node);
				});
			} else if (node.getId().equals("goldBottomPlayer")) {
				goldBottomPlayer = (Text) node;
			} else if (node.getId().equals("goldTopPlayer")) {
				goldTopPlayer = (Text) node;
			} else {
				//				System.out.print(node.getId() + "\t");
				//				System.out.println(node.localToScreen(node.getBoundsInLocal()));
			}
		};
		refreshGold();
	}

	public void moveSelectedDiceToMiddle() {
		byte meAxe = 0;
		byte meHelmet = 0;
		byte meArrow = 0;
		byte meShield = 0;
		byte opponentAxe = 0;
		byte opponentHelmet = 0;
		byte opponentArrow = 0;
		byte opponentShield = 0;

		for (Die die : bottomPlayer.getDiceToKeep()) {
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
		for (Die die : topPlayer.getDiceToKeep()) {
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
		for (Node node : elementsWithId) {
			if (node.getId() == null) {
				continue;
			}
			if (!node.getId().contains("center")) {
				continue;
			}

			byte indexImage = (byte) (node.getId().charAt(6)-48);
			if (node.getId().charAt(7)-48 < 9) {
				indexImage = (byte) (node.getId().charAt(7)-48 + 9);
			}
			boolean needUpdate = false;
			String path = "";
			if (node.getId().contains("BottomPlayer")) {
				if (indexImage <= meAxe) {
					path = "img/common/axe.jpg";
					needUpdate = true;
				} else if (indexImage <= Math.max(meAxe, opponentHelmet)
						+ meArrow
						&& indexImage > Math.max(meAxe, opponentHelmet)) {
					path = "img/common/arrow.jpg";
					needUpdate = true;
				} else if (indexImage <= Math.max(meAxe, opponentHelmet)
						+ Math.max(meArrow, opponentShield)
						+ meShield
						&& indexImage > Math.max(meAxe, opponentHelmet)
						+ Math.max(meArrow, opponentShield)) {
					path = "img/common/shield.jpg";
					needUpdate = true;
				} else if (indexImage <= Math.max(meAxe, opponentHelmet)
						+ Math.max(meArrow, opponentShield)
						+ Math.max(meShield, opponentArrow)
						+ meHelmet
						&& indexImage > Math.max(meAxe, opponentHelmet)
						+ Math.max(meArrow, opponentShield)
						+ Math.max(meShield, opponentArrow)) {
					path = "img/common/helmet.jpg";
					needUpdate = true;
				}
			} else if (node.getId().contains("TopPlayer")) {
				if (indexImage <= opponentHelmet) {
					path = "img/common/helmet.jpg";
					needUpdate = true;
				} else if (indexImage <= Math.max(meAxe, opponentHelmet)
						+ opponentShield
						&& indexImage > Math.max(meAxe, opponentHelmet)) {
					path = "img/common/shield.jpg";
					needUpdate = true;
				} else if (indexImage <= Math.max(meAxe, opponentHelmet)
						+ Math.max(meArrow, opponentShield)
						+ opponentArrow
						&& indexImage > Math.max(meAxe, opponentHelmet)
						+ Math.max(meArrow, opponentShield)) {
					path = "img/common/arrow.jpg";
					needUpdate = true;
				} else if (indexImage <= Math.max(meAxe, opponentHelmet)
						+ Math.max(meArrow, opponentShield)
						+ Math.max(meShield, opponentArrow)
						+ opponentAxe
						&& indexImage > Math.max(meAxe, opponentHelmet)
						+ Math.max(meArrow, opponentShield)
						+ Math.max(meShield, opponentArrow)) {
					path = "img/common/axe.jpg";
					needUpdate = true;
				}
			}

			if (needUpdate) {
				URL url = ValhallaOrlogApplication.class.getResource(path);
				((ImageView)node).setImage(new Image(url.toExternalForm()));
				node.setVisible(true);
			}
		}
	}

	public void hideMiddleDice() {
		for (Node node : elementsWithId) {
			if (node.getId() == null) {
				continue;
			}
			if (!node.getId().contains("center")) {
				continue;
			}
			node.setVisible(false);
		}
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

	public Map<BorderPane, Boolean> getmapBottomDice() {
		return mapBottomDice;
	}
	
	public Button getValidateSelectionButton() {
		return validateSelectionButton;
	}

	public void refreshGold() {
		goldTopPlayer.setText(((Byte)topPlayer.getGold()).toString());
		goldBottomPlayer.setText(((Byte)bottomPlayer.getGold()).toString());
	}

	public void setImagesOnBottomPlayerGod() {
		byte index = 0;
		for (BorderPane node : bottomDivinities) {
			String path = "img/gods/color/" + bottomPlayer
					.getDivinities()
			.get(index)
			.getName()
			.toLowerCase();
			path = path.concat(".jpg");
			URL url = ValhallaOrlogApplication.class.getResource(path);
			((ImageView)((BorderPane) node).getCenter()).setImage(new Image(url.toExternalForm()));

			index++;
		}
	}

	public void setImagesOnTopPlayerGod() {
		byte index = 0;
		for (BorderPane node : topDivinities) {
			String path = "img/gods/color/" + topPlayer
					.getDivinities()
					.get(index)
					.getName()
					.toLowerCase();
			path = path.concat(".jpg");
			URL url = ValhallaOrlogApplication.class.getResource(path);
			((ImageView)((BorderPane) node).getCenter()).setImage(new Image(url.toExternalForm()));

			index++;
		}
	}

	/**
	 * Set The image for the dice of a player.
	 * 
	 * @param player The player which needs to have the die's images changed
	 * @param die The die that need to be changed.
	 * @param dieNumber The index of the die in the list.
	 */
	public void setImageOnDie(Player player, Die die, byte dieNumber) {
		Set<BorderPane> setDice;
		if (player.equals(bottomPlayer)) {
			setDice = mapBottomDice.keySet();
		} else {
			setDice = mapTopDice.keySet();
		}
		for (Node node : setDice) {
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
		for (Node node : mapBottomDice.keySet()) {
			if (!nodeClicked.equals(node)) {
				continue;
			}
			PseudoClass imageViewBorder = PseudoClass.getPseudoClass("border");
			mapBottomDice.replace((BorderPane) node, !mapBottomDice.get(node));
			node.pseudoClassStateChanged(imageViewBorder, mapBottomDice.get(node));
		}
	}

	public void onGodClicked(BorderPane node) {
		for (BorderPane god : bottomDivinities) {
			if (god.equals(node)) {
				String path = ((ImageView)god.getCenter()).getImage()
						.getUrl();
				PopupWindow.showPopupGod(
						path.substring(path.lastIndexOf('/')+1
								,path.lastIndexOf('.'))
						,true, selectedGodAttackRank);

				return;
			}
		}
		for (BorderPane god : topDivinities) {
			if (god.equals(node)) {
				String path = ((ImageView)god.getCenter()).getImage().getUrl();
				PopupWindow.showPopupGod(
						path.substring(path.lastIndexOf('/')+1
								, path.lastIndexOf('.'))
						, false, selectedGodAttackRank);

				return;
			}
		}
	}

	public void resetHighlightedDice() {
		for (BorderPane node : mapBottomDice.keySet()) {
			PseudoClass imageViewBorder = PseudoClass.getPseudoClass("border");
			mapBottomDice.replace(node, false);
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

						x = tokenTopPlayer.localToScene(tokenTopPlayer.getBoundsInLocal()).getCenterX()
								- coinFlip.localToScene(coinFlip.getBoundsInLocal()).getCenterX();
						y = tokenTopPlayer.localToScene(tokenTopPlayer.getBoundsInLocal()).getCenterY()
								- coinFlip.localToScene(coinFlip.getBoundsInLocal()).getCenterY();

						scaleCoin.setToX(tokenTopPlayer.getScaleX());
						scaleCoin.setToY(tokenTopPlayer.getScaleY());
					} else {
						Thread.sleep(950);
						rotator.stop();
						coinFlip.setRotate(180);

						x = tokenBottomPlayer.localToScene(tokenBottomPlayer.getBoundsInLocal()).getCenterX()
								- coinFlip.localToScene(coinFlip.getBoundsInLocal()).getCenterX();
						y = tokenBottomPlayer.localToScene(tokenBottomPlayer.getBoundsInLocal()).getCenterY()
								- coinFlip.localToScene(coinFlip.getBoundsInLocal()).getCenterY();

						scaleCoin.setToX(tokenBottomPlayer.getScaleX());
						scaleCoin.setToY(tokenBottomPlayer.getScaleY());
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
			x = tokenBottomPlayer.localToScene(tokenBottomPlayer.getBoundsInLocal()).getCenterX()
					- coinFlip.localToScene(coinFlip.getBoundsInLocal()).getCenterX();
			y = tokenBottomPlayer.localToScene(tokenBottomPlayer.getBoundsInLocal()).getCenterY()
					- coinFlip.localToScene(coinFlip.getBoundsInLocal()).getCenterY();
		} else {
			x = tokenTopPlayer.localToScene(tokenTopPlayer.getBoundsInLocal()).getCenterX()
					- coinFlip.localToScene(coinFlip.getBoundsInLocal()).getCenterX();
			y = tokenTopPlayer.localToScene(tokenTopPlayer.getBoundsInLocal()).getCenterY()
					- coinFlip.localToScene(coinFlip.getBoundsInLocal()).getCenterY();
		}

		coinFlip.setVisible(true);
		translatePos.setNode(coinFlip);
		translatePos.setByX(x);
		translatePos.setByY(y);
		translatePos.play();
	}

	public final Map<String, Byte> getSelectedGodAttackRank() {
		return selectedGodAttackRank;
	}
}