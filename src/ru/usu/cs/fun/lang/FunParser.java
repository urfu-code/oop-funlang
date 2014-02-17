package ru.usu.cs.fun.lang;

import ru.usu.cs.fun.back.Abstraction;
import ru.usu.cs.fun.back.Application;
import ru.usu.cs.fun.back.Term;
import ru.usu.cs.fun.back.Variable;
import ru.usu.cs.fun.front.AbstractLL1Parser;
import ru.usu.cs.fun.front.Lexeme;
import ru.usu.cs.fun.front.Lexer;
import ru.usu.cs.fun.front.ParseAction;

// Statement - initial nonterm
// Statement has type Term
// Term has type Terms
// Tail has type Terms

// Statement ::= 'let' name ':=' Term ';'
// Statement ::= Term ';'
// Term ::= '(' Term ')' Tail
// Term ::= Atom Tail
// Term ::= 'fun' '(' name ')' Term Tail
// Tail ::=
// Tail ::= Term
// Atom ::= name
// Atom ::= int

public class FunParser extends AbstractLL1Parser {
	private final FunScope scope;

	private static Lexer createLexer(String text) {
		return new FunLexer(text);
	}

	// последовательно выполняет все предложения из programBlock
	// возвращает результат выполнения последнего из них
	public Term eval(String programBlock) {
		Lexer lexer = createLexer(programBlock);
		Object res = executeNext(lexer);
		Object newRes;
		while ((newRes = executeNext(lexer)) != null)
			res = newRes;
		return (Term) res;
	}

	// вычитывает из лексера и выполняет одно очередное предложение
	public Term evalNext(Lexer lexer) {
		return (Term) executeNext(lexer);
	}

	private static class Terms {

		public final Term head;
		public final Terms tail;

		public Terms(Term head, Terms tail) {
			super();
			this.head = head;
			this.tail = tail;
		}

		@Override
		public String toString() {
			if (head == null) return "[]";
			if (tail == null) return head.toString();
			else return head.toString() + " " + tail.toString();
		}
		
		public Term build() {
			Term result = head;
			Terms tmpTail = tail;
			while (tmpTail != null) {
				result = new Application(result, tmpTail.head);
				tmpTail = tmpTail.tail;
			}
			return result;
		}
	}

	public FunParser() {
		this(new FunScope());
	}

	public FunParser(FunScope aScope) {
		super();
		this.scope = aScope;

		// Statement ::= 'let' name '=' Term ';' // scope.add(name, Term)
		add("Statement", new String[] { "let", "name", ":=", "Term", ";" }, new ParseAction() {
			public Term execute(Object[] items) {
				String name = ((Lexeme) items[1]).getText();
				Term term = ((Terms) items[3]).build();
				scope.add(name, term.eval(scope));
				return term;
			}
		});

		// Statement ::= Term ';' // return Term.eval(scope);
		add("Statement", new String[] { "Term", ";" }, new ParseAction() {
			public Term execute(Object[] items) {
				Term term = ((Terms) items[0]).build();
				// EvaluationLogger logger = new EvaluationLogger(term);
				Term res = term.eval(scope);
				System.out.println(res.toString());
				return res;
			}
		});

		// Term ::= '(' Term ')' Tail // return Term.apply(Tail);
		add("Term", new String[] { "(", "Term", ")", "Tail" }, new ParseAction() {
			public Terms execute(Object[] items) {
				Term termInBrackets = ((Terms) items[1]).build();
				return new Terms(termInBrackets, (Terms) items[3]);
			}
		});

		// Term ::= Atom Tail // return Atom.apply(Tail);
		add("Term", new String[] {"int", "name"}, new String[] { "Atom", "Tail" }, new ParseAction() {
			public Terms execute(Object[] items) {
				Term atom = (Term)items[0];
				return new Terms(atom, (Terms) items[1]);
			}
		});

		// Term ::= 'fun' '(' name ')' Term // return fun(name) body;
		add("Term", new String[] { "fun", "(", "name", ")", "Term" }, new ParseAction() {
			public Terms execute(Object[] items) {
				Term body = ((Terms) items[4]).build();
				String name = ((Lexeme) items[2]).getText();
				Abstraction abstraction = new Abstraction(name, name.startsWith("~"), body);
				return new Terms(abstraction, null);
			}
		});

		// Tail ::= // return null;
		add("Tail", new String[] { ";", ")", }, new String[] {}, new ParseAction() {
			public Terms execute(Object[] items) {
				return null;
			}
		});

		// Tail ::= // return Term
		add("Tail", new String[] { "Term" }, new ParseAction() {
			public Terms execute(Object[] items) {
				return (Terms) items[0];
			}
		});

		// Atom ::= name
		add("Atom", new String[] { "name" }, new ParseAction() {
			public Variable execute(Object[] items) {
				return new Variable(((Lexeme)items[0]).getText());				
			}
		});

		// Atom ::= int
		add("Atom", new String[] { "int" }, new ParseAction() {
			public Int execute(Object[] items) {
				Object value = ((Lexeme)items[0]).getValue();
				return new Int((Integer)value);				
			}
		});

		setInitial("Statement");
	}
}
