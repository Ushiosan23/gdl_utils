package org.godot.utilities.utils;

import org.godot.utilities.core.callback.IAnyUtilsCallbacks;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Arrays;

public final class Arr {

	/**
	 * Cannot create instances.
	 */
	private Arr() {
	}

	/**
	 * Constant to indicate not found element in array
	 */
	public static final int NOT_FOUND = -1;

	/* ------------------------------------------------------------------
	 *
	 * Foreach
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Foreach method
	 *
	 * @param array    Target array
	 * @param callback Target execution callback
	 * @param <T>      Generic type
	 */
	public static <T> void foreach(T @NotNull [] array, IAnyUtilsCallbacks.IForeach<T> callback) {
		for (T item : array) {
			callback.invoke(item);
		}
	}


	/* ------------------------------------------------------------------
	 *
	 * Of
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Generate array from varargs.
	 *
	 * @param elements All elements to select
	 * @param <T>      Generic type
	 * @return Return an array with all {@code elements}
	 */
	@SafeVarargs
	public static <T> T[] of(T... elements) {
		return elements;
	}

	public static long[] longOf(long... elements) {
		return elements;
	}

	public static int[] intOf(int... elements) {
		return elements;
	}

	public static boolean[] boolOf(boolean... elements) {
		return elements;
	}

	public static float[] floatOf(float... elements) {
		return elements;
	}

	public static double[] doubleOf(double... elements) {
		return elements;
	}

	public static char @NotNull [] charOf(char... elements) {
		return elements;
	}

	public static Short @NotNull [] shotOf(Number... elements) {
		return Arrays.stream(elements)
			.map(Number::shortValue)
			.toArray(Short[]::new);
	}

	public static Byte @NotNull [] byteOf(Number... elements) {
		return Arrays.stream(elements)
			.map(Number::byteValue)
			.toArray(Byte[]::new);
	}

	/* ------------------------------------------------------------------
	 *
	 * IndexOf
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Search an element in the array
	 *
	 * @param array  Target array
	 * @param search Element to search
	 * @return Return the first index of element or {@code -1} if element to search not exists
	 */
	@Contract(pure = true)
	public static int indexOf(Object @NotNull [] array, @NotNull Object search) {
		// Iterate array
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(search)) return i;
		}
		return -1;
	}

	public static int indexOf(int @NotNull [] array, int search) {
		// Iterate array
		for (int i = 0; i < array.length; i++) {
			if (array[i] == search) return i;
		}
		return -1;
	}

	/**
	 * Search an element in the array. This method make the same process that {@link #indexOf(Object[], Object)} but
	 * inverted.
	 *
	 * @param array  Target array
	 * @param search Element to search
	 * @return Return the first last index of element or {@code -1} if element to search not exists
	 */
	@Contract(pure = true)
	public static int lastIndexOf(Object @NotNull [] array, @NotNull Object search) {
		for (int i = array.length - 1; i >= 0; i--) {
			if (array[i] == search) return i;
		}
		return -1;
	}

	public static int lastIndexOf(int @NotNull [] array, int search) {
		for (int i = array.length - 1; i >= 0; i--) {
			if (array[i] == search) return i;
		}
		return -1;
	}

	/* ------------------------------------------------------------------
	 *
	 * Empty
	 *
	 * ------------------------------------------------------------------ */

	/**
	 * Generate any type of empty arrays
	 *
	 * @param ref Class reference
	 * @param <T> Generic type
	 * @return Returns an empty array
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] emptyOf(Class<T> ref) {
		return (T[]) Array.newInstance(ref, 0);
	}

	public static long @NotNull [] longEmpty() {
		return new long[0];
	}

	public static int @NotNull [] intEmpty() {
		return new int[0];
	}

	public static short @NotNull [] shortEmpty() {
		return new short[0];
	}

	public static char @NotNull [] charEmpty() {
		return new char[0];
	}

	public static byte @NotNull [] byteEmpty() {
		return new byte[0];
	}

	public static boolean @NotNull [] booleanEmpty() {
		return new boolean[0];
	}

	public static float @NotNull [] floatEmpty() {
		return new float[0];
	}

	public static double @NotNull [] doubleEmpty() {
		return new double[0];
	}

	public static String @NotNull [] stringEmpty() {
		return emptyOf(String.class);
	}


}
