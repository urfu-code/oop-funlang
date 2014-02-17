package ru.usu.cs.fun.front;

public class Space extends AutomatonRecognizer {

	public Space() {
		super("space", true);
		transition(INITIAL_STATE, " ", INITIAL_STATE);
		finalStates(INITIAL_STATE);
	}
	@Override
	protected String getCharClass(char ch) {
		return (ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n') ? " " : "*";
	}

}
