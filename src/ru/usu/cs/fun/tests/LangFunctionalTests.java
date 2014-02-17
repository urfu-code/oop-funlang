package ru.usu.cs.fun.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.TestCase;
import ru.usu.cs.fun.front.Lexer;
import ru.usu.cs.fun.lang.FunLexer;
import ru.usu.cs.fun.lang.FunParser;
import ru.usu.cs.fun.lang.FunScope;

public class LangFunctionalTests extends TestCase {
	public void testChurchArithmetics() {
		runTestsSet("church-arithmetics");
	}

	public void testChurchBooleans() {
		runTestsSet("church-booleans");
	}

	public void testRecursion() {
		runTestsSet("recursion");
	}

	public void testReductionOrder() {
		runTestsSet("reduction-order");
	}

	private void runTestsSet(FunParser fun, FunScope scope, Lexer lexer, String expectedOutput) {
		Object result;
		String actualOutput = "";
		while ((result = fun.evalNext(lexer)) != null) {

			actualOutput = result.toString();
		}
		assertEquals(expectedOutput, actualOutput);

	}

	private void runTestsSet(String testssetName) {
		InputStream stream = getClass().getResourceAsStream("test-" + testssetName + ".txt");
		if (stream == null)
			throw new RuntimeException(testssetName + " is missing");
		BufferedReader r = new BufferedReader(new InputStreamReader(stream));
		String content = "";
		String line = null;
		FunScope scope = new FunScope();
		FunParser fun = new FunParser(scope);
		try {
			while ((line = r.readLine()) != null) {
				if (line.startsWith("//>")) {
					Lexer lexer = new FunLexer(content);
					try {
						runTestsSet(fun, scope, lexer, line.substring(3));
					} catch (Throwable e) {
						throw new RuntimeException("input: " + content + " -> " + e.getMessage(), e);
					}
					content = "";
				} else
					content = content + line + '\n';
			}
		} catch (IOException e) {
			throw new RuntimeException(testssetName + " read error");
		}
	}
}
