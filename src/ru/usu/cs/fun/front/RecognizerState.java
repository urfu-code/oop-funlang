package ru.usu.cs.fun.front;

public class RecognizerState {
	private final String name;

	private RecognizerState(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static final RecognizerState alive = new RecognizerState("alive");
	public static final RecognizerState finished = new RecognizerState("finished");
	public static final RecognizerState dead = new RecognizerState("dead");
}
