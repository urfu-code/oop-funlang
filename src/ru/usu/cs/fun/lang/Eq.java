package ru.usu.cs.fun.lang;

import ru.usu.cs.fun.back.Scope;
import ru.usu.cs.fun.back.Term;
import ru.usu.cs.fun.back.TermsSubstitutor;

public class Eq extends Term {

	@Override
	public String toString(TermsSubstitutor substitutor) {
		return "=";
	}

	@Override
	public Term apply(Term arg, Scope scope) {
		return new EqContinuation(arg);
	}

	protected Term calculate(Term arg1, Term arg2, Scope scope) {
		return Bool.from(arg1.eval(scope).equals(arg2.eval(scope)));
	}
	
	private class EqContinuation extends Term {

		private Term arg1;
		private boolean evaluated;

		public EqContinuation(Term arg1) {
			this.arg1 = arg1;
		}

		@Override
		public Term apply(Term arg2, Scope scope) {
			if (!evaluated) {
				arg1 = arg1.eval(scope);
				evaluated = true;
			}
			return calculate(arg1, arg2, scope);
		}

		@Override
		public String toString(TermsSubstitutor subst) {
			return Eq.this.toString() + ' ' + subst.substitute(arg1);
		}
	}
}
