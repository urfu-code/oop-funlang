package ru.usu.cs.fun.front;

import java.util.HashSet;
import java.util.Hashtable;


public abstract class AutomatonRecognizer extends BaseRecognizer {

	public static final String ERROR = null;
	public static final String INITIAL_STATE = "";

	private Hashtable<String, Hashtable<String, String>> stateCharState = new Hashtable<String, Hashtable<String,String>>();
	private Hashtable<String, String> defaultTransitions = new Hashtable<String, String>();
	private HashSet<String> isFinal = new HashSet<String>();

	public AutomatonRecognizer(String lexemeType) {
		super(lexemeType);
	}

	public AutomatonRecognizer(String lexemeType, boolean invisible) {
		super(lexemeType, invisible);
	}
	
	@Override
	public Lexeme tryReadLexeme(String s, int startPos) {
		onStartLexeme();
		String state = INITIAL_STATE;
		int lastFinalPos = -1;
		for(int pos=startPos; pos<s.length(); pos++){
			char ch = s.charAt(pos);
			String newState = nextState(state, ch);
			if (newState == ERROR) 
				break;
			if (isFinal.contains(newState))
				lastFinalPos = pos;
			onNextState(ch, state, newState);
			state = newState;
		}
		if (lastFinalPos < 0) 
			return null;
		return createLexeme(s.substring(startPos, lastFinalPos+1));
	}
	
	/**
	 * For overriding. Called before start recognizing next lexeme
	 */
	protected void onStartLexeme() {
//		System.out.println("start reading " + lexemeType);
	}

	/**
	 * For overriding. Called on every transition oldState --(ch)--> newState
	 */
	protected void onNextState(char ch, String oldState, String newState) {		
//		System.out.println(oldState + " - " + ch + " -> " + newState);
	}

	protected Lexeme createLexeme(String s) {
		if (invisibleLexeme)
			return new Lexeme.Invisible(s, lexemeType);
		return new Lexeme(s, lexemeType, getValue(s));
	}

	/**
	 * For overriding. Called after lexeme is recognized
	 * @return value for Lexeme.value
	 */
	protected Object getValue(String text) {
		return text;
	}

	private String nextState(String currentState, char ch) {
		if (currentState == ERROR) return ERROR;
		String newState = null;
		String charClass = getCharClass(ch);
		Hashtable<String, String> transitions = stateCharState.get(currentState);
		if (transitions != null)
			newState = transitions.get(charClass);
		if (newState == null)
			newState = defaultTransitions.get(currentState);
		return newState;
	}

	protected abstract String getCharClass(char ch);

	/**
	 * Add transition: from -> to, by character from class named chars 
	 */
	protected void transition(String from, String chars, String to) {
		if (!stateCharState.containsKey(from))
			stateCharState.put(from, new Hashtable<String, String>());
		stateCharState.get(from).put(chars, to);
	}

	/**
	 * Add transition: from -> to, by all other chars 
	 * (i.e. not specified by transition(from, chars, to))
	 */
	protected void transition(String from, String to) {
		defaultTransitions.put(from,  to);
	}

	/**
	 *  Mark some states as final
	 */
	protected void finalStates(String... finals) {
		for (String s : finals) {
			isFinal.add(s);
		}
	}
}
