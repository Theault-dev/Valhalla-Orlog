package com.game.orlog.utils;

import java.io.IOException;

import com.game.orlog.ValhallaOrlogApplication;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Popup;
import javafx.stage.WindowEvent;

/**
 * A static class to create a new popup window.
 * 
 * @author Theault & Titouan
 *
 */
public class PopupWindow {
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
	 * @param stage The stage from the application.
	 */
	public static void showPopupMessage(final String FXMLFile
			, final Parent root) {
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
		System.out.println(popup.getWidth());
	}
}
