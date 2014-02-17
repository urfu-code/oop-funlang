package ru.usu.cs.fun.front;

public class FunName extends AutomatonRecognizer {

	public FunName() {
		super("name");
		transition(INITIAL_STATE, "abc", "abc");
		transition("abc", "abc", "abc");
		finalStates("abc");
	}
	
	protected String getCharClass(char ch){
		boolean alpha = (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
		boolean num = ch >= '0' && ch <= '9';
		boolean symbols = "+-*/%=!&|_~".indexOf(ch) >= 0;
		return (alpha || symbols || num) ? "abc" : "*";
	}
}
