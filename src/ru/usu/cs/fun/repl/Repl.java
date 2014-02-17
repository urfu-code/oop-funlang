package ru.usu.cs.fun.repl;

import java.util.Scanner;

import ru.usu.cs.fun.lang.FunLexer;
import ru.usu.cs.fun.lang.FunParser;

public class Repl {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		FunParser parser = new FunParser();
		while (true) {
			String line = s.nextLine();
			if (line.equals("exit") || line.equals(":q") || line.equals("quit")) {
				System.out.println("Bye-bye!");
				return;
			}
			if (line.isEmpty())
				continue;
			if (!line.endsWith(";"))
				line = line + ";";
			try {
				parser.evalNext(new FunLexer(line));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
