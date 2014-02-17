package ru.usu.cs.fun.lang;

import ru.usu.cs.fun.back.Scope;
import ru.usu.cs.fun.back.Term;
import ru.usu.cs.fun.back.TermsSubstitutor;

public class Add extends Term {
	@Override
	public String toString(TermsSubstitutor substitutor) {
		return "+";
	}
	
	@Override
	public Term apply(Term arg, Scope scope) {
		return new AddContinuation(arg);
	}

	protected Term calculate(Term arg1, Term arg2, Scope scope) {
		int v1 = ((Int) arg1).value;
		int v2 = ((Int) arg2.eval(scope)).value;
		return new Int(v1 + v2);
	}

	private class AddContinuation extends Term {

		private Term arg1;
		private boolean evaluated;

		public AddContinuation(Term arg1) {
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
			return Add.this.toString() + ' ' + subst.substitute(arg1);
		}
	}
}
