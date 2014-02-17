package ru.usu.cs.fun.front;

import java.util.HashMap;
import java.util.Map;

public class OptimizationTable {
	private final Recognizer[] fastItems = new Recognizer[128];
	private final Map<Character, Recognizer> otherItems = new HashMap<Character, Recognizer>();

	public Recognizer put(char firstChar, Recognizer recognizer) {
		if (firstChar < 128)
			fastItems[firstChar] = recognizer;
		otherItems.put(firstChar, recognizer);
		return recognizer;
	}

	public Recognizer get(char firstChar) {
		if (firstChar < 128)
			return fastItems[firstChar];
		return otherItems.get(firstChar);
	}
}
