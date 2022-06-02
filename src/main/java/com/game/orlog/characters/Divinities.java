package com.game.orlog.characters;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.game.orlog.Effect;
import com.game.orlog.utils.Utils;
import com.game.orlog.utils.Utils.Affect;
import com.game.orlog.utils.Utils.GeneralEnum;
import com.game.orlog.utils.Utils.MultiplierOnWhat;
import com.game.orlog.utils.Utils.MultiplierOnWho;
import com.game.orlog.utils.Utils.Offering;
import com.game.orlog.utils.Utils.OnWho;
import com.game.orlog.utils.Utils.When;

public class Divinities {
	public ArrayList<Effect> divinities;
	
	/**
	 * Construct an instance of Divinities
	 */
	@SuppressWarnings("unchecked")
	public Divinities() {
		File file =
			new File("src/main/resources/json/Orlog_invocation_all.json");
		JSONArray array = Utils.readJSONFile(file);
		
		array.forEach( element ->
			divinities.add(parseJSONArray((JSONObject) element))
		);
	}
	
	/**
	 * Parse a JSONArray into elements and return an Effect
	 * @param element
	 * @return an Instance of Effect
	 * @see Effect
	 */
	@SuppressWarnings({ "unchecked" })
	private Effect parseJSONArray(JSONObject element) {
		String tmp;
		ArrayList<String> tmpArray;
		JSONObject tmpObject;
		ArrayList<JSONObject> tmpArrayObject;
		
		String name = (String) element.get("name");
		String description = (String) element.get("effet");
		
		When when = null;
		tmp = (String) element.get("quand");
		when = (When) forEachEnum(When.class, tmp);
		
		OnWho onWho = null;
		tmp = (String) element.get("sur_qui");
		onWho = (OnWho) forEachEnum(OnWho.class, tmp);
		
		ArrayList<Affect> affects = new ArrayList<Utils.Affect>();
		try {
			tmp = (String) element.get("affecte");
			affects.add((Affect) forEachEnum(Affect.class, tmp));
		} catch(ClassCastException e) {
			tmpArray = (ArrayList<String>) element.get("affecte");
			for(String str : tmpArray) {
				affects.add((Affect) forEachEnum(Affect.class, str));
			}
		}
		
		Offering offering = null;
		tmp = (String) element.get("sacrifice");
		offering = (Offering) forEachEnum(Offering.class, tmp);
		if(offering == null) {
			offering = Offering.NONE;
		}
		
		MultiplierOnWho multiplierOnWho = null;
		tmpObject = (JSONObject) element.get("multiplicateur");
		try {
			tmp = (String) tmpObject.get("qui");
			multiplierOnWho =
					(MultiplierOnWho) forEachEnum(MultiplierOnWho.class, tmp);
		} catch (NullPointerException e) {
			multiplierOnWho = MultiplierOnWho.NONE;
		}

		MultiplierOnWhat multiplierOnWhat = null;
		try {
			tmp = (String) tmpObject.get("quoi");
			multiplierOnWhat =
					(MultiplierOnWhat) forEachEnum(MultiplierOnWhat.class, tmp);
		} catch (NullPointerException e) {
			multiplierOnWho = MultiplierOnWho.NONE;
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
	private GeneralEnum forEachEnum(@SuppressWarnings("rawtypes") Class c,
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
