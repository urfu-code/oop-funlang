package ru.usu.cs.fun.front;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLL1Parser {

	private String initialNonterm;
	private final List<Rule> rules = new ArrayList<Rule>();

	public AbstractLL1Parser() {
		super();
	}

	protected void setInitial(String nonterm) {
		initialNonterm = nonterm;
	}

	protected void add(String nonterm, String[] firstSymbols, String[] body, ParseAction action) {
		rules.add(new Rule(nonterm, firstSymbols, body, action));
	}

	protected void add(String nonterm, String[] body, ParseAction action) {
		if (body.length > 0 && !Character.isUpperCase(body[0].charAt(0)))
			rules.add(new Rule(nonterm, new String[] { body[0] }, body, action));
		else
			rules.add(new Rule(nonterm, (String[]) null, body, action));
	}

	public Object executeNext(Lexer lexer) {
		return execute(lexer, initialNonterm);
	}

	private Object execute(Lexer lexer, String nonterm) {
		if (lexer.current() == null)
			return null;
		Rule rule = findRule(lexer.current().getType(), nonterm);
		try {
			Object[] items = new Object[rule.body.length];
			for (int i = 0; i < rule.body.length; i++) {
				String ruleItem = rule.body[i];
				if (isTerminal(ruleItem)) {
					if (lexer.current() == null)
						throw new RuntimeException(ruleItem + " expected but was end of file");
					if (ruleItem.equals(lexer.current().getType())) {
						items[i] = lexer.current();
						lexer.readNext();
					} else
						throw new RuntimeException(ruleItem + " expected but " + lexer.current().getType() + " '"+lexer.current().getText() + "' found");
				} else
					items[i] = execute(lexer, ruleItem);
			}
			return rule.executeAction(items);
		} catch (Exception e) {
			throw new RuntimeException(rule + "\r\n" + e.getMessage(), e);
		}
	}

	private boolean isTerminal(String id) {
		for (Rule r : rules) {
			if (r.nonterm.equals(id))
				return false;
		}
		return true;
	}

	private Rule findRule(String currentLexemeType, String nonterm) {
		for (Rule r : rules) {
			if (r.nonterm.equals(nonterm) && r.canUseOn(currentLexemeType))
				return r;
		}
		throw new RuntimeException(nonterm + " can't start with " + currentLexemeType);
	}

	private static class Rule {

		public final String[] body;

		public final String nonterm;

		public final String[] lookupSymbols;

		private final ParseAction action;

		public Object executeAction(Object[] items) {
			Object value = action.execute(items);
			//System.out.println("executing: " + this + " -> " + value);
			return value;
		}

		public boolean canUseOn(String currentLexemeType) {
			if (lookupSymbols == null)
				return true;
			for (String lookupSymbol : lookupSymbols) {
				if (lookupSymbol.equals(currentLexemeType))
					return true;
			}
			return false;
		}
		
		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append(nonterm).append(" ::= ");
			for (String item : body) {
				sb.append(item).append(" ");
			}	
			return sb.toString();
		}

		public Rule(String nonterm, String[] lookupSymbols, String[] body, ParseAction action) {
			super();
			this.nonterm = nonterm;
			this.lookupSymbols = lookupSymbols;
			this.body = body;
			this.action = action;
		}

	}

}
