package ru.usu.cs.fun.lang;

import ru.usu.cs.fun.back.Term;
import ru.usu.cs.fun.back.TermsSubstitutor;

public class Int extends Term {

	public final int value;

	public Int(int value) {
		super();
		this.value = value;
	}

	@Override
	public String toString(TermsSubstitutor subst) {
		return value + "";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj instanceof Int) {
			return value == ((Int) obj).value;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return value;
	}

	@Override
	public Term substitute(String name, Term value) {
		return this;
	}

}
