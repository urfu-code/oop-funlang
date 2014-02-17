package ru.usu.cs.fun.front;


public class GenericLexer implements Lexer {
	private final Recognizer[] recognizers;
	private final String text;
	private Lexeme currentLexeme;
	private int pos;

	public GenericLexer(String text, Recognizer... recognizers) {
		this.text = text;
		this.pos = 0;
		this.recognizers = recognizers;
		readNext();
	}

	@Override
	public Lexeme current() {
		return currentLexeme;
	}

	@Override
	public Lexeme readNext() {
		currentLexeme = null;
		if (pos >= text.length()) return null;
		Lexeme bestLexeme = null;
		for (Recognizer r : recognizers) {
			Lexeme lexeme = r.tryReadLexeme(text, pos);
			if (bestLexeme == null || lexeme != null && bestLexeme.getText().length() < lexeme.getText().length())
				bestLexeme = lexeme;
		}
		if (bestLexeme == null) {
			String snippet = text.substring(pos, Math.min(text.length(), 10));
			throw new RuntimeException("Can't read lexeme starting from [" + snippet + "]");
		}
		
		currentLexeme = bestLexeme;
		pos += bestLexeme.getText().length();
		if (currentLexeme instanceof Lexeme.Invisible)
			return readNext();
		else
			return currentLexeme;
	}
}
