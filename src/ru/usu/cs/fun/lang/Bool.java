package ru.usu.cs.fun.lang;

import ru.usu.cs.fun.back.Abstraction;
import ru.usu.cs.fun.back.TermsSubstitutor;
import ru.usu.cs.fun.back.Variable;

public class Bool extends Abstraction {

	public static final Bool TRUE = new Bool("true");
	public static final Bool FALSE = new Bool("false");

	public static Bool from(boolean value) {
		return value ? TRUE : FALSE;
	}

	private final String string;

	private Bool(String result) {
		super("true", true, new Abstraction("false", true, new Variable(result)));
		this.string = result;
	}

	@Override
	public boolean parenthesizeAsFun() {
		return false;
	}

	@Override
	public boolean parenthesizeAsArg() {
		return false;
	}

	@Override
	public String toString(TermsSubstitutor subst) {
		return string;
	}

}
