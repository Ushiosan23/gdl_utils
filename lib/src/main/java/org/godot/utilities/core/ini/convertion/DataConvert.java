package org.godot.utilities.core.ini.convertion;

import org.godot.utilities.core.error.InvalidTypeValueException;
import org.godot.utilities.utils.Arr;
import org.godot.utilities.utils.MapUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class DataConvert {

	/**
	 * This class cannot be instantiated.
	 */
	private DataConvert() {
	}

	/* ------------------------------------------------------------------
	 *
	 * Properties
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Default value separators
	 */
	public static final Map<String, String> ValueSeparators = MapUtils.of(
		MapUtils.entry("listSeparator", ","),
		MapUtils.entry("assignSeparator", "=")
	);

	/**
	 * All valid boolean values
	 */
	public static final Map<String, String[]> BooleanValidValues = MapUtils.of(
		MapUtils.entry("trueValues", Arr.of("yes", "y", "true", "1")),
		MapUtils.entry("falseValues", Arr.of("no", "n", "false", "0"))
	);

	/**
	 * List delimiters
	 */
	public static final Map<String, String> ListDelimiters = MapUtils.of(
		MapUtils.entry("start", "["),
		MapUtils.entry("end", "]")
	);

	/**
	 * Map delimiters
	 */
	public static final Map<String, String> MapDelimiters = MapUtils.of(
		MapUtils.entry("start", "{"),
		MapUtils.entry("end", "}")
	);

	/* ------------------------------------------------------------------
	 *
	 * Methods
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Clean string quotes
	 *
	 * @param data Target data to clean
	 * @return Cleaned data
	 */
	public static @NotNull String cleanString(@NotNull String data) {
		return data.startsWith("\"") && data.endsWith("\"") ? data.substring(1, data.length() - 1) : data;
	}

	/**
	 * Convert string to boolean.
	 *
	 * @param data Target data to convert
	 * @return Return a valid boolean value
	 * @throws InvalidTypeValueException Error if data is not a valid boolean value
	 */
	public static boolean parseBoolean(@NotNull String data) throws InvalidTypeValueException {
		// Convert to lower
		data = data.trim().toLowerCase();
		// Check if exists in valid values
		int trueIdx = Arr.indexOf(BooleanValidValues.get("trueValues"), data);
		int falseIdx = Arr.indexOf(BooleanValidValues.get("falseValues"), data);
		// Check if exists in any of two
		if (trueIdx == -1 && falseIdx == -1)
			throw new InvalidTypeValueException("Invalid boolean data.");
		// Determine boolean result
		return (trueIdx != -1) && falseIdx == -1;
	}

	/**
	 * Convert string data to valid byte
	 *
	 * @param data Target data to convert
	 * @return Return a byte value
	 * @throws InvalidTypeValueException Error if data is not a valid byte number
	 */
	public static byte parseByte(@NotNull String data) throws InvalidTypeValueException {
		// Clean data
		data = data.trim();
		byte result;
		// Check for errors
		try {
			result = Byte.parseByte(data);
		} catch (NumberFormatException err) {
			throw new InvalidTypeValueException(err);
		}
		return result;
	}

	/**
	 * Convert string data to valid short
	 *
	 * @param data Target data to convert
	 * @return Return a short value
	 * @throws InvalidTypeValueException Error if data is not a valid short number
	 */
	public static short parseShort(@NotNull String data) throws InvalidTypeValueException {
		// Clean data
		data = data.trim();
		short result;
		// Check for errors
		try {
			result = Short.parseShort(data);
		} catch (NumberFormatException err) {
			throw new InvalidTypeValueException(err);
		}

		return result;
	}

	/**
	 * Convert string data to valid integer
	 *
	 * @param data Target data to convert
	 * @return Return an integer value
	 * @throws InvalidTypeValueException Error if data is not a valid number
	 */
	public static int parseInt(@NotNull String data) throws InvalidTypeValueException {
		// Clean data
		data = data.trim();
		int result;
		// Check for errors
		try {
			result = Integer.parseInt(data);
		} catch (NumberFormatException err) {
			throw new InvalidTypeValueException(err);
		}

		return result;
	}

	/**
	 * Convert string data to valid float
	 *
	 * @param data Target data to convert
	 * @return Return a float value
	 * @throws InvalidTypeValueException Error if data is not a valid float number
	 */
	public static float parseFloat(@NotNull String data) throws InvalidTypeValueException {
		// Clean data
		data = data.trim();
		float result;
		// Check for errors
		try {
			result = Float.parseFloat(data);
		} catch (NumberFormatException err) {
			throw new InvalidTypeValueException(err);
		}

		return result;
	}

	/**
	 * Convert string data to valid double
	 *
	 * @param data Target data to convert
	 * @return Return a double value
	 * @throws InvalidTypeValueException Error if data is not a valid double number
	 */
	public static double parseDouble(@NotNull String data) throws InvalidTypeValueException {
		// Clean data
		data = data.trim();
		double result;
		// Check for errors
		try {
			result = Double.parseDouble(data);
		} catch (NumberFormatException err) {
			throw new InvalidTypeValueException(err);
		}
		return result;
	}

	/**
	 * Convert string data to valid long
	 *
	 * @param data Target data to convert
	 * @return Return a long value
	 * @throws InvalidTypeValueException Error if data is not a valid long number
	 */
	public static long parseLong(@NotNull String data) throws InvalidTypeValueException {
		// Clean data
		data = data.trim();
		long result;
		// Check for errors
		try {
			result = Long.parseLong(data);
		} catch (NumberFormatException err) {
			throw new InvalidTypeValueException(err);
		}

		return result;
	}

}
