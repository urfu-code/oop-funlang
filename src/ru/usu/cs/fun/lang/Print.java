package ru.usu.cs.fun.lang;

import ru.usu.cs.fun.back.Scope;
import ru.usu.cs.fun.back.Term;
import ru.usu.cs.fun.back.TermsSubstitutor;

public class Print extends Term {

	@Override
	public String toString(TermsSubstitutor substitutor) {
		return "print";
	}

	@Override
	public Term apply(Term arg, Scope scope) {
		System.out.println(arg.eval(scope).toString());
		return Bool.TRUE;
	}
	
	

}
