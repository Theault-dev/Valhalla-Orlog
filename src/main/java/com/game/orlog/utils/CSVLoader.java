package com.game.orlog.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Simple Java program to read CSV file in Java.
 * 
 * @author Theault & Titouan
 *
 */
public class CSVLoader {
	private static List<String> csvFile;
	private static String path = "/com/game/orlog/csv/localisation.csv";
	private static String lineSeparator = "\n";
	private static String fieldSeparator = "\",\"";

	/**
	 * Load the CSVFile in the memory
	 */
	public void loadCSV() {
		try {
			Scanner sc = new Scanner(
					new File(
							CSVLoader.class.getResource(path).getPath()));
			sc.useDelimiter(lineSeparator);
			csvFile = sc.tokens().toList();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Give a dictionnary which contains all the words usable in the application's
	 * elements' text
	 * 
	 * @param attributeId The shortcut for the language.
	 * 		Example : "en" for english.
	 * @return
	 */
	public HashMap<String, String> getDictionnaryValues(
			String attributeId) {
		HashMap<String, String> dictionnary = new HashMap<String, String>();

		int attributeIndex = -1;

		String[] headers = csvFile.get(0).split(fieldSeparator);

		for (int i = 0; i < headers.length; i++) {
			if (headers[i].contains(attributeId)) {
				attributeIndex = i;
				break;
			}
		}

		for (int i = 1; i < csvFile.size(); i++) {
			String line = csvFile.get(i);

			line = line.substring(1, line.lastIndexOf('"'));

			String[] fields = line.split(fieldSeparator);

			if (fields.length > attributeIndex) {
				for (int j = 0; j < fields.length; j++) {
					fields[j] = fields[j].trim();
				}

				String key = fields[0];
				if (dictionnary.containsKey(key)) {
					continue;
				}
				String value = fields[attributeIndex];
				dictionnary.put(key, value);
			}
		}

		return dictionnary;
	}
}
