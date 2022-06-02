package com.game.orlog.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Utils {
	public interface GeneralEnum {
		String getName();
	}
	public static enum When implements GeneralEnum {
	    BEFORE("apres_resolution"),
	    AFTER("avant_resolution"),
	    DEPEND("faveur_divine");
		private String name;
		private When(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	};
	public static enum OnWho implements GeneralEnum {
		PLAYER("joueur"),
		OPPONENT("adversaire"),
		CHOOSE("au_choix");
		private String name;
		private OnWho(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}
	public static enum Affect implements GeneralEnum {
	    HEALTH_TOKEN("jetons_santé"),
	    POWER_TOKEN("jetons_pouvoir"),
	    HELMET("casques"),
	    SHIELD("boucliers"),
	    AXE("haches"),
	    DIE("dés"),
	    DIVINE_FAVOR("faveur_divine"),
	    ARROW("flèches");
		private String name;
		private Affect(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	};
	public static enum Offering implements GeneralEnum {
		NONE(""),
		HEALTH_TOKEN("jetons_santé");
		private String name;
		private Offering(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}
	public static enum MultiplierOnWho implements GeneralEnum {
		NONE(""),
		PLAYER("joueur"),
		OPPONENT("adversaire");
		private String name;
		private MultiplierOnWho(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}
	public static enum MultiplierOnWhat implements GeneralEnum {
		NONE(""),
		AXE_DAMAGE("dégâts_hache"),
		BLOCKING("blocage"),
		AXE("haches"),
		POWER_TOKEN("jetons_pouvoir"),
		ARROW("flèches"),
		TAKEN_DAMAGE("dégâts_subis"),
		HAND("main");
		private String name;
		private MultiplierOnWhat(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}
	
	/**
	 * Read a JSONFile givin in parameter
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
}
