package com.game.orlog.model.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.game.orlog.model.entity.Divinity;
import com.game.orlog.model.enumClass.ActionEnum;
import com.game.orlog.model.enumClass.AffectEnum;
import com.game.orlog.model.enumClass.GamePhaseEnum;
import com.game.orlog.model.enumClass.GeneralEnum;
import com.game.orlog.model.enumClass.OfferingEnum;
import com.game.orlog.model.enumClass.OnWhatEnum;
import com.game.orlog.model.enumClass.OnWhoEnum;
import com.game.orlog.model.items.Face;

public class Utils {
	private static ArrayList<Divinity> ALL_DIVINITIES = null;
	
	public static Face HAND = new Face(false, ActionEnum.HAND);
	public static Face ARROW = new Face(false, ActionEnum.ARROW);
	public static Face AXE = new Face(false, ActionEnum.AXE);
	public static Face HELMET = new Face(false, ActionEnum.HELMET);
	public static Face SHIELD = new Face(false, ActionEnum.SHIELD);
	public static Face HAND_SPE = new Face(true, ActionEnum.HAND);
	public static Face ARROW_SPE = new Face(true, ActionEnum.ARROW);
	public static Face AXE_SPE = new Face(true, ActionEnum.AXE);
	public static Face HELMET_SPE = new Face(true, ActionEnum.HELMET);
	public static Face SHIELD_SPE = new Face(true, ActionEnum.SHIELD);
	
	public static ArrayList<Divinity> getAllDivinities(){
		if (ALL_DIVINITIES == null) {
			ALL_DIVINITIES = new ArrayList<Divinity>();
			File file =
				new File("src/main/resources/json/Orlog_invocation_all.json");
			JSONArray array = Utils.readJSONFile(file);
			
			for (int i = 0; i < array.size(); i++) {
				Effect effect = parseJSONArray((JSONObject) array.get(i));
				String name = effect.getName().split("[\\s']")
						[effect.getName().split("[\\s']").length-1];
				
				ALL_DIVINITIES.add(new Divinity(name , effect));
			}
		}
		return ALL_DIVINITIES;
	}
	
	/**
	 * Read a JSONFile given in parameter
	 * @param file JSON file
	 * @return JSONArray if correctly parsed, null otherwise
	 */
	public static JSONArray readJSONFile(File file) {
		JSONParser jsonParser = new JSONParser();
		
		try {
			FileReader reader = new FileReader(file);
			return (JSONArray) jsonParser.parse(reader);
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return null;
	}
	/**
	 * Parse a JSONArray into elements and return an Effect
	 * @param element
	 * @return an Instance of Effect
	 * @see Effect
	 */
	@SuppressWarnings({ "unchecked" })
	private static Effect parseJSONArray(JSONObject element) {
		String tmp;
		ArrayList<String> tmpArray;
		JSONObject tmpObject;
		ArrayList<JSONObject> tmpArrayObject;
		
		String name = (String) element.get("nom");
		String description = (String) element.get("effet");
		
		GamePhaseEnum when = null;
		tmp = (String) element.get("quand");
		when = (GamePhaseEnum) forEachEnum(GamePhaseEnum.class, tmp);
		
		OnWhoEnum onWho = null;
		tmp = (String) element.get("sur_qui");
		onWho = (OnWhoEnum) forEachEnum(OnWhoEnum.class, tmp);
		
		ArrayList<AffectEnum> affects = new ArrayList<AffectEnum>();
		try {
			tmp = (String) element.get("affecte");
			affects.add((AffectEnum) forEachEnum(AffectEnum.class, tmp));
		} catch(ClassCastException e) {
			tmpArray = (ArrayList<String>) element.get("affecte");
			for(String str : tmpArray) {
				affects.add((AffectEnum) forEachEnum(AffectEnum.class, str));
			}
		}
		
		OfferingEnum offering = null;
		tmp = (String) element.get("sacrifice");
		offering = (OfferingEnum) forEachEnum(OfferingEnum.class, tmp);
		if(offering == null) {
			offering = OfferingEnum.NONE;
		}
		
		OnWhoEnum multiplierOnWho = null;
		tmpObject = (JSONObject) element.get("multiplicateur");
		try {
			tmp = (String) tmpObject.get("qui");
			multiplierOnWho =
					(OnWhoEnum) forEachEnum(OnWhoEnum.class, tmp);
		} catch (NullPointerException e) {
			multiplierOnWho = OnWhoEnum.NONE;
		}

		OnWhatEnum multiplierOnWhat = null;
		try {
			tmp = (String) tmpObject.get("quoi");
			multiplierOnWhat =
					(OnWhatEnum) forEachEnum(OnWhatEnum.class, tmp);
		} catch (NullPointerException e) {
			multiplierOnWhat = OnWhatEnum.NONE;
		}
		
		HashMap<Byte, Float> costs = new HashMap<>();
		tmpArrayObject = (JSONArray) element.get("couts");
		for(JSONObject obj : tmpArrayObject) {
			byte cost = ((Long)obj.values().toArray()[1]).byteValue();
			float value;
			try {
				value = ((Long)obj.values().toArray()[0]).floatValue();
			} catch (ClassCastException e) {
				value = ((Double)obj.values().toArray()[0]).floatValue();
			}
			costs.put(cost, value);
		}
		
		return new Effect(name, description, when, onWho, affects,
				offering, multiplierOnWho, multiplierOnWhat, costs);
	}
	/**
	 * Compare all elements of a given enum class with the given String
	 * @param c Name of the enum Class
	 * @param comparingTo Name of the enum element
	 * @return Element of the enum
	 */
	@SuppressWarnings("unchecked")
	private static GeneralEnum forEachEnum(@SuppressWarnings("rawtypes") Class c,
			String comparingTo) {
		if (c.isEnum()) {
			for(Object element : EnumSet.allOf(c)) {
				if (((GeneralEnum)element).getName().equals(comparingTo)) {
					return (GeneralEnum) element;
				}
			}
		}
		return null;
	}

}
