package ru.usu.cs.fun.back;

public class Application extends Term {

	private final Term fun, arg;

	public Application(Term fun, Term arg) {
		super();
		this.fun = fun;
		this.arg = arg;
	}

	@Override
	public Term eval(Scope scope) {
		Term evaluatedFun = fun.eval(scope);
		Term afterApplication = evaluatedFun.apply(arg, scope);
		if (afterApplication == null)
			return this;
		return afterApplication.eval(scope);
	}

	@Override
	public Term substitute(String name, Term value) {
		Term newFun = fun.substitute(name, value);
		Term newArg = arg.substitute(name, value);
		if (newFun == fun && newArg == arg)
			return this;
		return new Application(newFun, newArg);
	}

	@Override
	public Term apply(Term arg, Scope scope) {
		Term evaluted = eval(scope);
		if (evaluted == this)
			return null;
		return evaluted.apply(arg, scope);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return arg.hashCode() + prime * fun.hashCode();
	}

	@Override
	public boolean parenthesizeAsArg() {
		return true;
	}

	@Override
	public String toString(TermsSubstitutor subst) {
		Term a = subst.substitute(arg);
		String sArg = a.toString(subst);
		if (a.parenthesizeAsArg())
			sArg = "(" + sArg + ")";
		Term f = subst.substitute(fun);
		String sFun = f.toString(subst);
		if (f.parenthesizeAsFun())
			sFun = "(" + sFun + ")";
		return sFun + " " + sArg;
	}

}
