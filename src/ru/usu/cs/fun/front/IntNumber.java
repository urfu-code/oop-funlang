package ru.usu.cs.fun.front;

public class IntNumber extends AutomatonRecognizer {
	public IntNumber() {
		super("int");
		transition(INITIAL_STATE, "-", "-");
		transition("-", "9", "-9");
		transition("-9", "9", "-9");
		transition(INITIAL_STATE, "9", "-9");
		finalStates("-9");
	}
	
	@Override
	protected String getCharClass(char ch) {
		if ('0' <= ch && ch <= '9') return "9";
		if (ch == '+' || ch == '-') return "-";
		return "#";
	}
	
	@Override
	protected Integer getValue(String text) {
		return Integer.parseInt(text);
	}
}
