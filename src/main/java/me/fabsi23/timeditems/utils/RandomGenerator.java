package me.fabsi23.timeditems.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {

	private static final ThreadLocalRandom random = ThreadLocalRandom.current();

	// to is excluded
	public static int randomInt(int from, int to) {
		if (from == to)
			return from;
		return from + random.nextInt(to - from);
	}

	public static double randomDouble(double from, double to) {
		if (from == to)
			return from;
		return random.nextDouble(from, to);
	}
}
