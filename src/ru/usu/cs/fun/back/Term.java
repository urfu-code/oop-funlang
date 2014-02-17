package ru.usu.cs.fun.back;

// Класс представляющий Лямбда выражение.
public abstract class Term {

	// Проводит редукцию выражения до нормальной формы.
	// Другими словами вычисляет выражение.
	public Term eval(Scope scope) {
		return this;
	}

	// Подставляет вместо всех свободных вхождений
	// переменной name выражение value.
	public Term substitute(String name, Term value) {
		return this;
	}

	// substitutor может содержать информацию об уже проведенных редукциях.
	// toString должен эту информацию учитывать.
	public abstract String toString(TermsSubstitutor substitutor);

	@Override
	public final String toString() {
		return toString(TermsSubstitutor.empty);
	}

	// Надо ли при переводе в строку заключать данный терм в скобки,
	// если он встретился в качестве функции (т.е. левой части Application-а)
	// Например, подвыражение "fun(x) y" нужно заключать в скобки
	// в выражении "(fun(x) y) 5"
	// иначе его можно будте проинтерпретировать как "fun(x) (y 5)".
	public boolean parenthesizeAsFun() {
		return false;
	}

	// Надо ли при переводе в строку заключать данный терм в скобки,
	// если он встретился в качестве аргумента (т.е. правой части Application-а)
	// Например, подвыражение "y z" нужно заключать в скобки
	// в выражении "x (y z)"
	// иначе его можно будте проинтерпретировать как "(x y) z".
	public boolean parenthesizeAsArg() {
		return false;
	}

	// Попытка вызвать текущий терм как функцию, передав в нее аргумент arg.
	// Если применение не приводит ни к одной редукции,
	// то метод возвращает null, что означает "применение невозможно".
	public Term apply(Term arg, Scope scope) {
		return null;
	}
}
