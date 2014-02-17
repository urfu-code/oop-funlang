package ru.usu.cs.fun.front;

public class Lexeme {
	private final String text;
	private final String type;
	private final Object value;

	public static class Invisible extends Lexeme {
		public Invisible(String text, String type) {
			super(text, type, null);
		}
	}

	public Lexeme(String text, String type, Object value) {
		this.text = text;
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return "(" + getText() + ", " + getType() + ")";
	}

}
