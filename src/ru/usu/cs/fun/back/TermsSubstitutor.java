package ru.usu.cs.fun.back;

public interface TermsSubstitutor {
	public Term substitute(Term originalTerm);

	public static TermsSubstitutor empty = new TermsSubstitutor() {

		@Override
		public Term substitute(Term originalTerm) {
			return originalTerm;
		}

	};

}
