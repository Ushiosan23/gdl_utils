package org.godot.utilities.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ListUtils {

	private ListUtils() {
	}

	/**
	 * Generate list from arguments
	 *
	 * @param elements All initial elements
	 * @param <T>      Generic list type
	 * @return Returns an instance of list with all data
	 */
	@Contract("_ -> new")
	@SafeVarargs
	public static <T> @NotNull List<T> of(T... elements) {
		return new ArrayList<>(Arrays.asList(elements));
	}

}
