package com.game.orlog.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import com.game.orlog.ValhallaOrlogApplication;
import com.game.orlog.model.entity.Divinity;
import com.game.orlog.model.enumClass.OfferingEnum;
import com.game.orlog.model.enumClass.OnWhatEnum;

import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Labeled;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.WindowEvent;

/**
 * A static class to create a new popup window.
 * 
 * @author Theault & Titouan
 *
 */
public class PopupWindow {
	private static boolean isGodSelected = false;
	
	/**
	 * Create and return the popup window with the given path.
	 * 
	 * @param FXMLPath The path where to find the XML File
	 * @return The popup winwow created
	 */
	private static Popup createPopup(final String FXMLPath) {
		final Popup popup = new Popup();
		popup.setAutoFix(true);
		popup.setAutoHide(true);
		popup.setHideOnEscape(true);
		try {
			Parent p;
			p = FXMLLoader.load(
					ValhallaOrlogApplication.class.getResource(FXMLPath));
			p.getStylesheets().add(
					CSVLoader.class
					.getResource("/com/game/orlog/css/styles.css")
					.toExternalForm());
			p.getStyleClass().add("popup");
			popup.getContent().add(p);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return popup;
	}

	/**
	 * Display a popup window with the path given.
	 * 
	 * @param FXMLFile Path where to find the XML file.
	 */
	public static void showPopupMessage(final String FXMLFile) {
		final Popup popup = createPopup(FXMLFile);
		popup.setOnShown(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				popup.setX(ValhallaOrlogApplication.getStage().getX()
						+ ValhallaOrlogApplication.getStage().getWidth()/2
						- popup.getWidth()/2);
				popup.setY(ValhallaOrlogApplication.getStage().getY(
						) + ValhallaOrlogApplication.getStage().getHeight()/2
						- popup.getHeight()/2);
			}
		});
		popup.show(ValhallaOrlogApplication.getStage());
	}

	/**
	 * Display a popup window with the God's informations.
	 * 
	 * @param godName The name of the god that's need to have his
	 * informations displayed
	 * @param isBottom boolean to know if it's the bottom player
	 * @param selectedGodAttackRank HasMap with the current attacked
	 * selected of a god
	 */
	public static void showPopupGod(final String godName
			, final boolean isBottom
			, Map<String, Byte> selectedGodAttackRank) {
		final Popup popup = createPopup("God.fxml");
		popup.setOnShown(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				popup.setX(ValhallaOrlogApplication.getStage().getX()
						+ ValhallaOrlogApplication.getStage().getWidth()/2
						- popup.getWidth()/2);
				popup.setY(ValhallaOrlogApplication.getStage().getY(
						) + ValhallaOrlogApplication.getStage().getHeight()/2
						- popup.getHeight()/2);
			}
		});
		popup.show(ValhallaOrlogApplication.getStage());
		
		Divinity god = Utils.getDivinityByName(godName);
		ArrayList<Node> nodes = new ArrayList<>();
		LocalisationSystem.addAllDescendents(popup.getScene().getRoot(), nodes);
		
		for (Node node : nodes) {
			if (node.getId() == null) {
				continue;
			}
			if (node.getId().equals("godName")) {
				((Labeled) node).setText(god.getName());
			} else if (node.getId().equals("effect")) {
				((Text) node).setText(god.getEffect().getDescription());
			} else if (node.getId().equals("when")) {
				((Labeled) node).setText(god.getEffect().getWhen().getName());
			} else if (node.getId().equals("onWho")) {
				((Labeled) node).setText(god.getEffect().getOnWho().getName());
			} else if (node.getId().equals("affect1")) {
				((Labeled) node).setText(god.getEffect().getAffect().get(0).getName());
			} else if (node.getId().equals("affect2")) {
				if (god.getEffect().getAffect().size() >= 2) {
					((Labeled) node).setText(god.getEffect().getAffect().get(1).getName());
					continue;
				}
				((HBox)node.getParent()).getChildren().clear();
			} else if (node.getId().equals("offeringBox")) {
				if (god.getEffect().getOffering() == OfferingEnum.NONE) {
					((HBox)node).getChildren().clear();
				}
			} else if (node.getId().equals("offering")) {
				((Labeled) node).setText(god.getEffect().getOffering().getName());
			} else if (node.getId().equals("multiplierBox")) {
				if (god.getEffect().getMultiplierOnWhat() == OnWhatEnum.NONE) {
					((VBox)node).getChildren().clear();
				}
			} else if (node.getId().equals("onWhoMult")) {
				((Labeled) node).setText(god.getEffect().getMultiplierOnWho().getName());
			} else if (node.getId().equals("onWhatMult")) {
				((Labeled) node).setText(god.getEffect().getMultiplierOnWhat().getName());
			} else if (node.getId().equals("costPrice1")) {
				((Labeled) node).setText(((Byte) god.getEffect().getCosts().keySet().toArray()[0]).toString());
			} else if (node.getId().equals("costPrice2")) {
				((Labeled) node).setText(((Byte) god.getEffect().getCosts().keySet().toArray()[1]).toString());
			} else if (node.getId().equals("costPrice3")) {
				((Labeled) node).setText(((Byte) god.getEffect().getCosts().keySet().toArray()[2]).toString());
			} else if (node.getId().equals("value1")) {
				((Labeled) node).setText(((Float) god.getEffect().getCosts().values().toArray()[0]).toString());
			} else if (node.getId().equals("value2")) {
				((Labeled) node).setText(((Float) god.getEffect().getCosts().values().toArray()[1]).toString());
			} else if (node.getId().equals("value3")) {
				((Labeled) node).setText(((Float) god.getEffect().getCosts().values().toArray()[2]).toString());
			} else if (node.getId().contains("effectRank")) {
				if (!isBottom) {
					continue;
				}
				PseudoClass imageViewBorder = PseudoClass.getPseudoClass("border");
				node.getStyleClass().add("image-view-wrapper");
				
				if (selectedGodAttackRank.size() == 1) {
					if (!selectedGodAttackRank.keySet().toArray()[0].equals(godName)) {
						continue;
					}
					if ((byte) selectedGodAttackRank.values().toArray()[0]
							!= node.getId().charAt(node.getId().length()-1)-48) {
						continue;
					}
					node.pseudoClassStateChanged(imageViewBorder, true);
				}
				
				node.setOnMouseClicked(event -> {
					if (selectedGodAttackRank.size() == 1) {
						if (!selectedGodAttackRank.keySet().toArray()[0].equals(godName)) {
							return;
						}
						if ((byte) selectedGodAttackRank.values().toArray()[0]
								!= node.getId().charAt(node.getId().length()-1)-48) {
							return;
						}
						node.pseudoClassStateChanged(imageViewBorder, false);
						selectedGodAttackRank.clear();
					} else if (selectedGodAttackRank.size() == 0) {
						node.pseudoClassStateChanged(imageViewBorder, true);
						selectedGodAttackRank.put(godName
								, (byte) (node.getId().charAt(node.getId().length()-1)-48));
					}
				});
			}
		}
	}
}
