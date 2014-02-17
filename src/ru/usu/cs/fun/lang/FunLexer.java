package ru.usu.cs.fun.lang;

import ru.usu.cs.fun.front.FunName;
import ru.usu.cs.fun.front.GenericLexer;
import ru.usu.cs.fun.front.IntNumber;
import ru.usu.cs.fun.front.Recognizer;
import ru.usu.cs.fun.front.Space;
import ru.usu.cs.fun.front.Word;

public class FunLexer extends GenericLexer {
	private static Recognizer[] recognizers;

	static {
		Recognizer let = new Word("let");
		Recognizer fun = new Word("fun");
		Recognizer eq = new Word(":=");
		Recognizer semicolon = new Word(";");
		Recognizer open = new Word("(");
		Recognizer close = new Word(")");
		Recognizer intNumber = new IntNumber();
		Recognizer name = new FunName();
		Recognizer space = new Space();
		recognizers = new Recognizer[] { let, fun, eq, semicolon, open, close, intNumber, name, space };
	}

	public FunLexer(String text) {
		super(text, recognizers);
	}

}
