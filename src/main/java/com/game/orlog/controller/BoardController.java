package com.game.orlog.controller;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import com.game.orlog.ValhallaOrlogApplication;
import com.game.orlog.model.Board;
import com.game.orlog.model.entity.Player;
import com.game.orlog.model.items.Die;
import com.game.orlog.model.items.Face;
import com.game.orlog.utils.LocalisationSystem;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class BoardController {
	private Board board;
	
	private Player me;
	private Player opponent;
	private Map<BorderPane, Boolean> mapDice;
	
	private Player currentTurnSPlayer;
	
	private ArrayList<Node> elementsWithId;
	
	private ImageView coinFlip;
	private ImageView token_top_player;
	private ImageView token_bottom_player;
	
	public BoardController(Player me, Player opponent, GridPane view) {
		this.me = me;
		this.opponent = opponent;
		mapDice = new HashMap<BorderPane, Boolean>();
		
		board = new Board(opponent, me);
		
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
					System.out.println(me.getDice().get(node.getId().charAt(3)-48).getVisibleFace());
				}
			} else if (node.getId().contains("name")) {
				if (node.getId().contains("bottom")) {
					((Labeled) node).setText(me.getName());
				} else {
					((Labeled) node).setText(opponent.getName());
				}
			} else {
//				System.out.print(node.getId() + "\t");
//				System.out.println(node.localToScreen(node.getBoundsInLocal()));
			}
		};
	}
	
	private void setImageOnDie(Player player, Node node, Die die) {
		Face face = player.getDice().get(node.getId().charAt(3)-48).getVisibleFace();
		URL url = ValhallaOrlogApplication.class.getResource("img/common/bouclier_ok.jpg");
		((ImageView)((BorderPane) node).getCenter()).setImage(new Image(url.toExternalForm()));
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

		if (currentTurnSPlayer.equals(me)) {
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
	
	public void onDieClicked(ActionEvent e) {
		//TODO
	}

}
