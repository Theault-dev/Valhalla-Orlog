package com.game.orlog.utils;

import java.util.ArrayList;
import java.util.HashMap;

import com.game.orlog.ValhallaOrlogApplication;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Labeled;

/**
 * Localisation class with all the static features that a good
 * localisation must have.
 * 
 * @author Theault & Titouan
 *
 */
public class LocalisationSystem {
	/**
	 * Enumeration with all the different language available in your
	 * dictionnary file.
	 * Must be edited mannually when you want to add a new language.
	 * 
	 * @author Theault & Titouan
	 *
	 */
	public enum Language {
		ENGLISH,
		FRENCH;
	}
	
	/**
	 * Current language used.
	 */
	public static Language language = Language.ENGLISH;
	
	private static HashMap<String, String> localisedEN;
	private static HashMap<String, String> localisedFR;
	private static boolean isInit;

	/**
	 * Load the dictionnary. Need to be called only once.
	 */
	private static void init() {
		CSVLoader csvLoader = new CSVLoader();
		csvLoader.loadCSV();
		
		localisedEN = csvLoader.getDictionnaryValues("en");
		localisedFR = csvLoader.getDictionnaryValues("fr");
		
		isInit = true;
	}
	
	/**
	 * Get the corresponding value from the dictionnary with they key
	 * given in parameter.
	 * 
	 * @param key The key from the dictionnary file.
	 * @return The value corresponding with the current language
	 * and the key.
	 */
	public static String getLocalisedValue(String key) {
		if (!isInit) {
			init();
		}
		String value = key;
		switch (language) {
		case FRENCH:
			value = localisedFR.get(key);
			break;
		case ENGLISH:
		default:
			value = localisedEN.get(key);
			break;
		}
		
		if (value == null) {
			value = key;
		}
		
		return value;
	}

	/**
	 * Load a list with the children of a given parent.
	 * 
	 * @param parent The parent from the FXML file
	 * @param nodes The node list which will be updated
	 */
	public static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
		for (Node node : parent.getChildrenUnmodifiable()) {
			nodes.add(node);
			if (node instanceof Parent) {
				addAllDescendents((Parent) node, nodes);
			}
		}
	}
	
	/**
	 * Change the current view language.
	 */
	public static void changeUILanguage() {
		ArrayList<Node> nodes = new ArrayList<>();
		Parent root = ValhallaOrlogApplication.getStage().getScene().rootProperty().get();
		
		addAllDescendents(root, nodes);
		
		for (Node node : nodes) {
			if (node.getId() == null || !(node instanceof Labeled)) {
				continue;
			}
			((Labeled) node).setText(LocalisationSystem
					.getLocalisedValue(node.getId()));
		}
	}
	
	/**
	 * Change a view language given in parameter
	 * @param root The view with the elements that need to be updated.
	 */
	public static void changeViewLanguage(Parent root) {
		ArrayList<Node> nodes = new ArrayList<>();		
		addAllDescendents(root, nodes);
		
		for (Node node : nodes) {
			if (node.getId() == null || !(node instanceof Labeled)) {
				continue;
			}
			((Labeled) node).setText(LocalisationSystem
					.getLocalisedValue(node.getId()));
		}
	}
}
