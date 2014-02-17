package ru.usu.cs.fun.front;

public class Word extends BaseRecognizer {
	private final String word;

	public Word(String lexemeType, String word) {
		super(lexemeType);
		this.word = word;
	}

	public Word(String word) {
		this(word, word);
	}

	@Override
	public Lexeme tryReadLexeme(String text, int startPos) {
		if (text.startsWith(word, startPos))
			return new Lexeme(word, lexemeType, word);
		return null;
	}
}
