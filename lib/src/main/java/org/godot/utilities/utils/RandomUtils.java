package org.godot.utilities.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Random;

public final class RandomUtils {

	/**
	 * System random
	 */
	private static Random random;

	/**
	 * Get current system random
	 *
	 * @return Get random instance
	 */
	public static Random getSystemRandom() {
		// Check if random is null
		if (random == null) {
			Calendar cl = Calendar.getInstance();
			random = new Random(cl.getTimeInMillis());
		}
		return random;
	}

	/* ------------------------------------------------------------------
	 *
	 * Methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Generate random string
	 *
	 * @param total Total bytes of data
	 * @return Returns a random string
	 */
	@Contract("_ -> new")
	public static @NotNull String getRandomString(int total) {
		// Create limits
		int[] limits = Arr.intOf(48, 122);
		// Generate string
		return getSystemRandom().ints(limits[0], limits[1] + 1)
			.filter(n -> (n <= 57 || n >= 65) && (n <= 90 || n >= 97))
			.limit(total)
			.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			.toString();
	}

	/**
	 * Generate random string
	 *
	 * @return Returns a random string
	 */
	public static @NotNull String getRandomString() {
		return getRandomString(10);
	}

}
