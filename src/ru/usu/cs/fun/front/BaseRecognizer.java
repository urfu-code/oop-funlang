package ru.usu.cs.fun.front;

public abstract class BaseRecognizer implements Recognizer {
	protected final String lexemeType;
	protected final boolean invisibleLexeme;
	
	public BaseRecognizer(String lexemeType) {
		this(lexemeType, false);
	}	

	public BaseRecognizer(String lexemeType, boolean invisibleLexeme) {
		super();
		this.lexemeType = lexemeType;
		this.invisibleLexeme = invisibleLexeme;
	}

	@Override
	public abstract Lexeme tryReadLexeme(String s, int startPos);

}
