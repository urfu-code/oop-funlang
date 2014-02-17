package ru.usu.cs.fun.front;

public interface Recognizer {
	public Lexeme tryReadLexeme(String s, int startPos);
}
