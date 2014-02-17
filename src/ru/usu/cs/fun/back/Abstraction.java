package ru.usu.cs.fun.back;

public class Abstraction extends Term {

	private final String argName;
	private final Boolean lazyArg;
	private final Term body;

	public Abstraction(String argName, Boolean lazyArg, Term body) {
		super();
		this.argName = argName;
		this.lazyArg = lazyArg;
		this.body = body;
	}

	@Override
	public Term apply(Term arg, Scope scope) {
		if (!lazyArg) arg = arg.eval(scope);
		return body.substitute(argName, arg);
	}

	@Override
	public Term eval(Scope scope) {
		return this;
	}

	@Override
	public Term substitute(String name, Term value) {
		if (name == argName)
			return this;
		String newArgName = argName;
		Term newBody = body;
		if (value != value.substitute(argName, new Variable(""))) {
			// argName встречается в выражении value в качестве свободной
			// переменной. Надо менять имя аргумента, иначе будет путаница.
			newArgName = newName();
			newBody = body.substitute(argName, new Variable(newArgName));
		}
		return new Abstraction(newArgName, lazyArg, newBody.substitute(name, value));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return argName.hashCode() + prime * body.hashCode();
	}

	@Override
	public boolean parenthesizeAsArg() {
		return true;
	}

	@Override
	public boolean parenthesizeAsFun() {
		return true;
	}

	private static String newName() {
		return "$" + lastGeneratedNameIndex++;
	}

	private static int lastGeneratedNameIndex = 0;

	@Override
	public String toString(TermsSubstitutor substitutor) {
		return "fun(" + argName + ") " + substitutor.substitute(body).toString();
	}
}
