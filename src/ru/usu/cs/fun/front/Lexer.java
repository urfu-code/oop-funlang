package ru.usu.cs.fun.front;

public interface Lexer {
	// при каждом новом вызове возвращает очередную прочитанную лексему
	// если лексем больше нет — возвращает null
	public Lexeme readNext();

	// последняя прочитанная лексема, либо null, если больше лексем нет
	public Lexeme current();
}
